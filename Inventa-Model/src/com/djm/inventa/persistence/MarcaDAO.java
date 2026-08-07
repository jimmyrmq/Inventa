package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.exception.BaseDatosException;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.core.exception.ProductoException;
import com.djm.inventa.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();
    private final String TABLE = "marca";

    public List<Marca> listarMarca() throws ProductoException {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT id, nombre FROM "+TABLE;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca marca = new Marca();
                marca.setID(rs.getInt("id"));
                marca.setNombre(rs.getString("nombre"));
                marcas.add(marca);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return marcas;
    }

    public Marca nuevoMarca(String nombre){
        Marca marca = new Marca();
        marca.setNombre(nombre);

        guardar(marca);

        return marca;
    }

    public boolean guardar(Marca marca){

        if(marca == null) {
            return false;
        }

        boolean nuevo = marca.getID() == null;
        String [] cols ={"nombre"};

        try {
            String sql = nuevo
                    ? SQLUtil.createInsert(TABLE,cols )
                    : SQLUtil.createUpdate(TABLE,"id = ?",cols );

            PreparedStatement ps =nuevo?
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS):
                    conn.prepareStatement(sql);

            ps.setString(1, marca.getNombre());

            if (!nuevo)
                ps.setInt(1, marca.getID());

            int filas = ps.executeUpdate();

            if (nuevo && filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        marca.setID(rs.getInt(1));
                    }
                }
            }

            return true;

        } catch (SQLException | BaseDatosException exc) {
            throw new RuntimeException(exc);
        }
    }

}
