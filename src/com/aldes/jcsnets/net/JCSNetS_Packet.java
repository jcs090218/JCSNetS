package com.aldes.jcsnets.net;

import java.io.Serializable;

/**
 * $File: JCSNetS_Packet.java $
 * $Date: 2017-03-27 23:57:17 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_Packet
 * @brief Serializable data struct.
 */
public interface JCSNetS_Packet extends Serializable {
    public byte[] getBytes();

    public Runnable getOnSend();

    public void setOnSend(Runnable onSend);
}
