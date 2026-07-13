package com.djm.inventa.producto.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.producto.exception.ProductoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO{
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<Categoria> listarCategorias() throws ProductoException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categoria";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setID(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categorias.add(categoria);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return categorias;
    }

}
