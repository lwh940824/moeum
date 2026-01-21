package com.moeum.moeum.type;

public enum AuthProvider {
    GOOGLE,
    KAKAO,
    NAVER;

    public static AuthProvider from(String provider) {
        return AuthProvider.valueOf(provider.toUpperCase());
    }
}
