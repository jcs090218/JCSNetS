package com.aldes.jcsnets.tools.data.output;

import java.io.ByteArrayOutputStream;

import com.aldes.jcsnets.net.ByteArrayJCSNetS_Packet;
import com.aldes.jcsnets.net.JCSNetS_Packet;
import com.aldes.jcsnets.tools.HexTool;

public class JCSNetS_PacketLittleEndianWriter extends GenericLittleEndianWriter {
    private ByteArrayOutputStream baos;

    /**
     * Constructor - initializes this stream with a default size.
     */
    public JCSNetS_PacketLittleEndianWriter() {
        this(32);
    }

    /**
     * Constructor - initializes this stream with size <code>size</code>.
     *
     * @param size The size of the underlying stream.
     */
    public JCSNetS_PacketLittleEndianWriter(int size) {
        this.baos = new ByteArrayOutputStream(size);
        setByteOutputStream(new BAOSByteOutputStream(baos));
    }

    /**
     * Gets a <code>JCSNetS_Packet</code> instance representing this
     * sequence of bytes.
     *
     * @return A <code>JCSNetS_Packet</code> with the bytes in this stream.
     */
    public JCSNetS_Packet getPacket() {
        return new ByteArrayJCSNetS_Packet(baos.toByteArray());
    }

    /**
     * Changes this packet into a human-readable hexadecimal stream of bytes.
     *
     * @return This packet as hex digits.
     */
    @Override
    public String toString() {
        return HexTool.toString(baos.toByteArray());
    }
}
