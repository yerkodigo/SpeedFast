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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    public int guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";
        int idGenerado = 0;

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido());
            stmt.setString(3, pedido.getEstado());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();
            if (id.next()) {
                idGenerado = id.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido en la base de datos.");
        }

        return idGenerado;
    }

    public void actualizarEstado(int idPedido, String estado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, idPedido);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado del pedido en la base de datos.");
        }
    }

    public List<Pedido> listarTodos() {
        String sql = """
                SELECT p.id, p.direccion, p.tipo, p.estado, r.nombre AS nombre_repartidor 
                FROM pedido p 
                left join entrega e on e.id_pedido = p.id 
                left join repartidor r on r.id = e.id_repartidor
                """;

        return listarConConsulta(sql);
    }

    public List<Pedido> listarEnReparto() {
        String sql = """
                SELECT p.id, p.direccion, p.tipo, p.estado, r.nombre AS nombre_repartidor 
                FROM pedido p 
                LEFT JOIN entrega e ON e.id_pedido = p.id 
                LEFT JOIN repartidor r ON r.id = e.id_repartidor 
                WHERE p.estado = 'EN_REPARTO'
                """;

        return listarConConsulta(sql);
    }

    private List<Pedido> listarConConsulta(String sql) {
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            Pedido pedido;
            while (rs.next()) {
                Integer idPedido = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");
                String nombreRepartidor = rs.getString("nombre_repartidor");

                switch (tipo) {
                    case "COMIDA" -> pedido = new PedidoComida(idPedido, direccion, tipo, estado);
                    case "ENCOMIENDA" -> pedido = new PedidoEncomienda(idPedido, direccion, tipo, estado);
                    default -> pedido = new PedidoExpress(idPedido, direccion, tipo, estado);
                }
                pedido.setRepartidorAsignado(nombreRepartidor);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar los pedidos en la base de datos.");
        }

        return pedidos;
    }
}
