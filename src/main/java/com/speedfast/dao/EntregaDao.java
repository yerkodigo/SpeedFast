package com.speedfast.dao;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.controlador.ConexionBD;
import com.speedfast.model.Entrega;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDao implements IEntregaDAO {
    @Override
    public void create(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entrega.getId_pedido());
            stmt.setInt(2, entrega.getId_repartidor());
            stmt.setDate(3, entrega.getFecha());
            stmt.setTime(4, entrega.getHora());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la entrega en la base de datos.");
        }
    }

    @Override
    public List<Entrega> readAll() {
        String sql = "SELECT * FROM entrega";
        List<Entrega> entregas = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            Entrega entrega;
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer id_pedido = rs.getInt("id_pedido");
                Integer id_repartidor = rs.getInt("id_repartidor");
                Date fecha = rs.getDate("fecha");
                Time hora = rs.getTime("hora");

                entrega = new Entrega(id, id_pedido, id_repartidor, fecha, hora);
                entregas.add(entrega);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar las entregas en la base de datos.");
        }
        return entregas;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

}
