package com.speedfast.vista;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.dao.EntregaDao;
import com.speedfast.dao.PedidoDAO;
import com.speedfast.dao.RepartidorDAO;
import com.speedfast.model.Entrega;
import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;

public class VentanaRegistroPedido extends JFrame {
    private final ControladorDeEnvios controlador;
    private final PedidoDAO dao;
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final EntregaDao entregaDao = new EntregaDao();

    private JTextField campoDireccion;
    private JComboBox<String> comboTipo;
    private JComboBox<Repartidor> comboRepartidor;

//    private JTextField campoDistancia;
//    private JLabel etiquetaCampoExtra;
//    private JTextField campoExtra;
//    private JCheckBox checkMochilaTermica;

    public VentanaRegistroPedido(ControladorDeEnvios controlador, PedidoDAO dao) {
        this.controlador = controlador;
        this.dao = dao;

        setTitle("Nuevo Registro");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // solo cierra ESTA ventana, no toda la app
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 10));

        add(new JLabel("Dirección:"));
        campoDireccion = new JTextField();
        add(campoDireccion);

        add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});
        add(comboTipo);

        add(new JLabel("Repartidor:"));
        comboRepartidor = new JComboBox<>();
        cargarRepartidores();
        add(comboRepartidor);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> validarYGuardar());
        add(new JLabel()); // celda vacía para alinear el botón a la derecha
        add(botonGuardar);

        setVisible(true);
    }

    private void cargarRepartidores() {
        comboRepartidor.removeAllItems();
        for (Repartidor repartidor : repartidorDAO.listarTodos()) {
            comboRepartidor.addItem(repartidor);
        }
    }

    private void validarYGuardar() {
        String direccion = campoDireccion.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        Repartidor repartidorSeleccionado = (Repartidor) comboRepartidor.getSelectedItem();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Dirección no puede estar vacío", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (repartidorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un repartidor. Si no existe, agréguelo desde \"Agregar Repartidor\" en el menú principal.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pedido pedido;
        switch (tipo) {
            case "COMIDA" -> pedido = new PedidoComida(direccion, tipo, false, 0);
            case "ENCOMIENDA" -> pedido = new PedidoEncomienda(direccion, tipo, 0f, 0);
            default -> pedido = new PedidoExpress(direccion, tipo, 0);
        }

        int idPedido = dao.guardar(pedido);
        pedido.setIdPedido(idPedido);

        entregaDao.guardar(new Entrega(null, idPedido, repartidorSeleccionado.getId(),
                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis())));
        dao.actualizarEstado(idPedido, "EN_REPARTO");
        pedido.setRepartidorAsignado(repartidorSeleccionado.getNombre());

        JOptionPane.showMessageDialog(this,
                "Pedido registrado correctamente.\nID: " + pedido.getIdPedido() +
                        "\nRepartidor: " + repartidorSeleccionado.getNombre(),
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        campoDireccion.setText("");
        comboTipo.setSelectedIndex(0);
        cargarRepartidores();
    }
}
