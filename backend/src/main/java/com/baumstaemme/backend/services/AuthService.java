package com.baumstaemme.backend.services;

import com.baumstaemme.backend.entity.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean checkPassword(User user, String inputPassword) {
        return user.getPassword().equals(inputPassword); // (nur Beispiel)
    }
}
