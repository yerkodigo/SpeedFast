package com.speedfast;

import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

public class Main {
    public static void main(String[] args) {
        Pedido pedidoComida = new PedidoComida("Las Acacias 123", "Pedido Plus", true, 10);
        Pedido pedidoEncomienda = new PedidoEncomienda("Dorsal 123", "Pedido normal", 39.8f, 8);
        Pedido pedidoExpress = new PedidoExpress("Cauquenes 123", "Pedido programado", 5);

        System.out.println("Pedido Comida #" + pedidoComida.getIdPedido());
        pedidoComida.mostrarResumen();
        pedidoComida.calcularTiempoEntrega();
        System.out.println("\n");

        System.out.println("Pedido Encomienda #" + pedidoEncomienda.getIdPedido());
        pedidoEncomienda.mostrarResumen();
        pedidoEncomienda.calcularTiempoEntrega();
        System.out.println("\n");

        System.out.println("Pedido Express #" + pedidoExpress.getIdPedido());
        pedidoExpress.mostrarResumen();
        pedidoExpress.calcularTiempoEntrega();
        System.out.println("\n");
    }
}
