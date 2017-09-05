package com.aldes.jcsnets.net.channel;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.aldes.jcsnets.client.JCSNetS_Character;

/**
 * $File: JCSNetS_PlayerStorge.java $
 * $Date: 2017-09-04 10:48:16 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_PlayerStorage implements IPlayerStorage {
    private Map<String, JCSNetS_Character> nameToChar = new LinkedHashMap<String, JCSNetS_Character>();
    private Map<Integer, JCSNetS_Character> idToChar = new LinkedHashMap<Integer, JCSNetS_Character>();

    public void registerPlayer(JCSNetS_Character chr) {
        nameToChar.put(chr.getName().toLowerCase(), chr);
        idToChar.put(chr.getId(), chr);
    }

    public void deregisterPlayer(JCSNetS_Character chr) {
        nameToChar.remove(chr.getName().toLowerCase());
        idToChar.remove(chr.getId());
    }

    public JCSNetS_Character getCharacterByName(String name) {
        return nameToChar.get(name.toLowerCase());
    }

    public JCSNetS_Character getCharacterById(int id) {
        return idToChar.get(Integer.valueOf(id));
    }

    public Collection<JCSNetS_Character> getAllCharacters() {
        return nameToChar.values();
    }
}
