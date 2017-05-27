package com.aldes.jcsnets.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldes.jcsnets.client.JCSNetS_Client;
import com.aldes.jcsnets.server.JCSNetS_LoginServer;
import com.aldes.jcsnets.tools.JCSNetS_PacketCreator;

/**
 * $File: JCSNetS_ServerHandler.java $
 * $Date: 2017-03-27 23:35:40 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_ServerHandler
 * @brief 
 */
public class JCSNetS_ServerHandler extends IoHandlerAdapter {
    
    private static Logger log = LoggerFactory.getLogger(JCSNetS_ServerHandler.class);
    
    private final static short BLACKVAULT_VERSION = 1;
    private PacketProcessor processor;
    private int channel = -1;
    
    public JCSNetS_ServerHandler(PacketProcessor processor) {
        this.processor = processor;
    }

    public JCSNetS_ServerHandler(PacketProcessor processor, int channel) {
        this.processor = processor;
        this.channel = channel;
    }
    
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
//        Runnable r = ((BlackVaultPacket) message).getOnSend();
//        if (r != null) {
//            r.run();
//        }
//        super.messageSent(session, message);
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("服務端與客戶端連接打開...");
        log.info("IoSession with {} opened", session.getRemoteAddress());
        
        // [IMPORTATN] Check the client are good client not something else here!!
        
        JCSNetS_Client client = new JCSNetS_Client();
        client.setSession(session);
        JCSNetS_LoginServer.getClients().add(client);
        
        byte[] buffer = JCSNetS_PacketCreator.getHello();
        
        session.write(buffer);      // send the index to client
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        synchronized (session) {
            JCSNetS_Client client = (JCSNetS_Client) session.getAttribute(JCSNetS_Client.CLIENT_KEY);
            if (client != null) {
                client.disconnect();
                // remove the client from server
                JCSNetS_LoginServer.getInstance().deresgister(client);
                session.removeAttribute(JCSNetS_Client.CLIENT_KEY);
            }
        }
        super.sessionClosed(session);
        
        log.info("客戶端已經關閉連結...");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        byte[] content = (byte[]) message;
        
//      for(int i = 0; i < content.length; ++i)
//          log.info("服務端接收到的數據為: " + (char)content[i]);
        
        session.write(content);
        
//        SeekableLittleEndianAccessor slea = new GenericSeekableLittleEndianAccessor(new ByteArrayByteStream(content));
//        short packetId = slea.readShort();
//        BlackVaultClient client = (BlackVaultClient) session.getAttribute(BlackVaultClient.CLIENT_KEY);
//        BlackVaultPacketHandler packetHandler = processor.getHandler(packetId);
//
//        if (packetHandler != null && packetHandler.validateState(client)) {
//            try {
//              // handle pack here
//                packetHandler.handlePacket(slea, client);
//            } catch (Throwable t) {
//                System.out.println("Exception during processing packet: " + packetHandler.getClass().getName() + ": " + t.getMessage());
//            }
//        }        
        
        
//      // 不做任何更改, 直接傳送回給所有客戶端!
//      Collection<IoSession> sessions = session.getService().getManagedSessions().values();
//      for (IoSession ss : sessions) {
//          ss.write(content);
//      }
        
    }

    @Override
    public void sessionIdle(final IoSession session, final IdleStatus status) throws Exception {
        JCSNetS_Client client = (JCSNetS_Client) session.getAttribute(JCSNetS_Client.CLIENT_KEY);
        if (client != null && client.getPlayer() != null) {
            System.out.println("玩家 " + client.getPlayer().getName() + " 在掛網.");
        }
        if (client != null) {
            client.sendPing();
        }
        super.sessionIdle(session, status);
        
        log.info("服務端進入閒空狀態...");
    }
    
}
