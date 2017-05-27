package com.aldes.jcsnets.tools;

import java.util.Random;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.net.JCSNetS_Packet;
import com.aldes.jcsnets.server.JCSNetS_LoginServer;

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
    private final static long FT_UT_OFFSET = 116444592000000000L; // EDT
    //protected BlackVaultCharacter[] visitors = new BlackVaultCharacter[3];
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
        //BlackVaultPacketLittleEndianWriter bvlew = new BlackVaultPacketLittleEndianWriter(16);

        //bvlew.writeShort(SendPacketOpcode.PING.getValue());

        //return bvlew.getPacket();
        return null;
    }

    /**
     * Get hello packat.
     * @return byte[] : hello buffer
     */
    public static byte[] getHello() {
        IoBuffer buf = IoBuffer.allocate(2);
        buf.setAutoExpand(true);

        // set header
        buf.putShort((short)10);
        buf.putShort((short) 0x10);


        // set data
        long len = JCSNetS_LoginServer.getClients().size();
        //buf.putLong(len);

        return buf.array();
    }

}
