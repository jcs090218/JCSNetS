package com.aldes.jcsnets.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ServerProperties {
    private static final Properties props = new Properties();

    private static final String[] toLoad = {
        "Settings.ini"
    };
    
    private ServerProperties() {
    }

    static {
        for (String s : toLoad) {
            InputStreamReader fr;
            try {
                fr = new InputStreamReader(new FileInputStream(s), "UTF-8");
                props.load(fr);
                fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
//        try {
//            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM auth_server_channel_ip");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                props.put(rs.getString("name") + rs.getInt("channelid"), rs.getString("value"));
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.exit(0);  //Big ass error.
//        }
    }

    public static String getProperty(String s) {
        return props.getProperty(s);
    }

    public static void setProperty(String prop, String newInf) {
        props.setProperty(prop, newInf);
    }

    public static String getProperty(String s, String def) {
        return props.getProperty(s, def);
    }
}
