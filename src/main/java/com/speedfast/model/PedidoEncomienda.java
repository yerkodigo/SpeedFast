package com.speedfast.model;

public class PedidoEncomienda extends Pedido {
    private float peso;

    public PedidoEncomienda(String direccionEntrega, String tipoPedido, float peso, Integer distanciaKm) {
        super(direccionEntrega, tipoPedido, distanciaKm);
        this.peso = peso;
    }

    @Override
    public String getNombreTipo() {
        return "Encomienda";
    }

    @Override
    public void calcularTiempoEntrega() {
        int tiempo = 20 + Math.round(1.5f * this.getDistanciaKm());
        System.out.println("Tiempo estimado: " + tiempo + " minutos");
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
            registrarRepartidor(nombreRepartidor);
            System.out.println("Repartidor asignado: " + nombreRepartidor);
        } else {
            System.out.println("Verificando peso... Peso no válido");
            System.out.println("Por favor ingrese un peso válido para asignar el pedido.");
        }
    }
}
