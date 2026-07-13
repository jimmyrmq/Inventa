package com.djm.inventa.producto.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class ProductoDAO {
    private Logger logger = Logger.getLogger(ProductoDAO.class.getName());

    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public Producto obtenerProducto(String codigo)throws ProductoException {

        String sql = """
            SELECT ID, Codigo, CodigoBarra, Nombre, UnidadMedida,
                   Modelo, Serie, MarcaID, CategoriaID, PrecioCosto,
                   Utilidad, Precio1, Precio2, Precio3, CantMayor,
                   PrecioIncluyeImpuesto, Disponible, CantidadDisponible,
                   StockCritico, NoRequiereStock, ReqAprobPrecioEspecial,
                   FechaActualizacion,FechaCreacion, Nota
            FROM Producto
            WHERE Codigo = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Producto producto = new Producto();

                    producto.setID(rs.getInt("ID"));
                    producto.setCodigo(rs.getString("Codigo"));
                    producto.setCodigoBarra(rs.getString("CodigoBarra"));
                    producto.setNombre(rs.getString("Nombre"));
                    producto.setUnidadMedida(rs.getString("UnidadMedida"));
                    producto.setModelo(rs.getString("Modelo"));
                    producto.setSerie(rs.getString("Serie"));

                    Integer marcaId = (Integer) rs.getObject("MarcaID");
                    if (marcaId != null) {
                        Marca marca = new Marca();
                        marca.setID(marcaId);
                        producto.setMarca(marca);
                    }

                    Integer categoriaId = (Integer) rs.getObject("CategoriaID");
                    if (categoriaId != null) {
                        Categoria categoria = new Categoria();
                        categoria.setID(categoriaId);
                        producto.setCategoria(categoria);
                    }

                    producto.setPrecioCosto(rs.getDouble("PrecioCosto"));

                    Integer utilidad = (Integer) rs.getObject("Utilidad");
                    producto.setUtilidad(utilidad);

                    producto.setPrecio1(rs.getDouble("Precio1"));
                    producto.setPrecio2(rs.getDouble("Precio2"));
                    producto.setPrecio3(rs.getDouble("Precio3"));

                    Integer cantMayor = (Integer) rs.getObject("CantMayor");
                    producto.setCantMayor(cantMayor);

                    producto.setPrecioIncluyeImpuesto(rs.getBoolean("PrecioIncluyeImpuesto"));
                    producto.setDisponible(rs.getBoolean("Disponible"));

                    Integer cantidadDisponible = (Integer) rs.getObject("CantidadDisponible");
                    producto.setCantidadDisponible(cantidadDisponible);

                    Integer stockCritico = (Integer) rs.getObject("StockCritico");
                    producto.setStockCritico(stockCritico);

                    producto.setNoRequiereStock(rs.getBoolean("NoRequiereStock"));
                    producto.setReqAprobPrecioEspecial(rs.getBoolean("ReqAprobPrecioEspecial"));

                    Timestamp ts = new Timestamp(rs.getLong("FechaActualizacion"));
                    producto.setFechaActualizacion(ts.toLocalDateTime());

                    Timestamp tsCreacion = new Timestamp(rs.getLong("FechaCreacion"));
                    producto.setFechaCreacion(tsCreacion.toLocalDateTime());

                    producto.setNota(rs.getString("Nota"));

                    return producto;
                }
            }
        } catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return null;
    }

    public boolean guardarProducto(Producto producto)throws ProductoException {

        boolean nuevoProducto = producto.getID() == null;

        String sql = nuevoProducto
                ? "INSERT INTO Producto (Codigo, CodigoBarra, Nombre, UnidadMedida, Modelo, Serie, MarcaID, CategoriaID, PrecioCosto, Utilidad, Precio1, Precio2, Precio3, CantMayor, PrecioIncluyeImpuesto, Disponible, CantidadDisponible, StockCritico, NoRequiereStock, ReqAprobPrecioEspecial, Nota, FechaCreacion, FechaActualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
                : "UPDATE Producto SET Codigo = ?, CodigoBarra = ?, Nombre = ?, UnidadMedida = ?, Modelo = ?, Serie = ?, MarcaID = ?, CategoriaID = ?, PrecioCosto = ?, Utilidad = ?, Precio1 = ?, Precio2 = ?, Precio3 = ?, CantMayor = ?, PrecioIncluyeImpuesto = ?, Disponible = ?, CantidadDisponible = ?, StockCritico = ?, NoRequiereStock = ?, ReqAprobPrecioEspecial = ?, Nota = ?,  FechaCreacion = ?, FechaActualizacion = ? WHERE ID = ?;";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getCodigoBarra());
            ps.setString(3, producto.getNombre());
            ps.setString(4, producto.getUnidadMedida());
            ps.setString(5, producto.getModelo());
            ps.setString(6, producto.getSerie());

            ps.setObject(7, producto.getMarca() != null ? producto.getMarca().getID() : null);
            ps.setObject(8, producto.getCategoria() != null ? producto.getCategoria().getID() : null);

            ps.setDouble(9, producto.getPrecioCosto());
            ps.setObject(10, producto.getUtilidad());

            ps.setDouble(11, producto.getPrecio1());
            ps.setDouble(12, producto.getPrecio2());
            ps.setDouble(13, producto.getPrecio3());

            ps.setObject(14, producto.getCantMayor());

            ps.setBoolean(15, Boolean.TRUE.equals(producto.isPrecioIncluyeImpuesto()));
            ps.setBoolean(16, producto.isDisponible());

            ps.setObject(17, producto.getCantidadDisponible());
            ps.setObject(18, producto.getStockCritico());

            ps.setBoolean(19, producto.isNoRequiereStock());
            ps.setBoolean(20, producto.isReqAprobnPrecioEspecial());

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
        } catch (SQLException exc) {
            logger.info(exc.getMessage());
            throw new ProductoException(exc.getMessage());
        }
    }
}
