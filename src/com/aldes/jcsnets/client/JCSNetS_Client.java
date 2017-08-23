package com.aldes.jcsnets.client;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.server.TimerManager;
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

    private JCSNetS_Character player = new JCSNetS_Character(this);
    private long lastPong;
    private boolean gm = false;
    private boolean loggedIn = false;
    

    public JCSNetS_Client(IoSession session) {
        this.session = session;
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
                            getSession().close();
                        }
                    }
                } catch (NullPointerException e) {
                }
            }
        }, 15000);

    }

    public void disconnect() {
        // Do all the disconnect task
    }

    public JCSNetS_Character getPlayer() {
        return this.player;
    }
    
}

