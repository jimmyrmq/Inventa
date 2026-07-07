package com.djm.inventa.appshell.startup;

import com.djm.inventa.core.DatabaseService;
import com.djm.ui.component.OptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServiceImplMysql implements DatabaseService {

    private Connection conn = null;

    public DatabaseServiceImplMysql() {
    }

    @Override
    public void connect() {

        if (isConnected()) {
            return;
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventa?useSSL=false","root","root");

            System.out.println("Conexión exitosa");

        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            OptionPane.error(CONSTANTS.I8N.getValue("mensaje.error.conexionbd"));
        }
    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    @Override
    public boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}