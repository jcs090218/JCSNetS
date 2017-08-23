package com.aldes.jcsnets.tools;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.net.JCSNetS_Packet;
import com.aldes.jcsnets.net.SendPacketOpcode;
import com.aldes.jcsnets.net.login.JCSNetS_LoginServer;
import com.aldes.jcsnets.tools.data.output.JCSNetS_PacketLittleEndianWriter;

/**
 * $File: JCSNetS_PacketCreator.java $
 * $Date: 2017-03-27 23:50:48 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_PacketCreator
 * @brief
 */
public class JCSNetS_PacketCreator {

    private static Logger log = LoggerFactory.getLogger(JCSNetS_PacketCreator.class);
    private final static byte[] CHAR_INFO_MAGIC = new byte[]{(byte) 0xff, (byte) 0xc9, (byte) 0x9a, 0x3b};
    private final static byte[] ITEM_MAGIC = new byte[]{(byte) 0x80, 5};
    private final static long FT_UT_OFFSET = 116444592000000000L;  // EDT
    private static Random rand = new Random();


    private static long getKoreanTimestamp(long realTimestamp) {
        long time = (realTimestamp / 1000 / 60); // convert to minutes
        return ((time * 600000000) + FT_UT_OFFSET);
    }

    private static long getTime(long realTimestamp) {
        long time = (realTimestamp / 1000); // convert to seconds
        return ((time * 10000000) + FT_UT_OFFSET);
    }

    /**
     * Sends a ping packet.
     *
     * @return The packet.
     */
    public static JCSNetS_Packet getPing() {
        JCSNetS_PacketLittleEndianWriter jcslew = new JCSNetS_PacketLittleEndianWriter(16);

        jcslew.writeShort(SendPacketOpcode.PING.getValue());

        return jcslew.getPacket();
    }

    /**
     * Get hello packat.
     * @return JCSNetS_Packet : hello packet.
     */
    public static JCSNetS_Packet getHello() {
        JCSNetS_PacketLittleEndianWriter jcslew = new JCSNetS_PacketLittleEndianWriter(16);

        // set header
        jcslew.writeShort(0x10);

        // set data
        long len = JCSNetS_LoginServer.getClients().size();
        jcslew.writeLong(len);

        return jcslew.getPacket();
    }

}
