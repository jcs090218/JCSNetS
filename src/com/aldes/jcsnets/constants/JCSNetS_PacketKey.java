package com.aldes.jcsnets.constants;

/**
 * $File: JCSNetS_PacketKey.java $
 * $Date: 2017-03-27 23:22:59 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_PacketKey {

    private static JCSNetS_PacketKey instance = null;

    public static int MAX_MESSAGE = 16 * 1024;  // 暂定一个消息最大为, [default: 16k bytes]

    public static int ENCODE_KEY_LEN = 4;
    public static byte[] ENCODE_KEY = new byte[ENCODE_KEY_LEN];
    public static int DECODE_KEY_LEN = 4;
    public static byte[] DECODE_KEY = new byte[DECODE_KEY_LEN];


    /**
     * @func JCSNetS_PacketKey
     * @brief Constructor
     */
    private JCSNetS_PacketKey() {
        // Generate Keys
        DECODE_KEY[0] = (byte)'J';
        DECODE_KEY[1] = (byte)'C';
        DECODE_KEY[2] = (byte)'S';
        DECODE_KEY[3] = (byte)'E';

        ENCODE_KEY[0] = (byte)'J';
        ENCODE_KEY[1] = (byte)'C';
        ENCODE_KEY[2] = (byte)'S';
        ENCODE_KEY[3] = (byte)'D';
    }

    /**
     * @func getInstance
     * @brief singleton
     */
    public static JCSNetS_PacketKey getInstance() {
        if (instance == null)
            instance = new JCSNetS_PacketKey();
        return instance;
    }
}
