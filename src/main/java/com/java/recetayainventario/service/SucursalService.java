package com.java.recetayainventario.service;

import com.java.recetayainventario.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalService {

    Sucursal guardar(Sucursal sucursal);

    List<Sucursal> obtenerTodos();

    Optional<Sucursal> buscaPorId(Long id);

    Optional<Sucursal> actualizar(Long id, Sucursal datos);

    boolean eliminar(Long id);
}