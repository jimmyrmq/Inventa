package com.djm.inventa.producto.core;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.producto.dev.DatabaseServiceImplSqlLite;
import com.djm.inventa.util.TableSQL;

public class ConexionDB {

    public static void establecerConexion() {
        //Establecer conxion con la BD
        DatabaseService db = new DatabaseServiceImplSqlLite();
        //Establer correcion
        db.connect();

        AppContext.getInstance().setPropiedad("db.service", db);
    }

    public static void initDBDev() {
        DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
        if(db.isConnected()) {
            TableSQL tableSQL = new TableSQL("inventa");
            String[] tables = {"almacen","categoria","marca","unidad_medida",
                    "moneda","producto_precio","tipo_precio","atributo","atributo_valor","producto",
                    "producto_variante","variante_atributo",
                    "movimiento_stock","stock_producto","tipo_movimiento",
                    "documento_item","usuario"};
            tableSQL.checkTable(tables);
        }else
            System.exit(1);

    }
}
