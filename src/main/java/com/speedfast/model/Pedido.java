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
        System.out.println("Pedido " + this.idPedido + " asignado correctamente a " + nombreRepartidor);
    }

    protected String getIdPedido() {
        return idPedido;
    }
}
