package com.speedfast.model;

public class PedidoComida extends Pedido {

    public PedidoComida(String direccionEntrega, String tipoPedido) {
        super(direccionEntrega, tipoPedido);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Repartidor asignado correctamente para el pedido de comida");
    }
}
