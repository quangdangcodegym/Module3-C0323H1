package com.codegym.manager_products.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    public static String hashPassword(String password) {
        String pass = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return pass;
    }

    public static boolean isValidPassword(String password, String passwordDB) {
        return BCrypt.checkpw(password, passwordDB);
    }
}
