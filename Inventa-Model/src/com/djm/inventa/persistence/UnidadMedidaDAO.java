package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.UnidadMedida;
import com.djm.inventa.core.exception.ProductoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnidadMedidaDAO {
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<UnidadMedida> listarUnidadesMedida() throws ProductoException {
        List<UnidadMedida> unidadesMedida = new ArrayList<>();
        String sql = "SELECT id, nombre, simbolo FROM unidad_medida";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UnidadMedida unidadMedida = new UnidadMedida();
                unidadMedida.setID(rs.getInt("id"));
                unidadMedida.setNombre(rs.getString("nombre"));
                unidadMedida.setSimbolo(rs.getString("simbolo"));
                unidadesMedida.add(unidadMedida);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return unidadesMedida;
    }
}
