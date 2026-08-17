package com.speedfast.model;

public class PedidoEncomienda extends Pedido {
    public PedidoEncomienda(String direccionEntrega, String tipoPedido) {
        super(direccionEntrega, tipoPedido);
    }

    @Override
    public void asignarRepartidor() {
        System.out.print("Repartidor asignado correctamente para el pedido de encomienda");
    }
}
