package com.moeum.moeum.domain;

import com.moeum.moeum.type.PaymentType;
import com.moeum.moeum.type.YnType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_payment")
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YnType useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String name, PaymentType paymentType) {
        this.name = name;
        this.paymentType = paymentType;
    }

    public void changeUseYn(YnType useYn) {
        this.useYn = useYn;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    @Builder
    public Payment(Long paymentId, String name, PaymentType paymentType, User user) {
        this.name = name;
        this.paymentType = paymentType;
        this.user = user;
    }
}
