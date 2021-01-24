package org.firewolf8385.playerpasswords.utils;

import java.security.MessageDigest;

public class EncryptionUtils {
    /**
     * An easy method to hash a String. Used for password protection.
     * @param s The string to hash.
     * @return The hashed string.
     */
    public static int hash(String s) {
        return s.hashCode();
    }

    /**
     * Hash a String with SHA-256.
     * @param value String
     * @return Hashed String
     */
    public static String getSha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * Convert bytes to hex
     * @param bytes Bytes
     * @return Hex
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
