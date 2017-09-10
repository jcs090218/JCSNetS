package com.aldes.jcsnets.server.map;

import java.util.LinkedHashSet;

import com.aldes.jcsnets.client.JCSNetS_Character;
import com.aldes.jcsnets.net.JCSNetS_Packet;

/**
 * $File: JCSNetS_Map.java $
 * $Date: 2017-03-28 00:45:09 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_Map {

    private int mapid;
    private String mapName;
    private String streetName;
    private int channel = -1;
    
    private LinkedHashSet<JCSNetS_Character> characters = new LinkedHashSet<JCSNetS_Character>();


    public JCSNetS_Map(int mapid, int channel) {
        this.mapid = mapid;
        this.channel = channel;
    }
    
    /**
     * Broadcast message no matter what.
     * @param packet : message/packet to broadcast.
     */
    public void broadcastMessage(JCSNetS_Packet packet) {
        broadcastMessage(null, packet);
    }
    
    /**
     * Broadcast message except the source.
     * @param source : source client.
     * @param packet : message/packet to broadcast.
     */
    private void broadcastMessage(JCSNetS_Character source, JCSNetS_Packet packet) {
        synchronized (characters) {
            for (JCSNetS_Character chr : characters) {
                if (chr != source) {
                    chr.getClient().getSession().write(packet);
                }
            }
        }
    }

    public int getMapid() {
        return this.mapid;
    }

    public void setMapid(int mapid) {
        this.mapid = mapid;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return this.streetName;
    }
    
    public void addPlayer(JCSNetS_Character chr) {
        synchronized (characters) {
            this.characters.add(chr);
        }
    }
    
    public void removePlayer(JCSNetS_Character chr) {
        synchronized (characters) {
            characters.remove(chr);
        }
    }

}
