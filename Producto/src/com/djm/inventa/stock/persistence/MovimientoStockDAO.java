package com.djm.inventa.stock.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.util.SQLUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class MovimientoStockDAO {
    private Logger logger = Logger.getLogger(MovimientoStockDAO.class.getName());
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public MovimientoStockDAO(){}

    public void agregarStock(MovimientoStock stock)throws ProductoException {
        String cols []= {"usuario_id",
                "producto_id", "almacen_id", "cantidad",
                "tipo", "fecha", "observacion", "stock_anterior", "stock_nuevo"};


        try {
            String sql  = SQLUtil.createInsert("movimiento_stock",cols);

            //System.out.println(sql+" -> "+stock);

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, stock.getUsuarioId());
            ps.setInt(2, stock.getProductoId());
            ps.setInt(3, stock.getAlmacenId());
            ps.setBigDecimal( 4, stock.getCantidad());
            ps.setInt(5, stock.getTipo());
            ps.setTimestamp(6, Timestamp.valueOf(stock.getFecha()));
            ps.setString(7, stock.getObservacion());
            ps.setBigDecimal(8, stock.getStockAnterior());
            ps.setBigDecimal(9, stock.getStockNuevo());

            int filas = ps.executeUpdate();

            if ( filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        stock.setId(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException | BaseDatosException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }
    }

    public void updateStockProducto(MovimientoStock stock)throws ProductoException{
        String sql = """
                update stock_producto set cantidad = ?
                where producto_id = ? and almacen_id = ?;
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, stock.getCantidad());
            ps.setInt(2, stock.getProductoId());
            ps.setInt(3, stock.getAlmacenId());

            ps.executeUpdate();

        } catch (Exception exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }

    }

    public BigDecimal getStockProducto(Integer producto_id, Integer almacen_id) throws ProductoException{
        BigDecimal stock = null;
        String sql = """
                select cantidad from stock_producto
                where producto_id = ? and almacen_id = ?;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, producto_id);
            ps.setInt(2, almacen_id);

             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     stock = rs.getBigDecimal("cantidad");
                 }
             }
        } catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return stock;
    }

}
