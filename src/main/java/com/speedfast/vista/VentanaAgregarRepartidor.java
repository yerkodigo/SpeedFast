package com.speedfast.vista;

import com.speedfast.dao.RepartidorDAO;

import javax.swing.*;
import java.awt.*;

public class VentanaAgregarRepartidor extends JFrame {
    private final RepartidorDAO dao = new RepartidorDAO();
    private JTextField campoNombre;

    public VentanaAgregarRepartidor() {
        setTitle("Nuevo Repartidor");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // solo cierra ESTA ventana, no toda la app
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 5, 10));

        add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        add(campoNombre);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> validarYGuardar());
        add(new JLabel()); // celda vacia para alinear el boton a la derecha
        add(botonGuardar);

        setVisible(true);
    }

    private void validarYGuardar() {
        String nombre = campoNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Nombre no puede estar vacío", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idGenerado = dao.guardar(nombre);

        JOptionPane.showMessageDialog(this,
                "Repartidor registrado correctamente.\nID: " + idGenerado,
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
