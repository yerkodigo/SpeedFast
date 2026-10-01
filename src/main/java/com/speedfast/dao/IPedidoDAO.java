package com.speedfast.dao;

import com.speedfast.model.Pedido;

import java.util.List;

public interface IPedidoDAO {
    //    CRUD
    //    create
    public int create(Pedido pedido);
    //    read
    public List<Pedido> readAll();
    //    update
    public void update(Pedido pedido);
    //    delete
    public void delete();
}
