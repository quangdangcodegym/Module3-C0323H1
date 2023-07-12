package com.codegym.customermanager.utils;

import java.util.regex.Pattern;

public class ValidatesUtils {
    public static final String USERNAME_REGEX = "^[A-Za-z][A-Za-z0-9_ ]{7,19}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";
    public static final String ADDRESS_REGEX = "^[A-Za-z][A-Za-z0-9_ ]{7,19}$";
    public static boolean isUserNameValid(String name) {
        return Pattern.matches(USERNAME_REGEX, name);
    }
    public static boolean isAddressValid(String address) {
        return Pattern.matches(ADDRESS_REGEX, address);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
