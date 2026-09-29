package com.speedfast.vista;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.dao.RepartidorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaRepartidores extends JFrame {
    private final RepartidorDAO dao = new RepartidorDAO();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private static final String[] COLUMNAS = {"ID", "Nombre"};

    public VentanaListaRepartidores() {
        setTitle("Listado de Repartidores");
        setSize(400, 350);
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
        List<Repartidor> repartidores = dao.listarTodos();
        for (Repartidor repartidor : repartidores) {
            modeloTabla.addRow(new Object[]{
                    repartidor.getId(),
                    repartidor.getNombre()
            });
        }
    }
}
