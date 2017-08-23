package com.aldes.jcsnets.server;

import java.util.ArrayList;

import com.aldes.jcsnets.client.JCSNetS_Client;

/**
 * $File: JCSNetS_LoginServer.java $
 * $Date: 2017-03-28 00:18:48 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * Simulator the login server...
 * in this version of game we does not have the
 * login server. Instead of the real login sever
 * we will just use to check in simple way!
 *
 * @author JenChieh(jcs090218@gmail.com)
 */
public class JCSNetS_LoginServer {

    private static JCSNetS_LoginServer instance = null;

    private static ArrayList<JCSNetS_Client> clients = new ArrayList<JCSNetS_Client>();

    
    private JCSNetS_LoginServer() {

    }

    public static JCSNetS_LoginServer getInstance() {
        if (instance == null)
            instance = new JCSNetS_LoginServer();
        return instance;
    }

    public void resgister(JCSNetS_Client client) {
        clients.add(client);
    }

    public void deresgister(JCSNetS_Client client) {
        for (int index = 0; index < clients.size(); ++index) {
            if (client == clients.get(index)) {
                clients.remove(index);
                break;
            }
        }
    }

    public static ArrayList<JCSNetS_Client> getClients() {
        return clients;
    }

}
