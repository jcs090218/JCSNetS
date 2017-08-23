package com.aldes.jcsnets.server.map;

import java.util.HashMap;
import java.util.Map;

/**
 * $File: JCSNetS_MapFactory.java $
 * $Date: 2017-08-23 14:01:35 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * Map design here...
 * 
 * @author JenChieh
 */
public class JCSNetS_MapFactory {
    
    private Map<Integer, JCSNetS_Map> maps = new HashMap<Integer, JCSNetS_Map>();
    private int channel = -1;
    
    public JCSNetS_MapFactory() {
        
    }
    
    public JCSNetS_Map getMap(int mapid) {
        Integer omapid = Integer.valueOf(mapid);
        JCSNetS_Map map = maps.get(omapid);
        
        if (map == null) {
            synchronized (this) {
                // check if someone else who was also synchronized has 
                // loaded the map already
                map = maps.get(omapid);
                if (map != null) {
                    return map;
                }
                
                
            }
        }
        
        return map;
    }
    
    public int getLoadedMaps() {
        return maps.size();
    }
    
    public void setChannel(int channel) {
        this.channel = channel;
    }
    
    public void disposeMap(int mapId) {
        synchronized (maps) {
            if (maps.containsKey(mapId)) {
                maps.remove(mapId);
            }
        }
    }
    
}
