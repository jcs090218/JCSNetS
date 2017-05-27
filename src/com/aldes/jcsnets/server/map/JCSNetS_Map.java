package com.aldes.jcsnets.server.map;

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


    public JCSNetS_Map() {

    }

    public int getMapid() {
        return this.mapid;
    }

    public void setMapid(int mapid) {
        this.mapid = mapid;
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

}
