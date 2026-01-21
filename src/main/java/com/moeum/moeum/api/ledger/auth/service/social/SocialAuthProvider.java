package com.moeum.moeum.api.ledger.auth.service.social;

import com.moeum.moeum.api.ledger.auth.dto.LoginRequestDto;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.type.AuthProvider;

public interface SocialAuthProvider {
    AuthProvider provider();

    User authenticate(LoginRequestDto request);
}
