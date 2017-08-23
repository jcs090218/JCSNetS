package com.aldes.jcsnets.net;

import com.aldes.jcsnets.tools.HexTool;

public class ByteArrayJCSNetS_Packet implements JCSNetS_Packet {

    public static final long serialVersionUID = -7997681658570958848L;
    private byte[] data;
    private Runnable onSend;

    public ByteArrayJCSNetS_Packet(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] getBytes() {
        return data;
    }

    @Override
    public String toString() {
        return HexTool.toString(data);
    }

    public Runnable getOnSend() {
        return onSend;
    }

    public void setOnSend(Runnable onSend) {
        this.onSend = onSend;
    }
}
