package com.speedfast.main;

import com.speedfast.controlador.ConexionBD;
import com.speedfast.vista.VentanaPrincipal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Ejecutando main");
        try (Connection conn = ConexionBD.obtenerConexion()) {
            System.out.println("✅ Conexión exitosa a la base de datos.");
            SwingUtilities.invokeLater(VentanaPrincipal::new);
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
}
