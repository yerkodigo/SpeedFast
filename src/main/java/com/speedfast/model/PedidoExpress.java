package com.speedfast.model;

public class PedidoExpress extends Pedido {
    public PedidoExpress(String direccionEntrega, String tipoPedido, Integer distanciaKm) {
        super(direccionEntrega, tipoPedido, distanciaKm);
    }

    @Override
    public String getNombreTipo() {
        return "Express";
    }

    @Override
    public void calcularTiempoEntrega() {
        int tiempo = 10;
        if (this.getDistanciaKm() > 5) {
            tiempo += 5;
        }
        System.out.println("Tiempo estimado: " + tiempo + " minutos");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Repartidor más cercano con disponibilidad inmediata encontrado.");
        registrarRepartidor("Repartidor de turno");
        System.out.println("Repartidor asignado: " + getRepartidorAsignado());
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        System.out.println("Repartidor más cercano con disponibilidad inmediata encontrado.");
        registrarRepartidor(nombreRepartidor);
        System.out.println("Repartidor asignado: " + nombreRepartidor);
    }
}
