package com.aldes.jcsnets.client;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.constants.JCSNetS_Constants;
import com.aldes.jcsnets.net.PacketProcessor;
import com.aldes.jcsnets.net.channel.JCSNetS_ChannelServer;
import com.aldes.jcsnets.net.login.JCSNetS_LoginServer;
import com.aldes.jcsnets.server.JCSNetS_Server;
import com.aldes.jcsnets.server.ProtocolType;
import com.aldes.jcsnets.server.TimerManager;
import com.aldes.jcsnets.server.map.JCSNetS_Map;
import com.aldes.jcsnets.tools.JCSNetS_PacketCreator;

/**
 * $File: JCSNetS_Client.java $
 * $Date: 2017-03-27 23:44:28 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_Client
 * @brief
 */
public class JCSNetS_Client {

    public static final String CLIENT_KEY = "CLIENT";
    private static final Logger log = LoggerFactory.getLogger(JCSNetS_Client.class);
    private IoSession session;

    private long lastPong;
    private boolean gm = false;
    private boolean loggedIn = false;
    
    private JCSNetS_Character player = new JCSNetS_Character(this);
    private int channel = 1;
    
    private JCSNetS_LoginServer loginServer = null;
    private JCSNetS_ChannelServer channelServer = null;
    
    // this is for UDP packet No. check.
    // store all packet number by packet id.
    private long[] packetNumbers = null;
    

    public JCSNetS_Client(IoSession session) {
        this.session = session;
        
        this.loginServer = JCSNetS_LoginServer.getInstance();
        this.channelServer = JCSNetS_ChannelServer.getInstance(this.channel);
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public synchronized IoSession getSession() {
        return session;
    }

    public void pongReceived() {
        lastPong = System.currentTimeMillis();
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    
    public void setIsLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        
        if (this.isLoggedIn() != loggedIn) {
            // if not the same val then, meaning the whole packet switch
            // to the new packet processor.
            this.loggedIn = loggedIn;
            resetPacketNumbers(getPacketProcessor().getHandlers().length);
        }
        this.loggedIn = loggedIn;
    }

    public void sendPing() {
        final long then = System.currentTimeMillis();
        getSession().write(JCSNetS_PacketCreator.getPing());
        TimerManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    if (lastPong - then < 0) {
                        if (getSession().isConnected()) {
                            System.out.println("Auto DC : " + session.getRemoteAddress().toString() + " : Ping Timeout.");
                            getSession().close(true);
                        }
                    }
                } catch (NullPointerException e) {
                }
            }
        }, 15000);

    }

    /**
     * Do all the disconnect task
     */
    public void disconnect() {
        JCSNetS_Map currentMap = this.getPlayer().getMap();
        if (currentMap != null)
            this.getPlayer().getMap().removePlayer(this.getPlayer());
    }

    public JCSNetS_Character getPlayer() {
        return this.player;
    }
    
    public JCSNetS_Server getCurrentServer() {
        if (isLoggedIn()) {
            return getChannelServer();
        }
        return getLoginServer();
    }
    
    /**
     * Is the current server the order check server?
     * Meaning that we need to check packet No. before we do any process.
     * @return
     */
    public boolean isOrderCheckServer() {
        return (this.getCurrentServer().getProtocolType() == ProtocolType.UDP);
    }
    
    /**
     * Get the processor by the current client's server is on.
     * @return { PacketProcessor } : current packet processor this client on.
     */
    public PacketProcessor getPacketProcessor() {
        if (isLoggedIn()) {
            return PacketProcessor.getProcessor(PacketProcessor.Mode.CHANNELSERVER);
        }
        return PacketProcessor.getProcessor(PacketProcessor.Mode.LOGINSERVER);
    }
    
    public JCSNetS_LoginServer getLoginServer() {
        return this.loginServer;
    }
    
    public JCSNetS_ChannelServer getChannelServer() {
        return this.channelServer;
    }
    
    /**
     * Reset packet numbers array by using packet handlers count.
     * 
     * @param handlersLength : length of the handlers array.
     */
    public void resetPacketNumbers(int handlersLength) {
        this.packetNumbers = new long[handlersLength];
        for (int count = 0; count < packetNumbers.length; ++count) {
            // all packet number start at -1.
            this.packetNumbers[count] = -1;
        }
    }
    
    /* Reset packet numbers array by using packet handlers count. */
    public void resetPacketNumbers() {
        resetPacketNumbers(getPacketProcessor().getHandlers().length);
    }
    
    public void setPacketNumber(short packetId, long packetNumber) {
        this.packetNumbers[packetId] = packetNumber;
        
        /*
         * Check if the packet numbers is overflow.
         * 
         * Limit the range of the generic data type, prevent 
         * overflow issue.
         */
        if (this.packetNumbers[packetId] > JCSNetS_Constants.MAX_PACKET_NUMBER) {
            this.packetNumbers[packetId] = -1;
        }
    }
    
    public long getPacketNumber(short packetId) {
        return this.packetNumbers[packetId];
    }
    
}

