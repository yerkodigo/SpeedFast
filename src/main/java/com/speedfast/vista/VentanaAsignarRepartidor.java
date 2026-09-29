package com.speedfast.vista;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.dao.PedidoDAO;
import com.speedfast.model.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaAsignarRepartidor extends JFrame {
    private final ControladorDeEnvios controlador;
    private JComboBox<Pedido> comboPedidos;
    private JLabel etiquetaRepartidor;
    private JButton botonAsignar;
    private final PedidoDAO dao;

    public VentanaAsignarRepartidor(ControladorDeEnvios controlador, PedidoDAO dao) {
        this.controlador = controlador;
        this.dao = dao;

        setTitle("Iniciar Entrega");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 10));

        add(new JLabel("Pedido en reparto:"));
        comboPedidos = new JComboBox<>();
        comboPedidos.addActionListener(e -> actualizarRepartidorMostrado());
        cargarPedidosEnReparto();
        add(comboPedidos);

        add(new JLabel("Repartidor asignado:"));
        etiquetaRepartidor = new JLabel();
        add(etiquetaRepartidor);

        JButton botonRefrescar = new JButton("Refrescar en reparto");
        botonRefrescar.addActionListener(e -> cargarPedidosEnReparto());
        add(botonRefrescar);

        botonAsignar = new JButton("Iniciar Entrega");
        botonAsignar.addActionListener(e -> iniciarEntrega());
        add(botonAsignar);

        setVisible(true);
    }

    private void cargarPedidosEnReparto() {
        comboPedidos.removeAllItems();
        for (Pedido pedido : dao.listarEnReparto()) {
            comboPedidos.addItem(pedido);
        }
        actualizarRepartidorMostrado();
    }

    private void actualizarRepartidorMostrado() {
        Pedido pedidoSeleccionado = (Pedido) comboPedidos.getSelectedItem();
        etiquetaRepartidor.setText(pedidoSeleccionado == null ? "" : pedidoSeleccionado.getRepartidorAsignado());
    }

    private void iniciarEntrega() {
        Pedido pedidoSeleccionado = (Pedido) comboPedidos.getSelectedItem();

        if (pedidoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay pedidos en reparto para iniciar", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreRepartidor = pedidoSeleccionado.getRepartidorAsignado();

        List<Pedido> pedidosAsignados = new ArrayList<>();
        pedidosAsignados.add(pedidoSeleccionado);

        Repartidor repartidor = new Repartidor(nombreRepartidor, pedidosAsignados, controlador);
        Thread hiloRepartidor = new Thread(repartidor, "Repartidor-" + nombreRepartidor);
        hiloRepartidor.start();

        JOptionPane.showMessageDialog(this,
                "Entrega iniciada con el repartidor " + nombreRepartidor + ".\n" + "El pedido #" + pedidoSeleccionado.getIdPedido() + " se despachará en unos segundos.",
                "Entrega en curso",
                JOptionPane.INFORMATION_MESSAGE);

        cargarPedidosEnReparto();
    }
}
