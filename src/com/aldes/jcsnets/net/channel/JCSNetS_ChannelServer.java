package com.aldes.jcsnets.net.channel;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.net.PacketProcessor;
import com.aldes.jcsnets.server.JCSNetS_Server;
import com.aldes.jcsnets.server.ServerProperties;
import com.aldes.jcsnets.server.map.JCSNetS_MapFactory;

/**
 * $File: JCSNetS_ChannelServer.java $
 * $Date: 2017-03-27 23:33:45 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * Design Channel server here.
 * 
 * @author JenChieh
 */
public class JCSNetS_ChannelServer extends JCSNetS_Server {
    
    private static ArrayList<JCSNetS_ChannelServer> CHANNEL_SERVERS = null;
    private JCSNetS_MapFactory mapFactory = null;
    
    
    private JCSNetS_ChannelServer(int port) {
        super(port, PacketProcessor.Mode.CHANNELSERVER);
    }
    
    public static JCSNetS_ChannelServer newInstance(int port) {
        return new JCSNetS_ChannelServer(port);
    }
    
    /**
     * Get the array list of channel server.
     * @return { ArrayList<JCSNetS_ChannelServer> } : array list of channel server. 
     */
    public static ArrayList<JCSNetS_ChannelServer> getInstance() {
        if (CHANNEL_SERVERS == null) {
            CHANNEL_SERVERS = new ArrayList<JCSNetS_ChannelServer>();
            startChannel_Main();
        }
        return CHANNEL_SERVERS;
    }
    
    /**
     * Main entry for channel server, this should only call once in 
     * 'getInstance' function.
     */
    private static void startChannel_Main() {
        int channelCount = Integer.parseInt(ServerProperties.getProperty("jcs.Count"));
        
        for (int count = 0; count < channelCount; ++count) {
            JCSNetS_ChannelServer newCS = newInstance(Integer.parseInt(ServerProperties.getProperty("jcs.Port")) + count);
            newCS.run();
            CHANNEL_SERVERS.add(newCS);
        }
    }
    
    public JCSNetS_MapFactory getMapFactory() {
        return this.mapFactory;
    }
}

