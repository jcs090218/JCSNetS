package com.aldes.jcsnets.tools.data.output;


/**
 * @inter LittleEndianWriter
 * @brief
 *
 */
public interface LittleEndianWriter {
    /**
     * Write an array of bytes to the sequence.
     *
     * @param b The bytes to write.
     */
    public void write(byte b[]);

    /**
     * Write a byte to the sequence.
     *
     * @param b The byte to write.
     */
    public void write(byte b);

    /**
     * Write a byte in integer form to the sequence.
     *
     * @param b The byte as an <code>Integer</code> to write.
     */
    public void write(int b);

    /**
     * Writes an integer to the sequence.
     *
     * @param i The integer to write.
     */
    public void writeInt(int i);
    
    /**
     * Writes an float to the sequence.
     *
     * @param f The float to write.
     */
    public void writeFloat(float f);

    /**
     * Write a short integer to the sequence.
     *
     * @param s The short integer to write.
     */
    public void writeShort(int s);

    /**
     * Write a long integer to the sequence.
     * @param l The long integer to write.
     */
    public void writeLong(long l);

    /**
     * Writes an ASCII string the the sequence.
     *
     * @param s The ASCII string to write.
     */
    void writeAsciiString(String s);

    /**
     * Writes a null-terminated ASCII string to the sequence.
     *
     * @param s The ASCII string to write.
     */
    void writeNullTerminatedAsciiString(String s);

    /**
     * Writes a JCSNetS-convention ASCII string to the sequence.
     *
     * @param s The ASCII string to use maple-convention to write.
     */
    void writeJCSNetS_AsciiString(String s);
    
    /**
     * Write a null-terminated Unicode string to sequence.
     * 
     * @param s : The Unicode string to write.
     */
    void writeNullTerminatedUnicodeString(String s);
    
    /**
     * Write a null-terminated UTF-8 string to sequence.
     * 
     * @param s : The UTF-8 string to write.
     */
    void writeNullTerminatedUTF8String(String s);
    
    /**
     * Write a null-terminated UTF-16 string to sequence.
     * 
     * @param s : The UTF-16 string to write.
     */
    void writeNullTerminatedUTF16String(String s);
    
    /**
     * Write a null-terminated UTF-32 string to sequence.
     * 
     * @param s : The UTF-32 string to write.
     */
    void writeNullTerminatedUTF32String(String s);
}
