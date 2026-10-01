package com.speedfast.dao;

import com.speedfast.concurrencia.Repartidor;

import java.util.List;

public interface IRepartidorDAO {
//    CRUD
//    create
    public int create(Repartidor repartidor);
//    read
    public List<Repartidor> readAll();
//    update
    public void update();
//    delete
    public void delete();
}
