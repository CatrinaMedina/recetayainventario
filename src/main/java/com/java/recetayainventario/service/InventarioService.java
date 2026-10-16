package com.java.recetayainventario.service;

import com.java.recetayainventario.model.StockSucursal;
import com.java.recetayainventario.model.SolicitudStock;

import java.util.List;
import java.util.Optional;

public interface InventarioService {

    StockSucursal guardar(SolicitudStock datos);

    List<StockSucursal> obtenerTodos();

    Optional<StockSucursal> buscaPorId(Long id);

    List<StockSucursal> buscarPorSucursal(Long id);

    Optional<StockSucursal> actualizar(Long id, SolicitudStock datos);

    boolean eliminar(Long id);

    StockSucursal reservar(SolicitudStock datos);

    StockSucursal dispensar(SolicitudStock datos);

    List<StockSucursal> dispensarLote(List<SolicitudStock> solicitudes);

    StockSucursal reponer(SolicitudStock datos);
}