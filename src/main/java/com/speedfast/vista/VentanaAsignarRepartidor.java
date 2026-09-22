package com.speedfast.vista;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.model.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaAsignarRepartidor extends JFrame {
    private final ControladorDeEnvios controlador;
    private JComboBox<Pedido> comboPedidos;
    private JTextField campoNombreRepartidor;
    private JButton botonAsignar;

    public VentanaAsignarRepartidor(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("Asignar Repartidor / Iniciar Entrega");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 10));

        add(new JLabel("Pedido pendiente:"));
        comboPedidos = new JComboBox<>();
        cargarPedidosPendientes();
        add(comboPedidos);

        add(new JLabel("Nombre del repartidor:"));
        campoNombreRepartidor = new JTextField();
        add(campoNombreRepartidor);

        JButton botonRefrescar = new JButton("Refrescar pendientes");
        botonRefrescar.addActionListener(e -> cargarPedidosPendientes());
        add(botonRefrescar);

        botonAsignar = new JButton("Asignar e Iniciar Entrega");
        botonAsignar.addActionListener(e -> asignarYDespachar());
        add(botonAsignar);

        setVisible(true);
    }

    private void cargarPedidosPendientes() {
        comboPedidos.removeAllItems();
        for (Pedido pedido : controlador.getPedidosPendientes()) {
            comboPedidos.addItem(pedido);
        }
    }

    private void asignarYDespachar() {
        Pedido pedidoSeleccionado = (Pedido) comboPedidos.getSelectedItem();
        String nombreRepartidor = campoNombreRepartidor.getText().trim();

        if (pedidoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes para asignar", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nombreRepartidor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Nombre del repartidor no puede estar vacío", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Pedido> pedidosAsignados = new ArrayList<>();
        pedidosAsignados.add(pedidoSeleccionado);

        Repartidor repartidor = new Repartidor(nombreRepartidor, pedidosAsignados, controlador);
        Thread hiloRepartidor = new Thread(repartidor, "Repartidor-" + nombreRepartidor);
        hiloRepartidor.start();

        JOptionPane.showMessageDialog(this,
                "Entrega iniciada con el repartidor " + nombreRepartidor + ".\n" + "El pedido #" + pedidoSeleccionado.getIdPedido() + " se despachará en unos segundos.",
                "Entrega en curso",
                JOptionPane.INFORMATION_MESSAGE);

        campoNombreRepartidor.setText("");
        cargarPedidosPendientes();
    }
}
