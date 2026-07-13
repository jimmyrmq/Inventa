package com.djm.inventa.util;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.exception.BaseDatosException;

import java.util.Collections;

public class SQLUtil {
    static {
        System.out.println(AppContext.getInstance().get("database.type"));
    }

    public static String createInsert(String table, String columns[])throws BaseDatosException {

        try {
            validateParams( table, columns);
        }catch (BaseDatosException exc){
            throw new BaseDatosException("INSTERT_DB", exc.getMessage());
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(table);
        sql.append(" ");
        String cols = String.join(", ", columns);
        sql.append(cols);
        sql.append(" VALUES (");

        String placeholders = String.join(", ", Collections.nCopies(columns.length, "?"));
        /*for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append("?");
        }
*/
        sql.append(placeholders);

        sql.append(")");

        return  sql.toString();
    }

    public static String createUpdate(String table, String whereColumn, String columns[]) throws BaseDatosException {

        try {
            validateParams( table, whereColumn, columns);
        }catch (BaseDatosException exc){
            throw new BaseDatosException("UPDATE_DB", exc.getMessage());
        }

        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE ")
                .append(table)
                .append(" SET ");

        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(columns[i]);
            sql.append(" = ?");
        }

        sql.append(" WHERE ")
                .append(whereColumn);

        return  sql.toString();
    }

    private static void validateParams(String table, String... columns) throws BaseDatosException {
        if (table == null || table.trim().isEmpty()) {
            throw new BaseDatosException("La tabla no puede estar vacía");
        }

        if (columns == null || columns.length == 0) {
            throw new BaseDatosException("Debe ingresar al menos una columna para actualizar");
        }

        for (String column : columns) {
            if (column == null || column.trim().isEmpty()) {
                throw new BaseDatosException("Las columnas no pueden estar vacías");
            }
        }
    }

    private static void validateParams(String table, String whereColumn, String... columns) throws BaseDatosException {
        validateParams(table, columns);

        if (whereColumn == null || whereColumn.trim().isEmpty()) {
            throw new BaseDatosException("La columna WHERE no puede estar vacía");
        }
    }

}
