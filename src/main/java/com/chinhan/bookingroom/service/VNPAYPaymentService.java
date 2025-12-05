package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.configuration.VnPayConfig;
import com.dacn.WebsiteBanDoCongNghe.dto.response.VNPAYPaymentsResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Orders;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.UserReponsitory;
import com.dacn.WebsiteBanDoCongNghe.utils.VnPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPAYPaymentService {
    VnPayConfig vnPayConfig;
    UserReponsitory userReponsitory;

    private User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        String userName = authentication.getName();
        return userReponsitory.findByUsername(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    }

    public VNPAYPaymentsResponse createVnPayPayment(HttpServletRequest request, String transactionId, Orders orders) {
        User user = getAuthenticatedUser();

        long totalPrice =Math.round(orders.getTotalPrice() * 100);
        String amount = String.valueOf(totalPrice);
        String bankCode = "NCB";

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(transactionId);
        vnpParamsMap.put("vnp_Amount", amount);
        vnpParamsMap.put("vnp_BankCode", bankCode);
        vnpParamsMap.put("vnp_IpAddr", VnPayUtils.getIpAddress(request));

        //build query url
        String queryUrl = VnPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VnPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl()+ "?" + queryUrl;
        return VNPAYPaymentsResponse.builder()
                .code("ok")
                .message("Thành công")
                .paymentUrl(paymentUrl).build();
    }
}
