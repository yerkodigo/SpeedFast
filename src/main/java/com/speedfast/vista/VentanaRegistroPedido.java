package com.speedfast.vista;

import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {
    private final ControladorDeEnvios controlador;

    private JTextField campoId;
    private JTextField campoDireccion;
    private JComboBox<String> comboTipo;
    private JTextField campoDistancia;

    private JLabel etiquetaCampoExtra;
    private JTextField campoExtra;
    private JCheckBox checkMochilaTermica;

    public VentanaRegistroPedido(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("Nuevo Registro");
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // solo cierra ESTA ventana, no toda la app
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 5, 10));

        add(new JLabel("ID:"));
        campoId = new JTextField();
        add(campoId);

        add(new JLabel("Dirección:"));
        campoDireccion = new JTextField();
        add(campoDireccion);

        add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});
        comboTipo.addActionListener(e -> actualizarCampoExtra());
        add(comboTipo);

        add(new JLabel("Distancia (km):"));
        campoDistancia = new JTextField();
        add(campoDistancia);

//        me faltaba para el constructor
        etiquetaCampoExtra = new JLabel();
        add(etiquetaCampoExtra);
        campoExtra = new JTextField();
        checkMochilaTermica = new JCheckBox("Sí");
        add(campoExtra);
        add(checkMochilaTermica);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> validarYGuardar());
        add(new JLabel()); // celda vacía para alinear el botón a la derecha
        add(botonGuardar);

        actualizarCampoExtra();

        setVisible(true);
    }

    private void actualizarCampoExtra() {
        String tipo = (String) comboTipo.getSelectedItem();
        boolean esComida = "Comida".equals(tipo);
        boolean esEncomienda = "Encomienda".equals(tipo);

        etiquetaCampoExtra.setText(esEncomienda ? "Peso (kg):" : "Mochila térmica:");
        campoExtra.setVisible(esEncomienda);
        checkMochilaTermica.setVisible(esComida);
        campoExtra.setText("");

        etiquetaCampoExtra.setVisible(esComida || esEncomienda);
    }

    private void validarYGuardar() {
        String id = campoId.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        String distanciaTexto = campoDistancia.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo ID no puede estar vacío", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Dirección no puede estar vacío", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int distanciaKm;
        try {
            distanciaKm = Integer.parseInt(distanciaTexto);
            if (distanciaKm <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La distancia debe ser un número entero positivo", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pedido pedido;
        switch (tipo) {
            case "Comida" -> pedido = new PedidoComida(direccion, tipo, checkMochilaTermica.isSelected(), distanciaKm);
            case "Encomienda" -> {
                float peso;
                try {
                    peso = Float.parseFloat(campoExtra.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El peso debe ser un número válido", "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pedido = new PedidoEncomienda(direccion, tipo, peso, distanciaKm);
            }
            default -> pedido = new PedidoExpress(direccion, tipo, distanciaKm);
        }

        controlador.agregarPedido(pedido);

        JOptionPane.showMessageDialog(this,
                "Pedido registrado correctamente.\nID: " + pedido.getIdPedido(),
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        campoId.setText("");
        campoDireccion.setText("");
        campoDistancia.setText("");
        campoExtra.setText("");
        checkMochilaTermica.setSelected(false);
        comboTipo.setSelectedIndex(0);
    }
}
