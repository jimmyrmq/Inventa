package com.djm.inventa.persistence;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.DatabaseService;
import com.djm.inventa.modelo.Atributo;
import com.djm.inventa.modelo.AtributoValor;
import com.djm.inventa.core.exception.ProductoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtributosValorDAO {
    private final DatabaseService db = AppContext.getInstance().getDatabaseService("db.service");
    private final Connection conn = db.getConnection();

    public List<AtributoValor> listarAtributoValor(Integer idAtributo) throws ProductoException {
        List<AtributoValor> atributos = new ArrayList<>();
        String sql = "SELECT id, valor FROM atributo_valor where atributo_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
             ps.setInt(1, idAtributo);

             try(ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     AtributoValor valor = new AtributoValor();
                     valor.setID(rs.getInt("id"));
                     valor.setValor(rs.getString("valor"));
                     atributos.add(valor);
                 }
             }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return atributos;
    }

    public List<AtributoValor> listarAtributoValor() throws ProductoException {
        List<AtributoValor> atributos = new ArrayList<>();
        String sql = """
                    SELECT a.id as id_a, a.nombre, av.id as id_av, av.valor 
                       FROM  atributo_valor av inner join atributo a on av.atributo_id = a.id
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Atributo atributo = new Atributo();
                atributo.setID(rs.getInt("id_a"));
                atributo.setNombre(rs.getString("nombre"));

                AtributoValor valor = new AtributoValor();
                valor.setID(rs.getInt("id_av"));
                valor.setValor(rs.getString("valor"));
                valor.setAtributo(atributo);

                atributos.add(valor);
            }
        }
        catch (SQLException exc) {
            throw new ProductoException(exc.getMessage());
        }

        return atributos;
    }

    public AtributoValor guardarAtributoValor(Integer idAtributo, String nombre) {
        String sql = "INSERT INTO atributo_valor (atributo_id, valor) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idAtributo);
            ps.setString(2, nombre);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                AtributoValor atributoValor = new AtributoValor();
                atributoValor.setID(rs.getInt(1));
                atributoValor.setValor(nombre);
                return atributoValor;
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }

        return null;
    }
}
