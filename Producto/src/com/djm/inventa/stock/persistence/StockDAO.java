package com.djm.inventa.stock.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.core.exception.ProductoException;
import com.djm.inventa.stock.model.TipoMovimientoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StockDAO {

    private Logger logger = Logger.getLogger(MovimientoStockDAO.class.getName());
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<TipoMovimientoDTO>  getTipoMovimientos() throws ProductoException {
        List<TipoMovimientoDTO>  list = new ArrayList<>();
        String sql = """
                Select * from tipo_movimiento where factor_stock = 1;
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapTipoMovimiento(rs));
            }
        } catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return list;
    }

    private TipoMovimientoDTO mapTipoMovimiento(ResultSet rs) throws SQLException {
        TipoMovimientoDTO tipoMovimientoDTO = new TipoMovimientoDTO();

        tipoMovimientoDTO.setId(rs.getInt("id"));
        tipoMovimientoDTO.setNombre(rs.getString("nombre"));
        tipoMovimientoDTO.setDescripcion(rs.getString("descripcion"));
        //tipoMovimientoDTO.setLanguage_key(rs.getString("language_key"));
        tipoMovimientoDTO.setFactor_stock(rs.getByte("factor_stock"));

        return tipoMovimientoDTO;
    }
}
