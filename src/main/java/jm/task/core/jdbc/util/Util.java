package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class Util {
    public static Connection getConnection() throws SQLException {
        Driver driver = new FabricMySQLDriver();
        DriverManager.registerDriver(driver);

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/somedb?useSSL=false",
                "root", "959297");
    }
}
