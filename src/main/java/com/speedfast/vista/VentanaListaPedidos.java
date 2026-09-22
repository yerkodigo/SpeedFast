package com.speedfast.vista;

import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {
    private final ControladorDeEnvios controlador;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private static final String[] COLUMNAS = {
            "ID",
            "Dirección",
            "Tipo", "Distancia (km)",
            "Repartidor",
            "Estado"
    };

    public VentanaListaPedidos(ControladorDeEnvios controlador) {
        this.controlador = controlador;
        setTitle("Listado de Pedidos");
        setSize(650, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para no cerrar todo
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton botonRefrescar = new JButton("Refrescar");
        botonRefrescar.addActionListener(e -> refrescarTabla());
        JPanel panelInferior = new JPanel();
        panelInferior.add(botonRefrescar);
        add(panelInferior, BorderLayout.SOUTH);

        refrescarTabla();

        setVisible(true);
    }

    public void refrescarTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> pedidos = controlador.getPedidos();
        for (Pedido pedido : pedidos) {
            modeloTabla.addRow(new Object[]{
                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getNombreTipo(),
                    pedido.getDistanciaKm(),
                    pedido.getRepartidorAsignado() == null ? "Sin asignar" : pedido.getRepartidorAsignado(),
                    pedido.getEstado()
            });
        }
    }
}
