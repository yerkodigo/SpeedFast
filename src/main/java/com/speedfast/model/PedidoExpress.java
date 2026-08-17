package com.speedfast.model;

public class PedidoExpress extends Pedido {
    public PedidoExpress(String direccionEntrega, String tipoPedido) {
        super(direccionEntrega, tipoPedido);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Repartidor asignado correctamente para el pedido express");
    }
}
