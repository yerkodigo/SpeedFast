package com.speedfast.model;

public class PedidoComida extends Pedido {
    private boolean mochilaTermica;

    public PedidoComida(String direccionEntrega, String tipoPedido, boolean mochilaTermica, Integer distanciaKm) {
        super(direccionEntrega, tipoPedido, distanciaKm);
        this.mochilaTermica = mochilaTermica;
    }

    @Override
    public String getNombreTipo() {
        return "Comida";
    }

    @Override
    public void calcularTiempoEntrega() {
        int tiempo = 15 + (2 * this.getDistanciaKm());
        System.out.println("Tiempo estimado: " + tiempo + " minutos");
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
            registrarRepartidor(nombreRepartidor);
            System.out.println("Repartidor asignado: " + nombreRepartidor);
        } else {
            System.out.println("Verificando mochila térmica... Sin mochila térmica");
            System.out.println("Por favor consiga mochila térmica para asignar el pedido.");
        }
    }
}
