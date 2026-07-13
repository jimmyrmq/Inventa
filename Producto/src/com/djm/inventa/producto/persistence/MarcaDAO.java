package com.djm.inventa.producto.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.producto.exception.ProductoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<Marca> listarMarca() throws ProductoException {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT ID, Nombre FROM Marca";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca marca = new Marca();
                marca.setID(rs.getInt("ID"));
                marca.setNombre(rs.getString("Nombre"));
                marcas.add(marca);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return marcas;
    }

}
