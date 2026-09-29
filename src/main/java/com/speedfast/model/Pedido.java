package com.speedfast.model;

import com.speedfast.interfaces.Cancelable;
import com.speedfast.interfaces.Despachable;

import java.util.UUID;

public abstract class Pedido implements Despachable, Cancelable {
    private Integer idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private Integer distanciaKm;
    private String repartidorAsignado;
    private String estado;

    public Pedido(String direccionEntrega, String tipoPedido, Integer distanciaKm) {
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
        this.estado = "PENDIENTE";
    }

    public Pedido(Integer idPedido, String direccionEntrega, String tipoPedido, String estado) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
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
        this.estado = "ENTREGADO";
        System.out.println("Pedido despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("Cancelando Pedido " + getNombreTipo() + " #" + this.idPedido + "...");
        this.estado = "CANCELADO";
        System.out.println("→ Pedido cancelado exitosamente.");
    }

    protected void registrarRepartidor(String nombreRepartidor) {
        this.repartidorAsignado = nombreRepartidor;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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

    @Override
    public String toString() {
        return "#" + idPedido + " - " + getNombreTipo() + " - " + direccionEntrega;
    }
}
