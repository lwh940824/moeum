package com.moeum.moeum.global.security;

import com.moeum.moeum.api.ledger.user.repository.UserRepository;
import com.moeum.moeum.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DevAuthBypassFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Value("${security.mock.email:dev@moeum.local}")
    private String mockEmail;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // DB에 없으면 하나 만들고
            User user = userRepository.findByEmail(mockEmail)
                    .orElseGet(() -> userRepository.save(
                            User.builder().email(mockEmail).name("Dev User").build()));

            var auth = new UsernamePasswordAuthenticationToken(
                    new CustomUserDetails(user.getId(), user.getEmail(),
                            org.springframework.security.core.authority.AuthorityUtils.createAuthorityList("ROLE_USER")),
                    null,
                    org.springframework.security.core.authority.AuthorityUtils.createAuthorityList("ROLE_USER")
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(req, res);
    }
}
