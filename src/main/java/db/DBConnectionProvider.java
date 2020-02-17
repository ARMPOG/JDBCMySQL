package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnectionProvider {

    private Connection connection;
    private String driverName;
    private String dbUrl;
    private String username;
    private String password;

    private static DBConnectionProvider instance = new DBConnectionProvider();

    private DBConnectionProvider() {
        try {
            loadProperties();
            Class.forName(driverName);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DBConnectionProvider getInstance() {
        return instance;
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("/home/ars/IdeaProjects/JDBCMySQL/src/main/resources/application.properties"));
        driverName = properties.getProperty("db.driver.name");
        dbUrl = properties.getProperty("url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(dbUrl, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
