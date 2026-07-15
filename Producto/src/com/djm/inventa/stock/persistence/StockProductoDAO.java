package com.djm.inventa.stock.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.stock.model.StockProducto;
import com.djm.inventa.util.SQLUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class StockProductoDAO {
    private Logger logger = Logger.getLogger(StockProductoDAO.class.getName());
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public void nuevoRegistro(StockProducto stockProducto)throws ProductoException{
        String columns [] = {"producto_id","almacen_id" ,"cantidad","stock_minimo","stock_maximo"};
        try {
            String sql = SQLUtil.createInsert("stock_producto", columns);
            //System.out.println(sql+" -> "+stock);

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, stockProducto.getProductoId());
            ps.setInt(2, stockProducto.getAlmacenId());
            ps.setBigDecimal(3, stockProducto.getCantidad());
            ps.setBigDecimal( 4, stockProducto.getStockMaximo());
            ps.setBigDecimal(5, stockProducto.getStockMinimo());

            int filas = ps.executeUpdate();

            if ( filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        stockProducto.setId(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException | BaseDatosException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }
    }

    public void actualizarCantidad(Integer idProducto, BigDecimal cantidad)throws ProductoException{

        String columns [] = {"cantidad"};
        try {
            String sql = SQLUtil.createUpdate("stock_producto","producto_id = ?", columns);
            //System.out.println(sql+" -> "+stock);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, cantidad);
            ps.setInt(2, idProducto);

            ps.executeUpdate();

        } catch (SQLException | BaseDatosException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }
    }

    public boolean existeProducto(Integer idProducto)throws ProductoException{
        if(idProducto == null)
            return false;

        String sql = """
                SELECT EXISTS(
                    SELECT 1 FROM stock_producto WHERE producto_id = ?)
                
                """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getBoolean(1);
                }
            }

        } catch (SQLException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }

        return false;
    }
}
