package com.aldes.jcsnets.util;

import java.nio.ByteBuffer;

/**
 * $File: JCSNetS_Util.java $
 * $Date: 2017-08-23 19:00:48 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_Util {
    
    /**
     * Convert float to byte array.
     * @param value : float value.
     * @return byte array.
     */
    public static byte [] float2ByteArray (float value) {  
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
    
    /**
     * Convert long to byte array.
     * @param value : float value.
     * @return byte array.
     */
    public static byte [] long2ByteArray (long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }
    
    /**
     * Print byte array in hex.
     * 
     * @param { byte[] } bArr : byte array to print.
     */
    public static void printByteArray(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString());
    }
    
    /**
     * Print a byte in hex.
     * 
     * @param { byte } b : byte to print.
     */
    public static void printByte(byte b) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X ", b));
        System.out.println(sb.toString());
    }
    
}
