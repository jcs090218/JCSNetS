package com.aldes.jcsnets.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

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
    
    protected ProtocolType protocolType = ProtocolType.TCP;
    
    protected int channel = -1;
    
    protected PacketProcessor processor = null;  // singleton
    protected PacketProcessor.Mode mode = null;
    protected int port = 8585;  // default port
    
    
    public JCSNetS_Server(int port, PacketProcessor.Mode mode, ProtocolType type) {
        setPacketProcessorMode(mode);
        setPort(port);
        setProtocolType(type);
        
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
        // run server base ont the type of the protocol.
        if (protocolType == ProtocolType.TCP) {
            tcpRun();
        } else if (protocolType == ProtocolType.UDP) {
            udpRun();
        }
    }
    
    public void setPacketProcessorMode(PacketProcessor.Mode mode) {
        this.mode = mode;
        this.processor = PacketProcessor.getProcessor(mode);
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public void setChannel(int channel) {
        this.channel = channel;
    }
    
    public void setProtocolType(ProtocolType type) {
        this.protocolType = type;
    }
    
    public ProtocolType getProtocolType() {
        return this.protocolType;
    }
    
    /**
     * Program Entry for TCP server.
     */
    private void tcpRun() {
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
    
    /**
     * Program Entry for UDP server.
     */
    private void udpRun() {
        IoAcceptor acceptor;
        
        try{
            acceptor = new NioDatagramAcceptor();
            
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
    
}
