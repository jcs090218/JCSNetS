package com.aldes.jcsnets.net;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.tools.data.input.SeekableLittleEndianAccessor;


/**
 * $File: JCSNetS_PacketHandler.java $
 * $Date: 2017-03-27 23:39:44 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @inter JCSNetS_PacketHandler
 * @brief
 */
public interface JCSNetS_PacketHandler {

    /**
     * Handle packet.
     *
     * @param sleaa : N/A
     * @param c : client
     */
    void handlePacket(SeekableLittleEndianAccessor slea, JCSNetS_Client c);

    /**
     * This method validates some general state constrains.
     * For example that the Client has to be logged in for this
     * packet. When the method returns false the Client should
     * be disconnected. Further validation based on the content
     * of the packet and disconnecting the client if it's invalid
     * in handlePacket is recommended.
     *
     * @param c : the client
     * @return true if the state of the client is valid to send this packettype
     */
    boolean validateState(JCSNetS_Client c);
}
