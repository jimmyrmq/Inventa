package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.TipoPrecio;
import com.djm.inventa.core.exception.ProductoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPrecioDAO {
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<TipoPrecio> listaTipoPrecio() throws ProductoException {
        List<TipoPrecio> tiposPrecio = new ArrayList<>();
        String sql = "SELECT id, nombre FROM tipo_precio";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TipoPrecio tipoPrecio = new TipoPrecio();
                    tipoPrecio.setID(rs.getInt("id"));
                    tipoPrecio.setNombre(rs.getString("nombre"));
                    tiposPrecio.add(tipoPrecio);
                }
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return tiposPrecio;
    }

}
