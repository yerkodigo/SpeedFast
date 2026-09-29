package com.speedfast.dao;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.controlador.ConexionBD;
import com.speedfast.model.Pedido;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public int guardar(String nombre) {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";
        int idGenerado = 0;

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);

            stmt.executeUpdate();

            ResultSet claves = stmt.getGeneratedKeys();
            if (claves.next()) {
                idGenerado = claves.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el repartidor en la base de datos.");
        }

        return idGenerado;
    }

    public List<Repartidor> listarTodos() {
        String sql = "SELECT * FROM repartidor";
        List<Repartidor> repartidores = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            Repartidor repartidor;
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                repartidor = new Repartidor(id, nombre);
                repartidores.add(repartidor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar los repartidores en la base de datos.");
        }
        return repartidores;
    }
}
