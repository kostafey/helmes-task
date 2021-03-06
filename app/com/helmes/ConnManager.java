package com.helmes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.*;

public class ConnManager {

    // For test & debug purpose - runs DB in the same JVM as console app.
    static Connection getInProcessConnection() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./db/helmes-db", "sa", "");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Actually obtains Connection.
     */
    private static Connection getConnectionInner() {
        // Run H2 as server DB:
        // java -jar ~/.m2/repository/com/h2database/h2/1.4.197/h2-1.4.197.jar -tcpAllowOthers

        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:h2:tcp://192.168.36.74:9092/./db/helmes-db;ifexists=true;",
                    "sa",
                    "");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    static Connection getConnection() {
        return getInProcessConnection();
    }
}
