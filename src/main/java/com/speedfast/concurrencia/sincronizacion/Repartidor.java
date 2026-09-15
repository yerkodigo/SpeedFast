package com.speedfast.concurrencia.sincronizacion;

import java.util.concurrent.ThreadLocalRandom;

public class Repartidor implements Runnable {
    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        Pedido pedido;
        while ((pedido = zonaDeCarga.retirarPedido()) != null) {
            System.out.println("[Repartidor - " + nombre + "] Retirando el pedido #" + pedido.getId() + "...");

            pedido.setEstado(EstadoPedido.EN_REPARTO);
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Repartidor - " + nombre + "] Entrega interrumpida antes de completar el pedido #"
                        + pedido.getId() + ".");
                return;
            }

            System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");
            pedido.setEstado(EstadoPedido.ENTREGADO);
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
        }
        System.out.println("[Repartidor - " + nombre + "] No quedan pedidos por retirar. Finalizó su turno.");
    }
}
