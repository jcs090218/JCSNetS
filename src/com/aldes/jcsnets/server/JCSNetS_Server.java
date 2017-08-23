package com.aldes.jcsnets.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.net.JCSNetS_ServerHandler;
import com.aldes.jcsnets.net.PacketProcessor;
import com.aldes.jcsnets.net.mina.JCSNetS_CodecFactory;
import com.aldes.jcsnets.util.JCSNetS_Logger;

/**
 * $File: JCSNetS_Server.java $
 * $Date: 2017-08-23 16:31:45 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_Server implements Runnable {
    
    private static Logger log = LoggerFactory.getLogger(JCSNetS_Server.class);
    
    private PacketProcessor processor = null;  // singleton
    private PacketProcessor.Mode mode = null;
    private int channel = -1;
    
    // default port
    private int port = 8585;
    
    
    public JCSNetS_Server(int port, PacketProcessor.Mode mode) {
        setPacketProcessorMode(mode);
        setPort(port);
        
        if (mode == PacketProcessor.Mode.LOGINSERVER) {
            this.channel = port - Integer.parseInt(ServerProperties.getProperty("jcs.LPort")) + 1;
        } else if (mode == PacketProcessor.Mode.CHANNELSERVER) {
            this.channel = port - Integer.parseInt(ServerProperties.getProperty("jcs.Port")) + 1;
        }
    }
    
    protected void printConnectInfo(int port) {
        if (mode == PacketProcessor.Mode.LOGINSERVER) {
            JCSNetS_Logger.println("Login   " + channel + ": Listening on port " + port + "");
        } else if (mode == PacketProcessor.Mode.CHANNELSERVER) {
            JCSNetS_Logger.println("Channel " + channel + ": Listening on port " + port + "");
        }
    }
    
    protected void printConnectError(Exception e) {
        if (mode == PacketProcessor.Mode.LOGINSERVER) {
            JCSNetS_Logger.println("服務端異常..." + e);
        } else if (mode == PacketProcessor.Mode.CHANNELSERVER) {
            JCSNetS_Logger.println("Binding to port " + port + " failed (ch: " + getChannel() + ")" + e);
        }
    }

    @Override
    public void run() {
        IoAcceptor acceptor;
        
        try{
            acceptor = new NioSocketAcceptor();
            
            // 編碼/解碼器
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new JCSNetS_CodecFactory()));
            // 設置線程池
            acceptor.getFilterChain().addLast("threadPool",
                    new ExecutorFilter(Executors.newCachedThreadPool()));
            
            acceptor.getSessionConfig().setReadBufferSize(2024);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
            
            acceptor.setHandler(new JCSNetS_ServerHandler(processor));
            
            acceptor.bind(new InetSocketAddress(port));
            printConnectInfo(port);
            
        }catch(Exception e){
            printConnectError(e);
            e.printStackTrace();
        }
    }
    
    public void setPacketProcessorMode(PacketProcessor.Mode mode) {
        this.mode = mode;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
}
