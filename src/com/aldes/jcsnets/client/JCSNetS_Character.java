package com.aldes.jcsnets.client;

import com.aldes.jcsnets.server.map.JCSNetS_Map;
import com.aldes.jcsnets.tools.Vector2f;

/**
 * $File: JCSNetS_Character.java $
 * $Date: 2017-03-28 00:40:58 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_Character
 * @brief Character in the game.
 */
public class JCSNetS_Character {

    private String name = new String("JCSNetS Default Player Name");
    
    private JCSNetS_Client client = null;
    private Vector2f position = new Vector2f();
    
    private JCSNetS_Map map = null;
    private int mapid = -1;


    public JCSNetS_Character(JCSNetS_Client client) {
        this.client = client;
    }

    public JCSNetS_Map getMap() {
        return this.map;
    }

    public void setMap(JCSNetS_Map map) {
        this.map = map;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public void setPosition(Vector2f pos) {
        this.position = pos;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMapid(int mapid) {
        this.mapid = mapid;
    }

    public int getMapid() {
        return this.mapid;
    }
    
    public JCSNetS_Client getClient() {
        return this.client;
    }

}
