package com.moeum.moeum.api.ledger.payment.mapper;

import com.moeum.moeum.api.ledger.payment.dto.PaymentCreateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.domain.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    @Mapping(source = "parentPayment.id", target = "parentPaymentId")
    PaymentResponseDto toDto(Payment payment);

    Payment toEntity(PaymentCreateRequestDto paymentCreateRequestDto);
}
