package com.djm.inventa.producto.dev;

import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.ui.component.OptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServiceImplSqlLite implements DatabaseService {

    private Connection conn = null;

    public DatabaseServiceImplSqlLite() {
    }

    @Override
    public void connect() {

        if (isConnected()) {
            return;
        }

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data/inventa.db");

            System.out.println("Conexión SQLite exitosa");

        } catch (SQLException exc) {
            System.out.println("Error al conectar: " + exc.getMessage());
            OptionPane.error(CONSTANTS.i18n.getValue("mensaje.error.conexionbd"));
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