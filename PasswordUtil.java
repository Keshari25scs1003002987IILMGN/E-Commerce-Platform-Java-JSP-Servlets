package com.cyrostack.ecommerce.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/** Passwords are stored as "salt:hash" (SHA-256 with a random per-user salt). */
public final class PasswordUtil {
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {}

    public static String hash(String password) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        String s = Base64.getEncoder().encodeToString(salt);
        return s + ":" + digest(s, password);
    }

    public static boolean verify(String password, String stored) {
        int i = stored.indexOf(':');
        if (i < 0) return false;
        String salt = stored.substring(0, i);
        return MessageDigest.isEqual(
                digest(salt, password).getBytes(StandardCharsets.UTF_8),
                stored.substring(i + 1).getBytes(StandardCharsets.UTF_8));
    }

    private static String digest(String salt, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString((md.digest((salt + password).getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
