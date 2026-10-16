package com.java.recetayainventario.service;

import com.java.recetayainventario.model.Medicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoService {

    Medicamento guardar(Medicamento medicamento);

    List<Medicamento> obtenerTodos();

    Optional<Medicamento> buscaPorId(Long id);

    Optional<Medicamento> actualizar(Long id, Medicamento datos);

    boolean eliminar(Long id);
}