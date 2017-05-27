package com.aldes.jcsnets.net.handler;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.net.JCSNetS_PacketHandler;
import com.aldes.jcsnets.tools.data.input.SeekableLittleEndianAccessor;


/**
 * $File: KeepAliveHandler.java $
 * $Date: 2017-03-28 00:06:25 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class KeepAliveHandler
 * @brief
 */
public class KeepAliveHandler implements JCSNetS_PacketHandler {

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, JCSNetS_Client c) {
        c.pongReceived();
    }

    @Override
    public boolean validateState(JCSNetS_Client c) {
        return true;
    }
}
