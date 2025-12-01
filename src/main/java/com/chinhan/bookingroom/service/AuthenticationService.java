package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.request.AuthenticationRequest;
import com.chinhan.bookingroom.dto.request.IntrospectRequest;
import com.chinhan.bookingroom.dto.request.LogoutRequest;
import com.chinhan.bookingroom.dto.request.RefreshTokenRequest;
import com.chinhan.bookingroom.dto.response.AuthenticationResponse;
import com.chinhan.bookingroom.dto.response.IntrospectResponse;
import com.chinhan.bookingroom.entity.RedisInvalidatedToken;
import com.chinhan.bookingroom.entity.User;
import com.chinhan.bookingroom.exception.AppException;
import com.chinhan.bookingroom.exception.ErrorCode;
import com.chinhan.bookingroom.repository.RedisInvalidatedTokenRepository;
import com.chinhan.bookingroom.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    RedisInvalidatedTokenRepository redisInvalitedTokenReponsitory;

    @NonFinal
    @Value("${jwt.signerkey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try{
            verifyToken(token, false);
        }catch(AppException e){
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }


    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signToken = verifyToken(request.getToken(), true);
            String jwtId = signToken.getJWTClaimsSet().getJWTID();
            Date exp = signToken.getJWTClaimsSet().getExpirationTime();

            long ttl = (exp.getTime() - System.currentTimeMillis()) / 1000;

            if(redisInvalitedTokenReponsitory.existsById(jwtId)){
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            RedisInvalidatedToken redisInvalidatedToken = RedisInvalidatedToken.builder()
                    .id(jwtId)
                    .ttl(ttl)
                    .build();

            redisInvalitedTokenReponsitory.save(redisInvalidatedToken);
        }catch(AppException exception){
            throw new AppException((ErrorCode.INVALID_TOKEN));
        }
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken(),true);

        var jit = signToken.getJWTClaimsSet().getJWTID();
        var exp = signToken.getJWTClaimsSet().getExpirationTime();
        long ttl = (exp.getTime() - System.currentTimeMillis()) / 1000;

        RedisInvalidatedToken redisInvalidatedToken = RedisInvalidatedToken.builder()
                .id(jit)
                .ttl(ttl)
                .build();

        redisInvalitedTokenReponsitory.save(redisInvalidatedToken);

        var userId = signToken.getJWTClaimsSet().getSubject();
        var user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirytime = (isRefresh) ? new Date(
                signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
        : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if(!(verified && expirytime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        if(isTokenInvalidated(jwtId)){
            throw  new AppException((ErrorCode.UNAUTHENTICATED));
        }
        return signedJWT;
    }

    public boolean isTokenInvalidated(String jwtId) {
        return redisInvalitedTokenReponsitory.existsById(jwtId);
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("nhanbooking.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("username", user.getUsername())
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add(role.getName());
            });
        }
        return stringJoiner.toString();
    }


}
