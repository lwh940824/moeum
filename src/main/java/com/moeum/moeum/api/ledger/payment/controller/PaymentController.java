package com.moeum.moeum.api.ledger.payment.controller;

import com.moeum.moeum.api.ledger.payment.dto.PaymentCreateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentUpdateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentUseYnPatchRequestDto;
import com.moeum.moeum.api.ledger.payment.service.PaymentService;
import com.moeum.moeum.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "결제수단")
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

    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @RequestBody PaymentCreateRequestDto paymentCreateRequestDto) {
        PaymentResponseDto paymentResponseDto = paymentService.create(userDetails.getId(), paymentCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                paymentResponseDto
        );
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> update(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @PathVariable Long paymentId,
                                                     @RequestBody PaymentUpdateRequestDto paymentUpdateRequestDto) {
        return ResponseEntity.ok(paymentService.update(userDetails.getId(), paymentId, paymentUpdateRequestDto));
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<Void> changeUseYn(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @PathVariable Long paymentId,
                                                          @RequestBody PaymentUseYnPatchRequestDto paymentUseYnPatchRequestDto) {
        paymentService.changeUseYn(userDetails.getId(), paymentId, paymentUseYnPatchRequestDto);
        return ResponseEntity.noContent().build();
    }
}