package com.moeum.moeum.api.ledger.payment.controller;

import com.moeum.moeum.api.ledger.payment.service.PaymentService;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayment(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(paymentService.findAllByUserId(userDetails.getId()));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPayment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.findByUserIdAndId(userDetails.getId(), paymentId));
    }
}
