package com.aldes.jcsnets.server;

/**
 * $File: ProtocolType.java $
 * $Date: 2017-08-31 15:44:35 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public enum ProtocolType {
    TCP("TCP"),
    UDP("UDP")
    ;
    
    private final String type;
    
    private ProtocolType(final String type) {
        this.type = type;
    }
    
    public String toString() {
        return type;
    }
}
