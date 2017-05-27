package com.aldes.jcsnets.net;

import com.aldes.jcsnets.client.JCSNetS_Client;

/**
 * $File: AbstractJCSNetS_PacketHandler.java $
 * $Date: 2017-03-28 00:09:45 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public abstract class AbstractJCSNetS_PacketHandler implements JCSNetS_PacketHandler {

    @Override
    public boolean validateState(JCSNetS_Client c) {
        return c.isLoggedIn();
    }
}
