package com.aldes.jcsnets.net.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.database.DatabaseConnection;
import com.aldes.jcsnets.net.PacketProcessor;
import com.aldes.jcsnets.server.JCSNetS_Server;
import com.aldes.jcsnets.server.ProtocolType;
import com.aldes.jcsnets.server.ServerProperties;
import com.aldes.jcsnets.util.JCSNetS_Logger;

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
public class JCSNetS_LoginServer extends JCSNetS_Server {

    private static JCSNetS_LoginServer instance = null;
    private static ArrayList<JCSNetS_Client> clients = new ArrayList<JCSNetS_Client>();
    
    // current unique client id.
    private static long clientId = -1L;
    
    private int loginInterval = Integer.parseInt(ServerProperties.getProperty("jcs.LoginInterval"));
    private int userLimit = Integer.parseInt(ServerProperties.getProperty("jcs.UserLimit"));


    private JCSNetS_LoginServer(ProtocolType type) {
        super(Integer.parseInt(ServerProperties.getProperty("jcs.LPort")), PacketProcessor.Mode.LOGINSERVER, type);
    }
    
    public static JCSNetS_LoginServer getInstance() {
        // give default as TCP protocol.
        return getInstance(ProtocolType.TCP);
    }
    
    public static JCSNetS_LoginServer getInstance(ProtocolType type) {
        if (instance == null) {
            instance = new JCSNetS_LoginServer(type);
            instance.run();
        }
        return instance;
    }

    public void resgister(JCSNetS_Client client) {
        if (clients.contains(client))
            return;
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
    
    public static long getUniqueClientId() {
        if (clientId >= Long.MAX_VALUE) {
            JCSNetS_Logger.println("Unique ID of range : " + clientId);
        }
        return ++clientId;
    }
    
    public int getLoginInterval() {
        return this.loginInterval;
    }
    
    public int getPossibleLogins() {
        int ret = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement limitCheck = con.prepareStatement("SELECT COUNT(*) FROM accounts WHERE loggedin > 1 AND gm = 0");
            ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                int usersOn = rs.getInt(1);
                if (usersOn < userLimit) {
                    ret = userLimit - usersOn;
                }
            }
            rs.close();
            limitCheck.close();
        } catch (Exception ex) {
            System.out.println("loginlimit error" + ex);
        }
        return ret;
    }
    
    public void reconnectWorld() {
        
    }

}
