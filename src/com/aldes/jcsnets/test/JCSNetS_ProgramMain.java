package com.aldes.jcsnets.test;

import com.aldes.jcsnets.constants.JCSNetS_PacketKey;
import com.aldes.jcsnets.net.channel.JCSNetS_ChannelServer;
import com.aldes.jcsnets.net.login.JCSNetS_LoginServer;
import com.aldes.jcsnets.server.ProtocolType;
import com.aldes.jcsnets.server.ServerProperties;
import com.aldes.jcsnets.util.JCSNetS_Logger;

/**
 * $File: JCSNetS_ProgramMain.java $
 * $Date: 2017-03-27 22:09:33 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_ProgramMain
 * @brief Test Program Entry.
 */
public class JCSNetS_ProgramMain {
    private static String GAME_NAME = "JCSNetS";
    private static String SERVER_VERSION = "1.0.0";
    private static String HOST_INC = "JayCeS";
    
    // Select TCP/UDP. [Default : TCP] 
    private static ProtocolType PROTOCAL_TYPE = ProtocolType.TCP;
    
    /**
     * @func main
     * @brief Program entry, testing purpose.
     * @param args : arguments vector.
     */
    public final static void main(final String[] args) {
        System.out.println(GAME_NAME + " " + SERVER_VERSION + " - " + HOST_INC);
        
        System.out.println("Protocal Type: " + PROTOCAL_TYPE);
        
        if (Boolean.parseBoolean(ServerProperties.getProperty("jcs.Admin"))) {
            JCSNetS_Logger.println("Admin mode on.");
        }
        
        JCSNetS_PacketKey.getInstance();
        
        /* Run Login Server. */
        JCSNetS_Logger.println("[Login Server]");
        JCSNetS_LoginServer.getInstance(PROTOCAL_TYPE);
        
        /* Run Channel Server. */
        JCSNetS_Logger.println("[Channel Server]");
        JCSNetS_ChannelServer.getInstances(PROTOCAL_TYPE);
        
        JCSNetS_Logger.println("\nDone :::\n");
    }
    
}
