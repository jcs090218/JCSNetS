package com.aldes.jcsnets.net.login.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.net.AbstractJCSNetS_PacketHandler;
import com.aldes.jcsnets.server.JCSNetS_LoginServer;
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

    private static Logger log = LoggerFactory.getLogger(AbstractJCSNetS_PacketHandler.class);

    @Override
    public void handlePacket(
            SeekableLittleEndianAccessor slea,
            JCSNetS_Client c) {

        JCSNetS_LoginServer.getInstance().resgister(c);
    }
}
