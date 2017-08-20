package com.aldes.jcsnets.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.aldes.jcsnets.handling.ExternalCodeTableGetter;
import com.aldes.jcsnets.handling.WritableIntValueHolder;

public enum SendPacketOpcode implements WritableIntValueHolder {
    // GENERAL
    PING,
    
    // LOGIN
    LOGIN_STATUS,
    
    // CHANNEL
    ;
    
    private short code = -2;
    
    @Override
    public void setValue(short code) {
        this.code = code;
    }

    @Override
    public short getValue() {
        return code;
    }

    public static Properties getDefaultProperties() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        FileInputStream fileInputStream = new FileInputStream("sendops.properties");
        props.load(fileInputStream);
        fileInputStream.close();
        return props;
    }

    static {
        reloadValues();
    }

    public static final void reloadValues() {
        try {
            ExternalCodeTableGetter.populateValues(getDefaultProperties(), values());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load sendops", e);
        }
    }
}
