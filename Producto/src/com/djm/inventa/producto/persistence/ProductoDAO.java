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
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;

public class ProductoDAO {
    private Logger logger = Logger.getLogger(ProductoDAO.class.getName());

    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public Producto obtenerProducto(String codigo)throws ProductoException {

        String sql = """            
                SELECT p.*
                    , COALESCE(sp.cantidad, 0) AS cantidad_stock, cat.nombre  AS categoria_nombre, m.nombre AS marca_nombre 
                 FROM (producto p inner join categoria cat on p.categoria_id = cat.id) LEFT JOIN stock_producto sp 
                    ON sp.producto_id = p.id
                     LEFT JOIN marca m ON p.marca_id = m.id
                WHERE codigo = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql);) {

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

        BigDecimal stockCritico = rs.getBigDecimal("stock_critico");
        producto.setStockCritico(stockCritico);

        producto.setNoRequiereStock(rs.getBoolean("no_requiere_stock"));
        producto.setReqAprobPrecioEspecial(rs.getBoolean("req_aprobacion_precio_especial"));
        producto.setMovimientoNegativo(rs.getBoolean("movimiento_negativo"));

        Timestamp ts = new Timestamp(rs.getLong("fecha_actualizacion"));
        producto.setFechaActualizacion(ts.toLocalDateTime());

        Timestamp tsCreacion = new Timestamp(rs.getLong("fecha_creacion"));
        producto.setFechaCreacion(tsCreacion.toLocalDateTime());

        producto.setNota(rs.getString("nota"));

        producto.setCantidadDisponible(new BigDecimal(rs.getString("cantidad_stock")));

        return producto;
    }

    public List<Producto> listarProductos() throws ProductoException {
        List<Producto> productos = new ArrayList<>();

        String sql = """
                SELECT p.*
                    , COALESCE(sp.cantidad, 0) AS cantidad_stock
                    , m.nombre AS marca_nombre
                    , c.nombre AS categoria_nombre
                FROM producto p
                LEFT JOIN stock_producto sp ON sp.producto_id = p.id
                LEFT JOIN marca m ON m.id = p.marca_id
                LEFT JOIN categoria c ON c.id = p.categoria_id
                ORDER BY p.nombre
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
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
        String cols []={ "codigo", "codigo_barra", "nombre",
                "unidad_medida", "modelo", "serie", "marca_id",
                "categoria_id", "precio_costo", "utilidad",
                "precio1", "precio2", "precio3", "cant_mayor",
                "precio_incluye_impuesto", "disponible",
                 "stock_critico",
                "no_requiere_stock", "req_aprobacion_precio_especial","movimiento_negativo",
                "nota", "fecha_creacion", "fecha_actualizacion"};

        try {

            String sql = nuevoProducto
                    ? SQLUtil.createInsert("producto",cols )//"INSERT INTO producto (codigo, codigo_barra, nombre, unidad_medida, modelo, serie, marca_id, categoria_id, precio_costo, utilidad, precio1, precio2, precio3, cant_mayor, precio_incluye_impuesto, disponible, cantidad_disponible, stock_critico, no_requiere_stock, req_aprobacion_precio_especial, nota, fecha_creacion, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
                : SQLUtil.createUpdate("producto","id = ?",cols );//"UPDATE producto SET codigo = ?, codigo_barra = ?, nombre = ?, unidad_medida = ?, modelo = ?, serie = ?, marca_id = ?, categoria_id = ?, precio_costo = ?, utilidad = ?, precio1 = ?, precio2 = ?, precio3 = ?, cant_mayor = ?, precio_incluye_impuesto = ?, disponible = ?, cantidad_disponible = ?, stock_critico = ?, no_requiere_stock = ?, req_aprobacion_precio_especial = ?, nota = ?, fecha_creacion = ?, fecha_actualizacion = ? WHERE id = ?;";

            PreparedStatement ps =nuevoProducto? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS):
                    conn.prepareStatement(sql);

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

            ps.setObject(17, producto.getStockCritico());

            ps.setBoolean(18, producto.isNoRequiereStock());
            ps.setBoolean(19, producto.isReqAprobPrecioEspecial());

            ps.setBoolean(20, producto.isMovimientoNegativo());
            ps.setString(21, producto.getNota());

            ps.setTimestamp(22, Timestamp.valueOf(producto.getFechaCreacion()));
            ps.setTimestamp(23, Timestamp.valueOf(producto.getFechaActualizacion()));

            if(!nuevoProducto)
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
        } catch (SQLException | BaseDatosException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }
    }


}
