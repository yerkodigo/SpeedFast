package com.speedfast.model;

import com.speedfast.interfaces.Cancelable;
import com.speedfast.interfaces.Despachable;

import java.util.UUID;

public abstract class Pedido implements Despachable, Cancelable {
    private final String idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private Integer distanciaKm;
    private String repartidorAsignado;
    private String estado;

    public Pedido(String direccionEntrega, String tipoPedido, Integer distanciaKm) {
        this.idPedido = UUID.randomUUID().toString().substring(0, 8);
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
        this.estado = "Pendiente";
    }

    public abstract String getNombreTipo();

    public abstract void calcularTiempoEntrega();

    public void mostrarResumen() {
        System.out.println("[Pedido " + getNombreTipo() + "]");
        System.out.println("Pedido #" + this.idPedido);
        System.out.println("Dirección: " + this.direccionEntrega);
        System.out.println("Distancia: " + this.distanciaKm + " km");
    }

    public void asignarRepartidor() {
        this.repartidorAsignado = "Repartidor de turno";
        System.out.println("Pedido " + this.idPedido + " asignado correctamente a " + this.repartidorAsignado);
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        this.repartidorAsignado = nombreRepartidor;
        System.out.println("Repartidor asignado: " + nombreRepartidor);
    }

    @Override
    public void despachar() {
        if (this.repartidorAsignado == null) {
            System.out.println("No se puede despachar el pedido #" + this.idPedido + ": no tiene repartidor asignado.");
            return;
        }
        this.estado = "Despachado";
        System.out.println("Pedido despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("Cancelando Pedido " + getNombreTipo() + " #" + this.idPedido + "...");
        this.estado = "Cancelado";
        System.out.println("→ Pedido cancelado exitosamente.");
    }

    protected void registrarRepartidor(String nombreRepartidor) {
        this.repartidorAsignado = nombreRepartidor;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Integer distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
}
