package com.djm.inventa.stock.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.util.SQLUtil;

import java.math.BigDecimal;
import java.sql.Connection;

public class MovimientoStockDAO {
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public MovimientoStockDAO(){}

    public void agregarStock(MovimientoStock stock)throws BaseDatosException {
        String cols []= {"usuario_id",
                "producto_id", "almacen_id", "cantidad",
                "tipo", "fecha", "observacion", "stock_anterior", "stock_nuevo"};

        String sql  = SQLUtil.createInsert("movimiento_stock",cols);

        System.out.println(sql);

    }

}
