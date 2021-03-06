package com.aldes.jcsnets.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.aldes.jcsnets.server.ServerProperties;

/**
 * $File: DatabaseConnection.java $
 * $Date: 2017-09-05 13:13:11 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information 
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class DatabaseConnection {
    private static final ThreadLocal<Connection> con = new ThreadLocalConnection();
    public static final int CLOSE_CURRENT_RESULT = 1;
    /**
     * The constant indicating that the current <code>ResultSet</code> object
     * should not be closed when calling <code>getMoreResults</code>.
     *
     * @since 1.4
     */
    public static final int KEEP_CURRENT_RESULT = 2;
    /**
     * The constant indicating that all <code>ResultSet</code> objects that
     * have previously been kept open should be closed when calling
     * <code>getMoreResults</code>.
     *
     * @since 1.4
     */
    public static final int CLOSE_ALL_RESULTS = 3;
    /**
     * The constant indicating that a batch statement executed successfully
     * but that no count of the number of rows it affected is available.
     *
     * @since 1.4
     */
    public static final int SUCCESS_NO_INFO = -2;
    /**
     * The constant indicating that an error occured while executing a
     * batch statement.
     *
     * @since 1.4
     */
    public static final int EXECUTE_FAILED = -3;
    /**
     * The constant indicating that generated keys should be made
     * available for retrieval.
     *
     * @since 1.4
     */
    public static final int RETURN_GENERATED_KEYS = 1;
    /**
     * The constant indicating that generated keys should not be made
     * available for retrieval.
     *
     * @since 1.4
     */
    public static final int NO_GENERATED_KEYS = 2;

    public static final Connection getConnection() {
        return con.get();
    }

    public static final void closeAll() throws SQLException {
        for (final Connection con : ThreadLocalConnection.allConnections) {
            con.close();
        }
    }

    private static final class ThreadLocalConnection extends ThreadLocal<Connection> {

        public static final Collection<Connection> allConnections = new LinkedList<Connection>();

        @Override
        protected final Connection initialValue() {
            try {
                Class.forName("com.mysql.jdbc.Driver"); // touch the mysql driver
            } catch (final ClassNotFoundException e) {
                System.err.println("ERROR" + e);
            }
            try {
                final Connection con = DriverManager.getConnection(
                        ServerProperties.getProperty("jcs.Url"), 
                        ServerProperties.getProperty("jcs.User"), 
                        ServerProperties.getProperty("jcs.Pass"));
                allConnections.add(con);
                return con;
            } catch (SQLException e) {
                System.err.println("ERROR" + e);
                return null;
            }
        }
    }
}
