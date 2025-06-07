package com.moeum.moeum.api.ledger.User;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login-success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("로그인 성공!");
    }
}
