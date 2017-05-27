package com.aldes.jcsnets.net;

import com.aldes.jcsnets.net.channel.handler.MovePlayerHandler;
import com.aldes.jcsnets.net.handler.KeepAliveHandler;
import com.aldes.jcsnets.net.login.handler.DeregisterLoginHandler;
import com.aldes.jcsnets.net.login.handler.RegisterLoginHandler;

/**
 * $File: PacketProcessor.java $
 * $Date: 2017-03-27 23:37:45 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class PacketProcessor
 * @brief Process the packet.
 */
public class PacketProcessor {

     public enum Mode {
        LOGINSERVER,
        CHANNELSERVER
    };

    private static PacketProcessor instance;
    private JCSNetS_PacketHandler[] handlers;

    private PacketProcessor() {
        int maxRecvOp = 0;
        for (RecvPacketOpcode op : RecvPacketOpcode.values()) {
            if (op.getValue() > maxRecvOp) {
                maxRecvOp = op.getValue();
            }
        }
        handlers = new JCSNetS_PacketHandler[maxRecvOp + 1];
    }

    public JCSNetS_PacketHandler getHandler(short packetId) {
        if (packetId > handlers.length) {
            return null;
        }
        JCSNetS_PacketHandler handler = handlers[packetId];
        if (handler != null) {
            return handler;
        }
        return null;
    }

    public void registerHandler(RecvPacketOpcode code, JCSNetS_PacketHandler handler) {
        try {
            handlers[code.getValue()] = handler;
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println("Check your Recv Packet Opcodes - Something is wrong");
        }
    }

    public synchronized static PacketProcessor getProcessor(Mode mode) {
        if (instance == null) {
            instance = new PacketProcessor();
            //instance.reset(mode);
        }
        return instance;
    }

    public void reset(Mode mode) {
        handlers = new JCSNetS_PacketHandler[handlers.length];
        registerHandler(RecvPacketOpcode.PONG, new KeepAliveHandler());
        if (mode == Mode.LOGINSERVER) {
            registerHandler(RecvPacketOpcode.REGISTER_LOGIN, new RegisterLoginHandler());
            registerHandler(RecvPacketOpcode.DEREGISTER_LOGIN, new DeregisterLoginHandler());
        } else if (mode == Mode.CHANNELSERVER) {
            registerHandler(RecvPacketOpcode.MOVE_PLAYER, new MovePlayerHandler());
        } else {
            throw new RuntimeException("Unknown packet processor mode");
        }
    }

}
