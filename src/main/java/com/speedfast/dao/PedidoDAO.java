package com.speedfast.dao;

import com.speedfast.controlador.ConexionBD;
import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    public void guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido());
            stmt.setString(3, pedido.getEstado());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido en la base de datos.");
        }
    }

    public List<Pedido> listarPendientes() {
        String sql = "SELECT * FROM pedido where estado = 'Pendiente'";
        List<Pedido> comboPedidos = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Pedido pedido;
            while (rs.next()) {
                Integer idPedido = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");

                switch (tipo) {
                    case "Comida" -> pedido = new PedidoComida(idPedido, direccion, tipo, estado);
                    case "Encomienda" -> {
                        pedido = new PedidoEncomienda(idPedido, direccion, tipo, estado);
                    }
                    default -> pedido = new PedidoExpress(idPedido, direccion, tipo, estado);
                }
                comboPedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido en la base de datos.");
        }

        return comboPedidos;
    }
}
