package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.core.exception.ProductoException;
import com.djm.inventa.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO{
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();
    private final String TABLE = "categoria";

    public List<Categoria> listarCategorias() throws ProductoException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre FROM "+TABLE;

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

    public Categoria nuevaCategoria(String nombre, String color){

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setColor(color);

        guardar(categoria);

        return categoria;
    }

    public boolean guardar(Categoria categoria){

        if(categoria == null) {
            return false;
        }

        boolean nuevo = categoria.getID() == null;
        String [] cols ={"nombre", "color"};

        try {
            String sql = nuevo
                    ? SQLUtil.createInsert(TABLE,cols )
                    : SQLUtil.createUpdate(TABLE,"id = ?",cols );

            PreparedStatement ps =nuevo?
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS):
                    conn.prepareStatement(sql);

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getColor());

            if (!nuevo)
                ps.setInt(3, categoria.getID());

            int filas = ps.executeUpdate();

            if (nuevo && filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setID(rs.getInt(1));
                    }
                }
            }

            return true;

        } catch (SQLException | BaseDatosException exc) {
            throw new RuntimeException(exc);
        }
    }

}
