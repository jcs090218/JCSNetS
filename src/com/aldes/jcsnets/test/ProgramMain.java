package com.aldes.jcsnets.test;

import com.aldes.jcsnets.net.JCSNetS_PacketKey;
import com.aldes.jcsnets.server.JCSNetS_Server;
import com.aldes.jcsnets.server.ServerProperties;

/**
 * $File: ProgramMain.java $
 * $Date: 2017-03-27 22:09:33 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class ProgramMain
 * @brief Test Program Entry.
 */
public class ProgramMain {
    
    public static JCSNetS_Server JCSNETS_SERVER = null;
    
    private static String GAME_NAME = "JCSNetS";
    private static String SERVER_VERSION = "1.0.0";
    private static String HOST_INC = "JayCeS";
    
    /**
     * @func main
     * @brief Program entry, testing purpose.
     * @param args : arguments vector.
     */
    public final static void main(final String[] args) {
        System.out.println(GAME_NAME + " " + SERVER_VERSION + " - " + HOST_INC);
        
        if (Boolean.parseBoolean(ServerProperties.getProperty("jcs.Admin"))) {
            System.out.println("Admin mode on.");
        }
        
        JCSNetS_PacketKey.getInstance();
        
        JCSNETS_SERVER = new JCSNetS_Server();
        JCSNETS_SERVER.run();
    }
    
}
