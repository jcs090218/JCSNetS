package com.aldes.jcsnets.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.aldes.jcsnets.handling.ExternalCodeTableGetter;
import com.aldes.jcsnets.handling.WritableIntValueHolder;


/**
 * @enum RecvPacketOpcode
 * @brief 
 *
 */
public enum RecvPacketOpcode implements WritableIntValueHolder {
    // GENERIC
    PONG,
    MAX_PACKET,
    
    // LOGIN
    REGISTER_LOGIN,
    DEREGISTER_LOGIN,
    CREATE_CHARACTER,
    
    // CHANNEL
    MOVE_PLAYER,
    SHOOT_BULLET,
    USE_ITEM,
    KILL_MONSTER
    ;

    private short code = -2;

    public void setValue(short code) {
        this.code = code;
    }

    @Override
    public short getValue() {
        return code;
    }

    public static Properties getDefaultProperties() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("recvops.properties");
        props.load(fis);
        fis.close();
        return props;
    }


    static {
        try {
            ExternalCodeTableGetter.populateValues(getDefaultProperties(), values());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load recvops", e);
        }
    }

}
