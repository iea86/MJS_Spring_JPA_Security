package com.epam.esm.auth.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class CryptUtil {

    private CryptUtil() {
    }

    private static final int HASH_ROUNDS = 12;

    public static String hashString(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt(HASH_ROUNDS));
    }
}

