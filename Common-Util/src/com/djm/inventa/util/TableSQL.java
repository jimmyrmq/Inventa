package com.djm.inventa.util;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/*
drop table producto;
drop table proveedor;
drop table marca;
drop table categoria;
*/
public class TableSQL {
    private String database;
    private String carpeta;

    public TableSQL(String database) {
        String motor = AppContext.getInstance().getString("database.type");

        this.database = database;
        this.carpeta = "my"; // Valor por defecto, puede ser modificado según el motor

        switch (motor.toLowerCase()) {
            case "sqlite":
                this.carpeta = "lite";
                break;
            case "mysql":
                this.carpeta = "my";
                break;
            default:
                LoggerApp.warn("Motor de base de datos no reconocido: " + motor + ". Se usará la carpeta por defecto 'my'.");
        }

    }

    public void checkTable(String ... tables){

        DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
        if(db.isConnected()) {
            Connection conn = db.getConnection();

            String dbType = AppContext.getInstance().getString("database.type");
            boolean isLite = dbType.equals("sqlite");

            String sql = isLite ? querySqllite() : queryMySQL();

            List<String> listTable = null;
            try {
                 PreparedStatement pstmt = conn.prepareStatement(sql);

                 if(!isLite)
                    pstmt.setString(1, this.database);

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    // Comprobar si la tabla existe
                    if (resultSet != null) {
                        listTable = new ArrayList<>();
                        while (resultSet.next()) {
                            listTable.add(resultSet.getString(1));
                        }
                        resultSet.close();
                    }
                }

            } catch (SQLException exc) {
                LoggerApp.error(exc.getMessage());
            }

            if (listTable != null) {
                boolean create;
                for (String tableName : tables) {
                    create = true;
                    cont:
                    for (String lt : listTable) {
                        if (tableName.equalsIgnoreCase(lt)) {
                            create = false;
                            break cont;
                        }
                    }
                    if (create)
                        createTable(tableName, conn);
                }
            }
        }
    }


    private boolean createTable(String table, Connection conn){
        boolean created = false;
        LoggerApp.info("Crear la tabla de "+table+".");
        File file = new File("scripts/" + this.carpeta + "/" + table + ".sql");
        if(file.exists()) {

            ReaderFileSQL rf = new ReaderFileSQL();
            String sqlAux = rf.readTable(file);

            String sqls[] = sqlAux.split(";");
            for (String sql : sqls) {
                if (sql != null && !sql.trim().isEmpty()) {
                        created = ejecutar(conn, sql);

                    if (created) {
                        String msg = sql.startsWith("CREATE TABLE") ? "Tabla de " + table + " creada con exito." : "Datos insertados con exito en la tabla " + table + ".";
                        LoggerApp.info(msg);
                    } else {
                        String msg = sql.startsWith("CREATE TABLE") ? "Error al crear la tabla " + table + "." : "Error al insertar en la tabla " + table + "\n\t" + sql + ".";
                        LoggerApp.error(msg);
                    }
                }
            }
        }else
            System.out.println("No se encontro el archivo ["+file.getAbsolutePath()+"] de script para la tabla "+table+".");

        return created;
    }

    private String queryMySQL() {
        return """
                SELECT TABLE_NAME
                FROM information_schema.tables
                WHERE table_schema = ?;
                """;
    }

    private String querySqllite() {
        return "SELECT name FROM sqlite_master WHERE type='table';";
    }

    private boolean ejecutar(Connection conn, String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        }
        catch (SQLException exc) {
            LoggerApp.error("ERROR: " + sql + "->\n\t" + exc.getMessage());
            return false;
        }
    }
}
