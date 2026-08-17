package com.speedfast.model;

public class PedidoEncomienda extends Pedido {
    private float peso;

    public PedidoEncomienda(String direccionEntrega, String tipoPedido, float peso) {
        super(direccionEntrega, tipoPedido);
        this.peso = peso;
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Repartidor asignado correctamente para el pedido de encomienda");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        if (peso < 60.0f) {
            System.out.println("Validando peso y embalaje... OK");
            System.out.println("Pedido " + getIdPedido() + " asignado a " + nombreRepartidor);
        } else {
            System.out.println("Verificando peso... Peso no válido");
            System.out.println("Por favor ingrese un peso válido para asignar el pedido.");
        }
    }
}
