package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.Atributo;
import com.djm.inventa.core.exception.ProductoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtributoDAO {
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<Atributo> listarAtributos() throws ProductoException {
        List<Atributo> atributos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM atributo";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Atributo atributo = new Atributo();
                atributo.setID(rs.getInt("id"));
                atributo.setNombre(rs.getString("nombre"));
                atributos.add(atributo);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return atributos;
    }

    public Atributo guardarAtributo(String nombre){
        String sql = "INSERT INTO atributo (nombre) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Atributo atributo = new Atributo();
                atributo.setID(rs.getInt(1));
                atributo.setNombre(nombre);
                return atributo;
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }

        return null;
    }
}
