package com.speedfast.controlador;

import com.speedfast.interfaces.Rastreable;
import com.speedfast.model.Pedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControladorDeEnvios implements Rastreable {
    private final List<Pedido> historialEntregas = Collections.synchronizedList(new ArrayList<>());

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
