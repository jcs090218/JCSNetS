package com.aldes.jcsnets.client;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.aldes.jcsnets.tools.HexTool;

/**
 * $File: JCSNetS_LoginCrypto.java $
 * $Date: 2017-09-28 12:47:30 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */

/*
 * Encrypt/Decrypt the login password.
 * @author JenChieh
 *
 */
public class JCSNetS_LoginCrypto {
    
    private JCSNetS_LoginCrypto () { 
    }
    
    private static char[] iota64 = new char[64];
    
    /**
     * Convert byte array to hex string.
     * 
     * @param { bytes[] } bytes : byte array to be convert.
     * @return { String } : hex string result after convert by byte array pass-in.
     */
    private static String toSimpleHexString(byte[] bytes) {
        return HexTool.toString(bytes).replace(" ", "").toLowerCase();
    }
    
    /**
     * Hash with password hash with digest fore more proper use.
     * 
     * @param { String } in : input buffer prepare to be hash data.
     * @param { String } digest : digest pair/key.
     * @return { String } : hashed with digest result.
     */
    private static String hashWithDigest(String in, String digest) {
        try {
            MessageDigest Digester = MessageDigest.getInstance(digest);
            Digester.update(in.getBytes("UTF-8"), 0, in.length());
            byte[] sha1Hash = Digester.digest();
            return toSimpleHexString(sha1Hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Hashing the password failed", ex);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding the string failed", e);
        }
    }
    

    /* Return hex for 'SHA-1' crypto. */
    public static String hexSha1(String in) {
        return hashWithDigest(in, "SHA-1");
    }

    /* Return hex for 'SHA-512' crypto. */
    private static String hexSha512(String in) {
        return hashWithDigest(in, "SHA-512");
    }

    /* Check hex for 'SHA-1' crypto match the password. */
    public static boolean checkSha1Hash(String hash, String password) {
        return hash.equals(hexSha1(password));
    }

    /* Check hex for 'SHA-512' crypto match the password. */
    public static boolean checkSaltedSha512Hash(String hash, String password, String salt) {
        return hash.equals(makeSaltedSha512Hash(password, salt));
    }

    /**
     * Make salt uses 'SHA-512' crypto hash.
     * 
     * @param { String } password : password to be salt into.
     * @param { String } salt : salt code.
     * @return { String } : salted password.
     */
    public static String makeSaltedSha512Hash(String password, String salt) {
        return hexSha512(password + salt);
    }

    /**
     * Make a salt so we can use to store into DB and for future check.
     * @return { String } : a salt code.
     */
    public static String makeSalt() {
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        return toSimpleHexString(salt);
    }

    /*
     * Generate alphabet and numbers characters code.
     */
    static {
        int i = 0;
        iota64[i++] = '.';
        iota64[i++] = '/';
        for (char c = 'A'; c <= 'Z'; c++) {
            iota64[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; c++) {
            iota64[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            iota64[i++] = c;
        }
    }

    /**
     * Hash the password.
     * 
     * @param { String } password : password to be hash/encrypt. 
     * @return { String } : hashed password result.
     */
    public static String hashPassword(String password) {
        byte[] randomBytes = new byte[6];
        Random randomGenerator = new Random();
        randomGenerator.nextBytes(randomBytes);
        return jcsNetS_Crypt(password, genSalt(randomBytes));
    }

    /**
     * Shortcut way of check if the password match to crypt result.
     * 
     * @param { String } password : password to check.
     * @param { String } hash : hash to check;
     * @return { boolean } : true, password match. false, vice versa.
     */
    public static boolean checkPassword(String password, String hash) {
        return (jcsNetS_Crypt(password, hash).equals(hash));
    }

    /**
     * Check if the password a legacy password.
     * 
     * @param { String } hash : 
     * @return { boolean } : true, is legacy. false, not a legacy password.
     */
    public static boolean isLegacyPassword(String hash) {
        return hash.substring(0, 3).equals("$H$");
    }

    /**
     * Default encrypt algorithm using "SHA-1" version hasing algorithm.
     * 
     * NOTE(jenchieh): design default hash/encrypt password here...
     * 
     * @param { String } password : password to be encrypt/hash.
     * @param { String } seed : seed key to encrypt/hash.
     * @return { String } : encrypted password.
     * @throws RuntimeException
     */
    private static String jcsNetS_Crypt(String password, String seed) throws RuntimeException {
        String out = null;
        int count = 8;
        MessageDigest digester;
        if (!seed.substring(0, 3).equals("$H$")) {
            byte[] randomBytes = new byte[6];
            java.util.Random randomGenerator = new java.util.Random();
            randomGenerator.nextBytes(randomBytes);
            seed = genSalt(randomBytes);
        }
        String salt = seed.substring(4, 12);
        if (salt.length() != 8) {
            throw new RuntimeException("Error hashing password - Invalid seed.");
        }
        byte[] sha1Hash = new byte[40];
        try {
            digester = MessageDigest.getInstance("SHA-1");
            digester.update((salt + password).getBytes("iso-8859-1"), 0, (salt + password).length());
            sha1Hash = digester.digest();
            do {
                byte[] CombinedBytes = new byte[sha1Hash.length + password.length()];
                System.arraycopy(sha1Hash, 0, CombinedBytes, 0, sha1Hash.length);
                System.arraycopy(password.getBytes("iso-8859-1"), 0, CombinedBytes, sha1Hash.length, password.getBytes("iso-8859-1").length);
                digester.update(CombinedBytes, 0, CombinedBytes.length);
                sha1Hash = digester.digest();
            } while (--count > 0);
            out = seed.substring(0, 12);
            out += encode64(sha1Hash);
        } catch (NoSuchAlgorithmException Ex) {
            System.out.println("Error hashing password." + Ex);
        } catch (UnsupportedEncodingException Ex) {
            System.out.println("Error hashing password." + Ex);
        }
        if (out == null) {
            throw new RuntimeException("Error hashing password - out = null");
        }
        return out;
    }

    /**
     * Generate salt form alphabet characters array list.
     * @param { byte[] } Random : random byte that want to add salt. 
     * @return { String } : salted code string.
     */
    private static String genSalt(byte[] Random) {
        String Salt = "$H$";
        Salt += iota64[30];
        Salt += encode64(Random);
        return Salt;
    }

    /**
     * Encode the byte array to alphabet salt result.
     * @param { String } Input : input byte buffer array.
     * @return { String } : encoded code string.
     */
    private static String encode64(byte[] Input) {
        int iLen = Input.length;
        int oDataLen = (iLen * 4 + 2) / 3;
        int oLen = ((iLen + 2) / 3) * 4;
        char[] out = new char[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            int i0 = Input[ip++] & 0xff;
            int i1 = ip < iLen ? Input[ip++] & 0xff : 0;
            int i2 = ip < iLen ? Input[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            out[op++] = iota64[o0];
            out[op++] = iota64[o1];
            out[op] = op < oDataLen ? iota64[o2] : '=';
            op++;
            out[op] = op < oDataLen ? iota64[o3] : '=';
            op++;
        }
        return new String(out);
    }
    
}
