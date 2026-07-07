package com.djm.inventa.producto.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.producto.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDB {

    public Producto buscarPorducto(String codigo) {

        Producto producto = null;

        DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");

        Connection conn = db.getConnection();

        String sql = "SELECT * FROM producto WHERE codigo = ?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                producto = new Producto();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    public boolean isDataProducto(String codigo){
        buscarPorducto(codigo);
        return false;
    }

    public boolean guardarProducto(Producto producto) {
        DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
        Connection conn = db.getConnection();

        boolean nuevoProducto = producto.getID() == null;

        String sql = nuevoProducto
                ? "INSERT INTO Producto (Codigo, CodigoBarra, Nombre, UnidadMedida, Modelo, Serie, MarcaID, CategoriaID, PrecioCosto, Utilidad, Precio1, Precio2, Precio3, CantMayor, PrecioIncluyeImpuesto, Disponible, CantidadDisponible, StockCritico, NoRequiereStock, ReqAprobPrecioEspecial, FechaActualizacion, Nota) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
                : "UPDATE Producto SET Codigo = ?, CodigoBarra = ?, Nombre = ?, UnidadMedida = ?, Modelo = ?, Serie = ?, MarcaID = ?, CategoriaID = ?, PrecioCosto = ?, Utilidad = ?, Precio1 = ?, Precio2 = ?, Precio3 = ?, CantMayor = ?, PrecioIncluyeImpuesto = ?, Disponible = ?, CantidadDisponible = ?, StockCritico = ?, NoRequiereStock = ?, ReqAprobPrecioEspecial = ?, FechaActualizacion = ?, Nota = ? WHERE ID = ?;";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

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

            ps.setTimestamp(21, new java.sql.Timestamp(producto.getFechaActualizacion().getTime()));

            ps.setString(22, producto.getNota());

            if(nuevoProducto)
                ps.setLong(23, producto.getID());

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
