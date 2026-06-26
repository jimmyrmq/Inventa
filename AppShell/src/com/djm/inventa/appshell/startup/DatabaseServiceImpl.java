package com.djm.inventa.appshell.startup;

import com.djm.inventa.core.DatabaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServiceImpl implements DatabaseService {

    private Connection conn;

    public DatabaseServiceImpl() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/packing?useSSL=false",
                    "root",
                    "root"
            );

            System.out.println("Conexión exitosa");

        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    @Override
    public Connection getConnection() {
        return conn;
    }
}