package com.aldes.jcsnets.net.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.constants.JCSNetS_PacketKey;

/**
 * $File: JCSNetS_PacketEncoder.java $
 * $Date: 2017-03-27 23:15:39 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_PacketEncoder
 * @brief Main packet encoder.
 */
public class JCSNetS_PacketEncoder implements ProtocolEncoder {

    private static Logger log = LoggerFactory.getLogger(JCSNetS_PacketEncoder.class);

    /**
     * @func dispose
     * @brief Do stuff if client drop connection.
     * @param session : Client object.
     */
    @Override
    public void dispose(IoSession session) throws Exception {

    }

    /**
     * @func encode
     * @brief Do stuff if packet came in.
     * @param session : Client object.
     * @param message : package object.
     * @param out : interface output port to message/buffer to client.
     */
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
        throws Exception {

        byte[] unencrypted = (byte[]) message;

        int packetLength = unencrypted.length + JCSNetS_PacketKey.ENCODE_KEY_LEN;
        if (packetLength > JCSNetS_PacketKey.MAX_MESSAGE) {
            // TODO(JenChieh): split the packet into suitable size
            log.info("編碼失敗, 要傳送的數據過長!!");
            return;
        }

        // 包裝上表頭!!
        byte[] encrypted = new byte[packetLength];
        for (int index = 0;
                index < JCSNetS_PacketKey.ENCODE_KEY_LEN;
                ++index) {
            encrypted[index] = JCSNetS_PacketKey.ENCODE_KEY[index];
        }

        // 複製"真實數據區"到"發送包"
        for (int index = JCSNetS_PacketKey.ENCODE_KEY_LEN;
                index < packetLength;
                ++index) {
            encrypted[index] = unencrypted[index - JCSNetS_PacketKey.ENCODE_KEY_LEN];
        }

        IoBuffer buf = IoBuffer.allocate(encrypted.length).setAutoExpand(true);
        buf.put(encrypted, 0, encrypted.length);

        buf.flip();
        
        log.info("編碼後：" + buf.toString());
        out.write(buf);
    }
}
