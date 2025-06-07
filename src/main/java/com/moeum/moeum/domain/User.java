package com.moeum.moeum.domain;

import com.moeum.moeum.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50)
    private String activate_id;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String address;

    private String email;

    private String provider;

    private String providerId;

    @Builder
    public User(String activate_id, String password, RoleType roleType, String address, String email, String provider, String providerId) {
        this.activate_id = activate_id;
        this.password = password;
        this.roleType = roleType;
        this.address = address;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }
}
