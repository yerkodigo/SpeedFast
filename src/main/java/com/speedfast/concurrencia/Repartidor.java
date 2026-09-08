package com.speedfast.concurrencia;

import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.model.Pedido;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Repartidor implements Runnable {
    private final String nombre;
    private final List<Pedido> pedidosAsignados;
    private final ControladorDeEnvios controlador;

    public Repartidor(String nombre, List<Pedido> pedidosAsignados, ControladorDeEnvios controlador) {
        this.nombre = nombre;
        this.pedidosAsignados = pedidosAsignados;
        this.controlador = controlador;
    }

    @Override
    public void run() {
        for (Pedido pedido : pedidosAsignados) {
            try {
                pedido.asignarRepartidor(nombre);
                System.out.println("[Repartidor: " + nombre + "] Entregando Pedido"
                        + pedido.getNombreTipo() + " #" + pedido.getIdPedido() + "...");

                int tiempoEntregaMs = ThreadLocalRandom.current().nextInt(1000, 3001);
                Thread.sleep(tiempoEntregaMs);

                controlador.despacharPedido(pedido);
                System.out.println("[Repartidor: " + nombre + "] Pedido #" + pedido.getIdPedido() + " entregado.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Repartidor: " + nombre + "] Entrega interrumpida antes de completar el pedido #"
                        + pedido.getIdPedido() + ".");
            }
        }
        System.out.println("[Repartidor: " + nombre + "] Finalizó todas sus entregas.");
    }
}
