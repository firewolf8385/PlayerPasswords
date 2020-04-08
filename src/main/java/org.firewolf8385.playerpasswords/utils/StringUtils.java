package org.firewolf8385.playerpasswords.utils;

public class StringUtils
{

    /**
     * An easy method to hash a String. Used for password protection.
     * @param s The string to hash.
     * @return The hashed string.
     */
    public static int hash(String s)
    {
        return s.hashCode();
    }

}
