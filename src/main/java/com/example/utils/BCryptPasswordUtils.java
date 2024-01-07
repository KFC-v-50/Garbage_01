package com.example.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 哈希加密
 */
public class BCryptPasswordUtils{

    public static boolean matchPassword(String newPassword, String oldPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(newPassword, oldPassword);
    }

    public static String encodePassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
