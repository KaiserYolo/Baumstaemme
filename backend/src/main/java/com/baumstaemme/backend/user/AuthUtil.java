package com.baumstaemme.backend.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean checkPassword(User user, String inputPassword) {
        return passwordEncoder.matches(inputPassword, user.getPassword());
    }
}
