package com.speedfast;

import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

public class Main {
    public static void main(String[] args) {
        Pedido pedidoComida = new PedidoComida("Las Acacias 123", "Pedido Plus", true);
        Pedido pedidoEncomienda = new PedidoEncomienda("Dorsal 123", "Pedido normal", 39.8f);
        Pedido pedidoExpress = new PedidoExpress("Cauquenes 123", "Pedido programado");

        System.out.println("[Pedido Comida]");
        pedidoComida.asignarRepartidor();
        pedidoComida.asignarRepartidor("Juan Pérez");
        System.out.println("\n");

        System.out.println("[Pedido Encomienda]");
        pedidoEncomienda.asignarRepartidor();
        pedidoEncomienda.asignarRepartidor("Camila Soto");
        System.out.println("\n");

        System.out.println("[Pedido Express]");
        pedidoExpress.asignarRepartidor();
        pedidoExpress.asignarRepartidor("Luis Díaz");
        System.out.println("\n");
    }
}
