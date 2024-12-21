package com.example.schoolmanagement.util;

import java.util.regex.Pattern;

public class UtilService {
    
    public static boolean emailValidation(String email) {
        return Pattern.matches(Constants.EMAIL_PATTERN, email);
    }

    public static boolean phoneNumberValidation(String phoneNumber) {
        return Pattern.matches(Constants.PHONE_REGEX, phoneNumber);
    }
}
