package com.aldes.jcsnets.net.channel;

import java.util.Collection;

import com.aldes.jcsnets.client.JCSNetS_Character;

/**
 * $File: IPlayerStorage.java $
 * $Date: 2017-09-04 11:03:31 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public interface IPlayerStorage {

    public JCSNetS_Character getCharacterByName(String name);

    public JCSNetS_Character getCharacterById(int id);

    Collection<JCSNetS_Character> getAllCharacters();
}
