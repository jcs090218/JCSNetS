package com.aldes.jcsnets.net.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * $File: JCSNetS_CodecFactory.java $
 * $Date: 2017-03-27 23:12:29 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * @class JCSNetS_CodecFactory
 * @brief Produce encoder and decoder codec.
 */
public class JCSNetS_CodecFactory implements ProtocolCodecFactory {

    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;

    /**
     * @func JCSNetS_CodecFactory
     * @brief constructor
     */
    public JCSNetS_CodecFactory() {
        this.encoder = new JCSNetS_PacketEncoder();
        this.decoder = new JCSNetS_PacketDecoder();
    }

    
    /* getter */

    @Override
    public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
        return this.decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
        return this.encoder;
    }

}
