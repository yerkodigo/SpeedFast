package com.speedfast.vista;

import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.dao.PedidoDAO;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final ControladorDeEnvios controlador = new ControladorDeEnvios();
    private final PedidoDAO dao = new PedidoDAO();

    public VentanaPrincipal() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Panel de Gestión SpeedFast", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton botonRegistrar = new JButton("Registrar Pedido");
        JButton botonListar = new JButton("Listar Pedidos");
//        JButton botonAsignar = new JButton("Iniciar Entrega");
        JButton botonAgregarRepartidor = new JButton("Agregar Repartidor");
        JButton botonListarRepartidores = new JButton("Listar Repartidores");

        botonRegistrar.addActionListener(e -> new VentanaRegistroPedido(controlador, dao));
        botonListar.addActionListener(e -> new VentanaListaPedidos());
//        botonAsignar.addActionListener(e -> new VentanaAsignarRepartidor(controlador, dao));
        botonAgregarRepartidor.addActionListener(e -> new VentanaAgregarRepartidor());
        botonListarRepartidores.addActionListener(e -> new VentanaListaRepartidores());

        panelBotones.add(botonRegistrar);
        panelBotones.add(botonListar);
//        panelBotones.add(botonAsignar);
        panelBotones.add(botonAgregarRepartidor);
        panelBotones.add(botonListarRepartidores);

        add(panelBotones, BorderLayout.CENTER);

        setVisible(true);
    }
}
