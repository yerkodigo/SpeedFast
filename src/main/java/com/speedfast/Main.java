package com.speedfast;

import com.speedfast.concurrencia.Repartidor;
import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        Repartidor camila = new Repartidor("Camila", List.of(
                new PedidoComida("Las Acacias 123", "Pedido Plus", true, 10),
                new PedidoEncomienda("Dorsal 123", "Pedido normal", 39.8f, 8)
        ), controlador);

        Repartidor luis = new Repartidor("Luis", List.of(
                new PedidoExpress("Cauquenes 123", "Pedido programado", 5),
                new PedidoComida("Vicuña Mackenna 456", "Pedido normal", true, 3)
        ), controlador);

        Repartidor daniela = new Repartidor("Daniela", List.of(
                new PedidoEncomienda("Los Leones 111", "Pedido normal", 12.5f, 6),
                new PedidoExpress("Providencia 321", "Pedido programado", 2)
        ), controlador);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(camila);
        executor.submit(luis);
        executor.submit(daniela);

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
        controlador.verHistorial();
    }
}
