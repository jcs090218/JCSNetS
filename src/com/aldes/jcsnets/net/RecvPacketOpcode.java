package com.aldes.jcsnets.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * @enum RecvPacketOpcode
 * @brief 
 *
 */
public enum RecvPacketOpcode implements WritableIntValueHolder {
    // GENERIC
    PONG, 
    
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

    private int code = -2;

    public void setValue(int code) {
        this.code = code;
    }

    @Override
    public int getValue() {
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
