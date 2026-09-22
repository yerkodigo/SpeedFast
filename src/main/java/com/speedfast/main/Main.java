package com.speedfast.main;

import com.speedfast.vista.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ejecutando main");
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}
