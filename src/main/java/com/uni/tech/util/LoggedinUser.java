package com.uni.tech.util;

import com.uni.tech.exception.NotFoundException;
import com.uni.tech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class LoggedinUser {

    public static String getPin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal!= null) {
                String pin = (String) principal;
                return pin;
            }
        }
        throw new NotFoundException("User not found in Security Context.");
    }

}
