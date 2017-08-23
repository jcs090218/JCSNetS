package com.aldes.jcsnets.tools.data.input;

/**
 * $File: LittleEndianAccessor.java $
 * $Date: 2017-03-28 00:26:33 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @inter LittleEndianAccessor
 * @brief 
 */
public interface LittleEndianAccessor {
	 /**
     * Reads a byte.
     *
     * @return The byte read.
     */
    byte readByte();
    /**
     * Reads a character.
     *
     * @return The character read.
     */
    char readChar();
    /**
     * Reads a short integer.
     *
     * @return The short integer read.
     */
    short readShort();
    /**
     * Reads a integer.
     *
     * @return The integer read.
     */
    int readInt();
    /**
     * Reads a long integer.
     *
     * @return The long integer read.
     */
    long readLong();
    /**
     * Skips ahead <code>num</code> bytes.
     *
     * @param num Number of bytes to skip ahead.
     */
    void skip (int num);
    /**
     * Reads a number of bytes.
     *
     * @param num The number of bytes to read.
     * @return The bytes read.
     */
    byte []read(int num);

    /**
     * Reads a floating point integer.
     *
     * @return The floating point integer read.
     */
    float readFloat();
    /**
     * Reads a double-precision integer.
     *
     * @return The double-precision integer read.
     */
    double readDouble();

    /**
     * Reads an ASCII string.
     *
     * @return The string read.
     */
    String readAsciiString(int n);
    /**
     * Reads a null-terminated ASCII string.
     *
     * @return The string read.
     */
    String readNullTerminatedAsciiString();
    /**
     * Reads a JCSNetS convention length ASCII string.
     *
     * @return The string read.
     */
    String readJCSNetSAsciiString();
    /**
     * Read char in sequence until terminated character appear.
     * 
     * @return The string read.
     */
    String readNullTerminatedUnicodeString();
    /**
     * Read char in sequence until terminated character appear.
     * 
     * @return The string read.
     */
    String readNullTerminatedUTF8String();
    /**
     * Read char in sequence until terminated character appear.
     * 
     * @return The string read.
     */
    String readNullTerminatedUTF16String();
    /**
     * Read char in sequence until terminated character appear.
     * 
     * @return The string read.
     */
    String readNullTerminatedUTF32String();

    /**
     * Gets the number of bytes read so far.
     *
     * @return The number of bytes read as an long integer.
     */
    long getBytesRead();
    /**
     * Gets the number of bytes left for reading.
     *
     * @return The number of bytes left for reading as an long integer.
     */
    long available();
}
