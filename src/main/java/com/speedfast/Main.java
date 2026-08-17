package com.speedfast;

import com.speedfast.model.Pedido;
import com.speedfast.model.PedidoComida;
import com.speedfast.model.PedidoEncomienda;
import com.speedfast.model.PedidoExpress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Pedido pedidoComida = new PedidoComida("Las Acacias 123", "Pedido Plus");
        Pedido pedidoEncomienda = new PedidoEncomienda("Dorsal 123", "Pedido normal");
        Pedido pedidoExpress = new PedidoExpress("Cauquenes 123", "Pedido programado");

        System.out.println("[Pedido Comida]");
        pedidoComida.asignarRepartidor("Juan Perez", true);
        pedidoComida.asignarRepartidor();
        System.out.println("\n");

        System.out.println("[Pedido Encomienda]");
        pedidoEncomienda.asignarRepartidor("Camila Soto", 39.8f);
        pedidoEncomienda.asignarRepartidor();
        System.out.println("\n");

        System.out.println("[Pedido Express]");
        pedidoExpress.asignarRepartidor("Juan Rodriguez");
        pedidoExpress.asignarRepartidor();
        System.out.println("\n");

    }
}
