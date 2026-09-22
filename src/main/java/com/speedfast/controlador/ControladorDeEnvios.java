package com.speedfast.controlador;

import com.speedfast.interfaces.Rastreable;
import com.speedfast.model.Pedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControladorDeEnvios implements Rastreable {
    private final List<Pedido> pedidos = Collections.synchronizedList(new ArrayList<>());
    private final List<Pedido> historialEntregas = Collections.synchronizedList(new ArrayList<>());

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    public List<Pedido> getPedidosPendientes() {
        List<Pedido> pendientes = new ArrayList<>();
        synchronized (pedidos) {
            for (Pedido pedido : pedidos) {
                if (!"Despachado".equals(pedido.getEstado()) && !"Cancelado".equals(pedido.getEstado())) {
                    pendientes.add(pedido);
                }
            }
        }
        return pendientes;
    }

    public synchronized void despacharPedido(Pedido pedido) {
        pedido.despachar();
        if ("Despachado".equals(pedido.getEstado())) {
            historialEntregas.add(pedido);
        }
    }

    public void cancelarPedido(Pedido pedido) {
        pedido.cancelar();
    }

    @Override
    public void verHistorial() {
        System.out.println("Historial:");
        for (Pedido pedido : historialEntregas) {
            System.out.println("- Pedido" +
                pedido.getNombreTipo() +
                " #"+
                pedido.getIdPedido() +
                " – entregado por " +
                pedido.getRepartidorAsignado()
            );
        }
    }
}
