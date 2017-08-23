package com.aldes.jcsnets.net.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.net.JCSNetS_PacketKey;

/**
 * $File: JCSNetS_PacketDecoder.java $
 * $Date: 2017-03-27 23:18:13 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_PacketDecoder
 * @brief Main packet decoder
 */
public class JCSNetS_PacketDecoder extends CumulativeProtocolDecoder {

    private static Logger log = LoggerFactory.getLogger(JCSNetS_PacketDecoder.class);
    private static final String DECODER_STATE_KEY = JCSNetS_PacketDecoder.class.getName() + ".STATE";

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in,
            ProtocolDecoderOutput out) throws Exception {
        
        log.info("解碼前：" + in.toString());

        // Check packet length
        int packetLength = in.remaining();
        if (packetLength > JCSNetS_PacketKey.MAX_MESSAGE) {
            // TODO(JenChieh): split the packet into suitable size
            log.info("解碼失敗: 數據長度過長!...");
            return false;
        }

        byte[] undecryptedPacket = new byte[packetLength];
        in.get(undecryptedPacket, 0, undecryptedPacket.length);

        // 檢查header
        for (int index = 0;
                index < JCSNetS_PacketKey.DECODE_KEY_LEN;
                ++index) {

            char c = (char)undecryptedPacket[index];
            if (c != JCSNetS_PacketKey.DECODE_KEY[index]) {
                log.info("解碼失敗! 金鑰: " + JCSNetS_PacketKey.DECODE_KEY[index] + ", 傳送金鑰" + c);
                return false;
            }
        }

        byte[] decryptedPacket = new byte[packetLength - JCSNetS_PacketKey.DECODE_KEY_LEN];

        // Get the decrypted packet!!
        // 解碼成功後, 複製一份真實數據區!!
        for (int index = 0;
                index < decryptedPacket.length;
                ++index) {
            decryptedPacket[index] = undecryptedPacket[index + JCSNetS_PacketKey.DECODE_KEY_LEN];
        }
        
        // IMPORTANT(jenchieh): Check the client are good client not 
        // something else here!!

        // 交由handler處理
        out.write(decryptedPacket);

        return true;
    }

}
