package com.speedfast.model;

public class PedidoComida extends Pedido {
    private boolean mochilaTermica;

    public PedidoComida(String direccionEntrega, String tipoPedido, boolean mochilaTermica) {
        super(direccionEntrega, tipoPedido);
        this.mochilaTermica = mochilaTermica;
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Repartidor asignado correctamente para el pedido de comida");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        if (mochilaTermica) {
            System.out.println("Verificando mochila térmica... OK");
            System.out.println("Pedido " + getIdPedido() + " asignado a " + nombreRepartidor);
        } else {
            System.out.println("Verificando mochila térmica... Sin mochila térmica");
            System.out.println("Por favor consiga mochila térmica para asignar el pedido.");
        }
    }
}
