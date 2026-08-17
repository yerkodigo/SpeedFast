package com.speedfast.model;

public class PedidoExpress extends Pedido {
    public PedidoExpress(String direccionEntrega, String tipoPedido) {
        super(direccionEntrega, tipoPedido);
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
