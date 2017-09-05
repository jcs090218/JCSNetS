package com.aldes.jcsnets.net.login.handler;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.net.AbstractJCSNetS_PacketHandler;
import com.aldes.jcsnets.net.login.JCSNetS_LoginServer;
import com.aldes.jcsnets.tools.data.input.SeekableLittleEndianAccessor;


/**
 * $File: RegisterLoginHandler.java $
 * $Date: 2017-03-28 00:05:31 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */



/**
 * @class RegisterLoginHandler
 * @brief
 */
public class RegisterLoginHandler extends AbstractJCSNetS_PacketHandler {

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, JCSNetS_Client client) {
        JCSNetS_LoginServer.getInstance().resgister(client);
    }
}
