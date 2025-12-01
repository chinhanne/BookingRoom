package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.UserCreationRequest;
import com.chinhan.bookingroom.dto.request.UserUpdateAvatarRequest;
import com.chinhan.bookingroom.dto.request.UserUpdateRequest;
import com.chinhan.bookingroom.dto.response.UserResponse;
import com.chinhan.bookingroom.entity.User;
import com.chinhan.bookingroom.entity.Role;
import com.chinhan.bookingroom.exception.AppException;
import com.chinhan.bookingroom.exception.ErrorCode;
import com.chinhan.bookingroom.mapper.UserMapper;
import com.chinhan.bookingroom.repository.RoleRepository;
import com.chinhan.bookingroom.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) throws IOException {
        try {
            User user = userMapper.toUser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            if(userRepository.existsByUsername(user.getUsername())){
                throw new AppException(ErrorCode.USER_EXISTED);
            }

            HashSet<Role> roles = new HashSet<>();
            roleRepository.findById("USER").ifPresent(roles::add);
            user.setRoles(roles);

            if(request.getImage() != null && !request.getImage().isEmpty()){
                String fileName = UUID.randomUUID() + "_" + request.getImage().getOriginalFilename();
                Path path = Paths.get("D:/Java_SpringBoot/images/" + fileName);
                Files.createDirectories(path.getParent());
                request.getImage().transferTo(path.toFile());

                user.setImage("/booking/images/" + fileName);
            }
            return userMapper.toUserResponse(userRepository.save(user));
        }catch (Exception e){
            log.error("Lỗi khi update room: ", e);
            throw e;
        }
    }

    @PostAuthorize("returnObject.id == authentication.principal.id or hasRole('ADMIN')")
    public UserResponse updateUser(String id, UserUpdateRequest request) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PostAuthorize("returnObject.id == authentication.principal.id or hasRole('ADMIN')")
    public UserResponse updateAvatar(String id, UserUpdateAvatarRequest request) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        MultipartFile image = request.getImage();

        if(image != null && !image.isEmpty()){
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get("D:/Java_SpringBoot/images/" + fileName);
            Files.createDirectories(path.getParent());
            image.transferTo(path.toFile());

            user.setImage("/booking/images/" + fileName);
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> getAllUsers(int page, int size){
        Sort sort = Sort.by("username").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = userRepository.findAll(pageable);

        return PageResponse.<UserResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(userMapper::toUserResponse).toList())
                .build();
    }

    @PostAuthorize("returnObject.id == authentication.principal.id") //Neu de authentication.name thi no se tu map userid vao .name
    public UserResponse getUserById(String userId){
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
