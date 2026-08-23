package com.speedfast.model;

public class PedidoExpress extends Pedido {
    @Override
    public void calcularTiempoEntrega() {
        int tiempo = 10;
        if (this.getDistanciaKm() > 5) {
            tiempo += 5;
        }
        System.out.println("Tiempo estimado de entrega: " + tiempo + " minutos");
    }

    public PedidoExpress(String direccionEntrega, String tipoPedido, Integer distanciaKm) {
        super(direccionEntrega, tipoPedido,  distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Repartidor asignado correctamente para el pedido express");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        System.out.println("Repartidor más cercano con disponibilidad inmediata encontrado.");
        System.out.println("Pedido " + getIdPedido() + " asignado a " + nombreRepartidor);
    }
}
