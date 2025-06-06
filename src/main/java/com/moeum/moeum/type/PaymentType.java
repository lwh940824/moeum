package com.moeum.moeum.type;

public enum PaymentType {
    CASH("현금"),
    ATM("체크카드"),
    CREDIT("신용카드"),
    ETC("기타");

    private final String paymentType;

    PaymentType(String paymentType) {this.paymentType = paymentType;}
}
