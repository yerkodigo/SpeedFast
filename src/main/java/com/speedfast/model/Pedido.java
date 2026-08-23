package com.speedfast.model;

import java.util.UUID;

public abstract class Pedido {
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private Integer distanciaKm;

    public void mostrarResumen() {
        System.out.println("Dirección: " + this.direccionEntrega);
        System.out.println("Distancia: " + this.distanciaKm + " km");
    }

    public abstract void calcularTiempoEntrega();

    public Pedido(String direccionEntrega, String tipoPedido, Integer distanciaKm) {
        this.idPedido = UUID.randomUUID().toString().substring(0, 8);
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
    }

    public void asignarRepartidor() {
        System.out.println("Pedido " + this.idPedido + " asignado correctamente");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        System.out.println("Pedido " + this.idPedido + " asignado correctamente a " + nombreRepartidor);
    }

    public String getIdPedido() {
        return idPedido;
    }

    public Integer getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Integer distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

}
