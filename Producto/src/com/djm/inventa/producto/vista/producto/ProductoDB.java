package com.djm.inventa.producto.vista.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.producto.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDB {

    public Producto buscarPorducto(String codigo) {

        Producto producto = null;

        DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");

        Connection conn = db.getConnection();

        String sql = "SELECT * FROM producto WHERE codigo = ?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                producto = new Producto();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    public boolean isDataProducto(String codigo){
        buscarPorducto(codigo);

        return false;
    }
}
