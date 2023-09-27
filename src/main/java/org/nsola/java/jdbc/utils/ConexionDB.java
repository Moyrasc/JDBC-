package org.nsola.java.jdbc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            Properties propiedades = new Properties();
            String rutaActual = System.getProperty("user.dir");
            try {
                FileInputStream archivoPropiedades = new FileInputStream(rutaActual + "\\src\\main\\resources\\config.properties");
                propiedades.load(archivoPropiedades);
                archivoPropiedades.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String url = propiedades.getProperty("db.url");
            String username = propiedades.getProperty("db.username");
            String password = propiedades.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
