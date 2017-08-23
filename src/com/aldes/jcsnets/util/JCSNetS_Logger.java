package com.aldes.jcsnets.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.server.ServerProperties;

/**
 * $File: JCSNetS_Logger.java $
 * $Date: 2017-08-23 16:33:18 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * Simple Logger with debug control.
 * 
 * @author JenChieh
 */
public class JCSNetS_Logger {
    public static boolean DEBUG_MODE = Boolean.parseBoolean(ServerProperties.getProperty("jcs.Debug"));
    
    
    public static void println() {
        if (!DEBUG_MODE)
            return;
        System.out.println();
    }
    
    public static void println(String msg) {
        if (!DEBUG_MODE)
            return;
        System.out.println(msg);
    }
}
