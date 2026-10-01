package com.speedfast.dao;

import com.speedfast.model.Entrega;

import java.util.List;

public interface IEntregaDAO {
    //    CRUD
    //    create
    public void create(Entrega entrega);
    //    read
    public List<Entrega> readAll();
    //    update
    public void update();
    //    delete
    public void delete();
}
