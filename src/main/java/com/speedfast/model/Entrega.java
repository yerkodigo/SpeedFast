package com.speedfast.model;

import java.sql.Date;
import java.sql.Time;

public class Entrega {
    private Integer id;
    private Integer id_pedido;
    private Integer id_repartidor;
    private Date fecha;
    private Time hora;

    public Entrega(Integer id, Integer idPedido, Integer idRepartidor, Date fecha, Time hora) {
        this.id = id;
        this.id_pedido = idPedido;
        this.id_repartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Integer getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(Integer id_repartidor) {
        this.id_repartidor = id_repartidor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }
}
