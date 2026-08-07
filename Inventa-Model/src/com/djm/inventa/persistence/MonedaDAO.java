package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.core.exception.ProductoException;
import com.djm.inventa.modelo.Moneda;
import com.djm.inventa.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MonedaDAO {
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();
    private final String TABLE = "moneda";

    public List<Moneda> listarMoneda() throws ProductoException {
        List<Moneda> monedas = new ArrayList<>();
        String sql = "SELECT id, nombre, simbolo FROM "+TABLE;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Moneda moneda = new Moneda();
                moneda.setID(rs.getInt("id"));
                moneda.setNombre(rs.getString("nombre"));
                moneda.setSimbolo(rs.getString("simbolo"));
                monedas.add(moneda);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return monedas;
    }

    public boolean guardar(Moneda moneda){

        if(moneda == null) {
            return false;
        }

        boolean nuevo = moneda.getID() == null;
        String [] cols ={ "nombre", "simbolo"};

        try {
            String sql = nuevo
                    ? SQLUtil.createInsert(TABLE,cols )
                    : SQLUtil.createUpdate(TABLE,"id = ?",cols );

            PreparedStatement ps =nuevo?
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS):
                    conn.prepareStatement(sql);

            ps.setString(1, moneda.getNombre());
            ps.setString(2, moneda.getSimbolo());

            if (!nuevo)
                ps.setInt(3, moneda.getID());

            int filas = ps.executeUpdate();

            if (nuevo && filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        moneda.setID(rs.getInt(1));
                    }
                }
            }

            return true;

        } catch (SQLException | BaseDatosException exc) {
            throw new RuntimeException(exc);
        }
    }
}
