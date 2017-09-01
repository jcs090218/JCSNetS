package com.aldes.jcsnets.database;

/**
 * $File: DatabaseException.java $
 * $Date: 2017-09-01 16:36:45 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = -420103154764822555L;

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
