package com.speedfast.vista;

import com.speedfast.controlador.ControladorDeEnvios;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final ControladorDeEnvios controlador = new ControladorDeEnvios();

    public VentanaPrincipal() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Panel de Gestión SpeedFast", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton botonRegistrar = new JButton("Registrar Pedido");
        JButton botonListar = new JButton("Listar Pedidos");
        JButton botonAsignar = new JButton("Asignar repartidor / Iniciar Entrega");

        botonRegistrar.addActionListener(e -> new VentanaRegistroPedido(controlador));
        botonListar.addActionListener(e -> new VentanaListaPedidos(controlador));
        botonAsignar.addActionListener(e -> new VentanaAsignarRepartidor(controlador));

        panelBotones.add(botonRegistrar);
        panelBotones.add(botonListar);
        panelBotones.add(botonAsignar);

        add(panelBotones, BorderLayout.CENTER);

        setVisible(true);
    }
}
