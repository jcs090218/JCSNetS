package com.aldes.jcsnets.net.login.handler;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.net.AbstractJCSNetS_PacketHandler;
import com.aldes.jcsnets.net.login.JCSNetS_LoginServer;
import com.aldes.jcsnets.tools.data.input.SeekableLittleEndianAccessor;

/**
 * $File: DeregisterLoginHandler.java $
 * $Date: 2017-03-28 00:04:19 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */



/**
 * @class DeregisterLoginHandler
 * @brief
 */
public class DeregisterLoginHandler extends AbstractJCSNetS_PacketHandler {

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea,
                             JCSNetS_Client c) {
        JCSNetS_LoginServer.getInstance().deresgister(c);
    }

}
