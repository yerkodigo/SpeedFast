package com.speedfast.model;

import java.util.UUID;

public class Pedido {
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;

    public Pedido(String direccionEntrega, String tipoPedido) {
        this.idPedido = UUID.randomUUID().toString().substring(0, 8);
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }

    public void asignarRepartidor() {
        System.out.println("Pedido " + this.idPedido + " asignado correctamente");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        System.out.println("Repartidor más cercano con disponibilidad inmediata encontrado.");
        System.out.println("Pedido " + this.idPedido + " asignado correctamente a " + nombreRepartidor);
    }

    public void asignarRepartidor(String nombreRepartidor, boolean mochilaTermica) {
        System.out.print("Asignando repartidor...");
        if (!nombreRepartidor.isEmpty() && mochilaTermica) {
            System.out.println("Verificando mochila térmica... OK");
            System.out.println("Pedido " + this.idPedido + " asignado correctamente a " + nombreRepartidor);
        } else {
            System.out.println("Verificando mochila térmica... Sin mochila térmica");
            System.out.println("Por favor consiga mochila térmica para asignar el pedido.");
        }
    }

    public void asignarRepartidor(String nombreRepartidor, Float peso) {
        System.out.print("Asignando repartidor...");
        if (!nombreRepartidor.isEmpty() && peso < 60.0) {
            System.out.println("Validando peso y embalaje... OK");
            System.out.println("Pedido " + this.idPedido + " asignado correctamente a " + nombreRepartidor);
        } else {
            System.out.println("Verificando peso... Peso no válido");
            System.out.println("Por favor ingrese un peso válido para asignar el pedido.");
        }
    }
}
