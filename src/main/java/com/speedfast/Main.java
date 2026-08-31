package com.speedfast;

import com.speedfast.controlador.ControladorDeEnvios;
import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

public class Main {
    public static void main(String[] args) {
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        Pedido pedidoComida = new PedidoComida("Las Acacias 123", "Pedido Plus", true, 10);
        Pedido pedidoEncomienda = new PedidoEncomienda("Dorsal 123", "Pedido normal", 39.8f, 8);
        Pedido pedidoExpress = new PedidoExpress("Cauquenes 123", "Pedido programado", 5);

        // asignacion manual
        pedidoComida.mostrarResumen();
        pedidoComida.asignarRepartidor("Luis Diaz");
        pedidoComida.calcularTiempoEntrega();
        controlador.despacharPedido(pedidoComida);
        System.out.println();

        // asignacion manual
        pedidoEncomienda.mostrarResumen();
        pedidoEncomienda.asignarRepartidor("Daniela Tapia");
        pedidoEncomienda.calcularTiempoEntrega();
        controlador.despacharPedido(pedidoEncomienda);
        System.out.println();

        // asignacion automatica y cancelacion
        pedidoExpress.mostrarResumen();
        pedidoExpress.asignarRepartidor();
        pedidoExpress.calcularTiempoEntrega();
        controlador.cancelarPedido(pedidoExpress);
        System.out.println();

        controlador.verHistorial();
    }
}
