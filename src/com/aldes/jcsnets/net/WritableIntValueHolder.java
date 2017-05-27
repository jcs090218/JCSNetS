package com.aldes.jcsnets.net;

/**
 * $File: WritableIntValueHolder.java $
 * $Date: 2017-03-28 00:34:14 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @inter WritableIntValueHolder
 * @breif
 */
public interface WritableIntValueHolder extends IntValueHolder {
    public void setValue(int newval);

    int getValue();
}
