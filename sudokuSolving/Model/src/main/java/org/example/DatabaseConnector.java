package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    private static Connection connection = null;
    public String username;

    public Connection getConnection(String name) throws JdbcException {

        try {
            String url = "jdbc:mysql://localhost:3306/comprog";
            username = name;
            String password = "bahar";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(),e);
        }
        return connection;
    }
}
