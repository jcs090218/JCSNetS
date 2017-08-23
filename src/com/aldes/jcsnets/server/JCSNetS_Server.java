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

/**
 * $File: JCSNetS_Server.java $
 * $Date: 2017-03-27 23:33:45 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class JCSNetS_Server implements Runnable {
    
    private static Logger log = LoggerFactory.getLogger(JCSNetS_Server.class);
    private PacketProcessor processor = null;  // singleton
    private PacketProcessor.Mode packetMode = null;
    
    public JCSNetS_Server(PacketProcessor.Mode packetMode) {
        this.packetMode = packetMode;
        this.processor = PacketProcessor.getProcessor(packetMode);
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
            
            
            int port = 8585;
            if (PacketProcessor.Mode.CHANNELSERVER == packetMode) {
                port = Integer.parseInt(ServerProperties.getProperty("jcs.Port"));
            } else if (PacketProcessor.Mode.LOGINSERVER == packetMode) {
                port = Integer.parseInt(ServerProperties.getProperty("jcs.LPort"));
            }
            
            acceptor.bind(new InetSocketAddress(port));
            log.info("服務端起動成功... 端口號為: " + port);
            
        }catch(Exception e){
            log.error("服務端異常...", e);
            e.printStackTrace();
        }
    }
    
}

