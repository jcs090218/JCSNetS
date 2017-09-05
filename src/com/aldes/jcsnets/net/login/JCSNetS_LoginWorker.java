package com.aldes.jcsnets.net.login;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.client.JCSNetS_Client;

/**
 * $File: JCSNetS_LoginWorker.java $
 * $Date: 2017-09-05 12:56:08 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_LoginWorker implements Runnable {
    private static JCSNetS_LoginWorker instance = new JCSNetS_LoginWorker();
    private Deque<JCSNetS_Client> waiting;
    private Set<String> waitingNames;
    private List<Integer> possibleLoginHistory = new LinkedList<Integer>();
    public static Logger log = LoggerFactory.getLogger(JCSNetS_LoginWorker.class);

    
    private JCSNetS_LoginWorker() {
        waiting = new LinkedList<JCSNetS_Client>();
        waitingNames = new HashSet<String>();
    }
    
    public static JCSNetS_LoginWorker getInstance() {
        return instance;
    }
    
    @Override
    public void run() {
        try {
            int possibleLogins = JCSNetS_LoginServer.getInstance().getPossibleLogins();
            
        } catch (Exception ex) {
            JCSNetS_LoginServer.getInstance().reconnectWorld();
        }
    }
    
    public void registerClient(JCSNetS_Client c) {
        synchronized (waiting) {
            if (!waiting.contains(c) && !waitingNames.contains(c.getAccountName().toLowerCase())) {
                waiting.add(c);
                waitingNames.add(c.getAccountName().toLowerCase());
                c.updateLoginState(JCSNetS_Client.LOGIN_WAITING);
            }
        }
    }

    public void registerGMClient(JCSNetS_Client c) {
        synchronized (waiting) {
            if (!waiting.contains(c) && !waitingNames.contains(c.getAccountName().toLowerCase())) {
                waiting.addFirst(c);
                waitingNames.add(c.getAccountName().toLowerCase());
                c.updateLoginState(JCSNetS_Client.LOGIN_WAITING);
            }
        }
    }

    public void deregisterClient(JCSNetS_Client c) {
        synchronized (waiting) {
            waiting.remove(c);
            if (c.getAccountName() != null) {
                waitingNames.remove(c.getAccountName().toLowerCase());
            }
        }
    }
    
    public double getPossibleLoginAverage() {
        int sum = 0;
        for (Integer i : possibleLoginHistory) {
            sum += i;
        }
        return (double) sum / (double) possibleLoginHistory.size();
    }

    public int getWaitingUsers() {
        return waiting.size();
    }
}
