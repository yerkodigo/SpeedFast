package com.speedfast;

import com.speedfast.concurrencia.sincronizacion.Pedido;
import com.speedfast.concurrencia.sincronizacion.Repartidor;
import com.speedfast.concurrencia.sincronizacion.ZonaDeCarga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();
        System.out.println();

        zonaDeCarga.agregarPedido(new Pedido(1, "Santiago Centro"));
        zonaDeCarga.agregarPedido(new Pedido(2, "Providencia"));
        zonaDeCarga.agregarPedido(new Pedido(3, "Ñuñoa"));
        zonaDeCarga.agregarPedido(new Pedido(4, "Recoleta"));
        zonaDeCarga.agregarPedido(new Pedido(5, "Las condes"));
        System.out.println();

        Repartidor juan = new Repartidor("Juan", zonaDeCarga);
        Repartidor camila = new Repartidor("Camila", zonaDeCarga);
        Repartidor pedro = new Repartidor("Pedro", zonaDeCarga);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(juan);
        executor.submit(camila);
        executor.submit(pedro);

        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                System.out.println("La simulación excedió el tiempo de espera y fue forzada a detenerse.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.out.println("La simulación fue interrumpida antes de finalizar.");
        }

        System.out.println();
        System.out.println("[Zona de carga vacía]");
        System.out.println("Todos los pedidos han sido entregados correctamente.");
    }
}
