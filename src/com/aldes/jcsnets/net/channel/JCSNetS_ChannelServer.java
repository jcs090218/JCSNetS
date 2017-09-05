package com.aldes.jcsnets.net.channel;

import java.util.HashMap;
import java.util.Map;

import com.aldes.jcsnets.client.JCSNetS_Character;
import com.aldes.jcsnets.net.PacketProcessor;
import com.aldes.jcsnets.server.JCSNetS_Server;
import com.aldes.jcsnets.server.ProtocolType;
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
    
    private static Map<Integer, JCSNetS_ChannelServer> instances = null;
    private JCSNetS_MapFactory mapFactory = null;
    private JCSNetS_PlayerStorage players = new JCSNetS_PlayerStorage();
    
    
    private JCSNetS_ChannelServer(int port, ProtocolType type, int channel) {
        super(port, PacketProcessor.Mode.CHANNELSERVER, type);
        
        setChannel(channel);
        
        /* Create instances for this channel. */
        this.mapFactory = new JCSNetS_MapFactory();
    }
    
    public static JCSNetS_ChannelServer newInstance(int port, ProtocolType type, int channel) {
        return new JCSNetS_ChannelServer(port, type, channel);
    }
    
    public static Map<Integer, JCSNetS_ChannelServer> getInstances() {
        // give default as TCP protocol.
        return getInstances(ProtocolType.TCP);
    }
    
    /**
     * Get the Map of channel servers.
     * 
     * @param { ProtocolType } type : type of the server protocol.
     * @return { Map<Integer, JCSNetS_ChannelServer> } : array list of channel server. 
     */
    public static Map<Integer, JCSNetS_ChannelServer> getInstances(ProtocolType type) {
        if (instances == null) {
            instances = new HashMap<Integer, JCSNetS_ChannelServer>();
            startChannel_Main(type);
        }
        return instances;
    }
    
    /**
     * Get a channel server that we have runs.
     * 
     * @param channel : channel id.
     * @return { JCSNetS_ChannelServer } : channel server handle object.
     */
    public static JCSNetS_ChannelServer getInstance(int channel) {
        return instances.get(channel);
    }
    
    /**
     * Main entry for channel server, this should only call once in 
     * 'getInstance' function.
     */
    private static void startChannel_Main(ProtocolType type) {
        int channelCount = Integer.parseInt(ServerProperties.getProperty("jcs.Count"));
        
        for (int count = 0; count < channelCount; ++count) {
            int channel = count + 1;
            
            JCSNetS_ChannelServer newCS = newInstance(
                    Integer.parseInt(ServerProperties.getProperty("jcs.Port")) + count, 
                    type, 
                    channel);
            newCS.run();
            // here we treat port as the channel id.
            instances.put(channel, newCS);
        }
    }
    
    public JCSNetS_MapFactory getMapFactory() {
        return this.mapFactory;
    }
    
    public IPlayerStorage getPlayerStorage() {
        return this.players;
    }
    
    public void addPlayer(JCSNetS_Character chr) {
        this.players.registerPlayer(chr);
    }
    
    public void removePlayer(JCSNetS_Character chr) {
        players.deregisterPlayer(chr);
    }
    
    public int getConnectedClients() {
        return players.getAllCharacters().size();
    }

}

