package com.djm.inventa.producto.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.util.SQLUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

public class ProductoDAO {
    private Logger logger = Logger.getLogger(ProductoDAO.class.getName());

    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public Producto obtenerProducto(String codigo)throws ProductoException {

        String sql = querySelectProducto("WHERE codigo = ?");

        try(//Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapProducto(rs);
                }
            }
        } catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return null;
    }

    private Producto mapProducto(ResultSet rs) throws SQLException {

        Producto producto = new Producto();
        producto.setID(rs.getInt("id"));
        producto.setCodigo(rs.getString("codigo"));
        producto.setCodigoBarra(rs.getString("codigo_barra"));
        producto.setNombre(rs.getString("nombre"));
        producto.setUnidadMedida(rs.getString("unidad_medida"));
        producto.setModelo(rs.getString("modelo"));
        producto.setSerie(rs.getString("serie"));

        Marca marca = new Marca();
        marca.setID(rs.getInt("marca_id"));
        marca.setNombre(rs.getString("marca_nombre"));
        producto.setMarca(marca);

        Categoria categoria = new Categoria();
        categoria.setID(rs.getInt("categoria_id"));
        categoria.setNombre(rs.getString("categoria_nombre"));
        producto.setCategoria(categoria);

        producto.setPrecioCosto(rs.getBigDecimal("precio_costo"));

        Integer utilidad = (Integer) rs.getObject("utilidad");
        producto.setUtilidad(utilidad);

        producto.setPrecio1(rs.getBigDecimal("precio1"));
        producto.setPrecio2(rs.getBigDecimal("precio2"));
        producto.setPrecio3(rs.getBigDecimal("precio3"));

        Integer cantMayor = (Integer) rs.getObject("cant_mayor");
        producto.setCantMayor(cantMayor);

        producto.setPrecioIncluyeImpuesto(rs.getBoolean("precio_incluye_impuesto"));
        producto.setDisponible(rs.getBoolean("disponible"));

        producto.setNoRequiereStock(rs.getBoolean("no_requiere_stock"));
        producto.setReqAprobPrecioEspecial(rs.getBoolean("req_aprobacion_precio_especial"));
        producto.setMovimientoNegativo(rs.getBoolean("movimiento_negativo"));

        Timestamp ts = new Timestamp(rs.getLong("fecha_actualizacion"));
        producto.setFechaActualizacion(ts.toLocalDateTime());

        Timestamp tsCreacion = new Timestamp(rs.getLong("fecha_creacion"));
        producto.setFechaCreacion(tsCreacion.toLocalDateTime());

        producto.setNota(rs.getString("nota"));

        producto.setCantidadDisponible(new BigDecimal(rs.getString("cantidad_stock")));
        producto.setStockMaximo(rs.getBigDecimal("stock_maximo"));
        producto.setStockMinimo(rs.getBigDecimal("stock_minimo"));

        producto.setEliminado(rs.getBoolean("eliminado"));

        return producto;
    }

    public List<Producto> listarProductos() throws ProductoException {
        List<Producto> productos = new ArrayList<>();

        String sql = querySelectProducto("WHERE eliminado = 0","ORDER BY p.nombre");

        try (//Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapProducto(rs));
            }
        } catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return productos;
    }

    public boolean guardarProducto(Producto producto)throws ProductoException {

        boolean nuevoProducto = producto.getID() == null;
        String [] cols ={ "codigo", "codigo_barra", "nombre",
                "unidad_medida", "modelo", "serie", "marca_id",
                "categoria_id", "precio_costo", "utilidad",
                "precio1", "precio2", "precio3", "cant_mayor",
                "precio_incluye_impuesto", "disponible",
                "no_requiere_stock", "req_aprobacion_precio_especial","movimiento_negativo",
                "nota", "fecha_creacion", "fecha_actualizacion","eliminado"};

        //try (Connection conn = db.getConnection()) {
        try{
            String sql = nuevoProducto
                    ? SQLUtil.createInsert("producto",cols )//"INSERT INTO producto (codigo, codigo_barra, nombre, unidad_medida, modelo, serie, marca_id, categoria_id, precio_costo, utilidad, precio1, precio2, precio3, cant_mayor, precio_incluye_impuesto, disponible, cantidad_disponible, stock_critico, no_requiere_stock, req_aprobacion_precio_especial, nota, fecha_creacion, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
                    : SQLUtil.createUpdate("producto","id = ?",cols );//"UPDATE producto SET codigo = ?, codigo_barra = ?, nombre = ?, unidad_medida = ?, modelo = ?, serie = ?, marca_id = ?, categoria_id = ?, precio_costo = ?, utilidad = ?, precio1 = ?, precio2 = ?, precio3 = ?, cant_mayor = ?, precio_incluye_impuesto = ?, disponible = ?, cantidad_disponible = ?", stock_critico = ?", no_requiere_stock = ?", req_aprobacion_precio_especial = ?", nota = ?", fecha_creacion = ?", fecha_actualizacion = ? WHERE id = ?;";

            //try (
                    PreparedStatement ps =nuevoProducto?
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS):
                    conn.prepareStatement(sql);//) {

                ps.setString(1, producto.getCodigo());
                ps.setString(2, producto.getCodigoBarra());
                ps.setString(3, producto.getNombre());
                ps.setString(4, producto.getUnidadMedida());
                ps.setString(5, producto.getModelo());
                ps.setString(6, producto.getSerie());

                ps.setObject(7, producto.getMarca() != null ? producto.getMarca().getID() : null);
                ps.setObject(8, producto.getCategoria() != null ? producto.getCategoria().getID() : null);

                ps.setBigDecimal(9, producto.getPrecioCosto());
                ps.setObject(10, producto.getUtilidad());

                ps.setBigDecimal(11, producto.getPrecio1());
                ps.setBigDecimal(12, producto.getPrecio2());
                ps.setBigDecimal(13, producto.getPrecio3());

                ps.setObject(14, producto.getCantMayor());

                ps.setBoolean(15, Boolean.TRUE.equals(producto.isPrecioIncluyeImpuesto()));
                ps.setBoolean(16, producto.isDisponible());

                ps.setBoolean(17, producto.isNoRequiereStock());
                ps.setBoolean(18, producto.isReqAprobPrecioEspecial());

                ps.setBoolean(19, producto.isMovimientoNegativo());
                ps.setString(20, producto.getNota());

                ps.setTimestamp(21, Timestamp.valueOf(producto.getFechaCreacion()));
                ps.setTimestamp(22, Timestamp.valueOf(producto.getFechaActualizacion()));
                ps.setBoolean(23, producto.isEliminado());

                if (!nuevoProducto)
                    ps.setLong(24, producto.getID());

                int filas = ps.executeUpdate();

                if (nuevoProducto && filas > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            producto.setID(rs.getInt(1));
                        }
                    }
                }

                return true;
            //}
        } catch (SQLException | BaseDatosException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }
    }


    public boolean eliminarProducto(Integer idProducto) throws ProductoException {

        if (puedeEliminarProducto(idProducto)) {
            try {
                eliminarProductoDB(idProducto);
                return true;
            } catch (SQLException e) {
                throw new ProductoException(e.getMessage());
            }
        }
        else{
            int idUsuario = AppContext.getInstance().getInt("usuario.id");
            try {
                String [] cols = {"disponible","eliminado", "fecha_eliminacion", "usuario_id_eliminacion"};

                String sql = SQLUtil.createUpdate("producto", "id = ?", cols);

                try (//Connection conn = db.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, 0);
                    ps.setInt(2, 1);
                    ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setInt(4, idUsuario);

                    ps.setInt(5, idProducto);

                    int filasEliminadas = ps.executeUpdate();

                    if (filasEliminadas > 0) {
                        return true;
                    }

                } catch (SQLException exc) {
                    throw new ProductoException(exc.getMessage());
                }
            }catch (BaseDatosException exc){
                throw new ProductoException(exc.getMessage());
            }
        }

        return false;
    }

    private boolean puedeEliminarProducto(Integer idProducto) throws ProductoException {
        String sql = """
            SELECT
                ms.total_movimientos,
                ms.cantidad,
                (SELECT COUNT(*) FROM documento_item WHERE producto_id = ?) AS items_documento
            FROM (
                 SELECT COUNT(*) AS total_movimientos, SUM(cantidad) AS cantidad
                FROM movimiento_stock WHERE producto_id = ?) AS ms 
            """;

        try (//Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int totalMovimientos = rs.getInt("total_movimientos");
                    int cantidad = rs.getInt("cantidad");
                    int itemsDocumento = rs.getInt("items_documento");

                    // Validar: exactamente 1 movimiento, cantidad = 0, y sin documentos
                    return totalMovimientos == 1 && cantidad == 0 && itemsDocumento == 0;
                }
            }
        } catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return false;
    }

    private String querySelectProducto(String ... addSql){
        //COALESCE: Devuelve el primer valor que no sea null

        StringJoiner columns = new StringJoiner(", ");
        columns.add("p.*");
        columns.add("COALESCE(sp.cantidad, 0) AS cantidad_stock");
        columns.add("COALESCE(sp.stock_maximo, 0) AS stock_maximo");
        columns.add("COALESCE(sp.stock_minimo, 0) AS stock_minimo");
        columns.add("m.nombre AS marca_nombre");
        columns.add("c.nombre AS categoria_nombre");

        StringBuilder sql = new StringBuilder ();
        sql.append("SELECT ");
        sql.append(columns);
        sql.append(" FROM producto p");
        sql.append(" LEFT JOIN stock_producto sp ON sp.producto_id = p.id");
        sql.append(" LEFT JOIN marca m ON m.id = p.marca_id");
        sql.append(" LEFT JOIN categoria c ON c.id = p.categoria_id");

        if(addSql != null){

            sql.append(" ");
            String resultado = String.join(" ", addSql);

            sql.append(resultado);
        }

        //System.out.println(sql.toString());

        return sql.toString();
    }

    public void eliminarProductoDB(int productoId) throws SQLException {
        String deleteMovimientos = """
        DELETE FROM movimiento_stock
        WHERE producto_id = ?
        """;

        String deleteStock = """
        DELETE FROM stock_producto
        WHERE producto_id = ?
        """;

        String deleteProducto = """
        DELETE FROM producto
        WHERE id = ?
        """;

        //try(Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement psMov = conn.prepareStatement(deleteMovimientos);
                    PreparedStatement psStock = conn.prepareStatement(deleteStock);
                    PreparedStatement psProd = conn.prepareStatement(deleteProducto)
            ) {

                psMov.setInt(1, productoId);
                psMov.executeUpdate();

                psStock.setInt(1, productoId);
                psStock.executeUpdate();

                psProd.setInt(1, productoId);
                psProd.executeUpdate();

                conn.commit();

            } catch (SQLException e) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }

                throw e;
            }
            finally {

                conn.setAutoCommit(true);
            }
        //}
    }
}
