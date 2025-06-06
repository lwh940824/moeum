package com.moeum.moeum.domain;

import com.moeum.moeum.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long user_id;

    @Column(length = 50)
    private String activate_id;

    @Column(length = 100)
    private String password;

    private RoleType roleType;

    private String address;

    @Builder
    public User(Long user_id, String activate_id, String password, RoleType roleType, String address) {
        this.activate_id = activate_id;
        this.password = password;
        this.roleType = roleType;
        this.address = address;
    }
}
