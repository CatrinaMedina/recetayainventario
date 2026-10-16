package com.java.recetayainventario.service;

import com.java.recetayainventario.model.Medicamento;
import com.java.recetayainventario.repository.MedicamentoRepository;
import com.java.recetayainventario.repository.StockSucursalRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository repository;
    private final StockSucursalRepository stockRepository;

    public MedicamentoServiceImpl(
            MedicamentoRepository repository,
            StockSucursalRepository stockRepository) {
        this.repository = repository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Medicamento guardar(Medicamento medicamento) {

        validar(medicamento);

        medicamento.setId(null);
        return repository.save(medicamento);
    }

    @Override
    public List<Medicamento> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Medicamento> buscaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Medicamento> actualizar(Long id, Medicamento datos) {

        validar(datos);

        return repository.findById(id)
            .map(medicamento -> {
                medicamento.setNombre(datos.getNombre());
                medicamento.setDescripcion(datos.getDescripcion());
                return repository.save(medicamento);
            });
    }

    @Override
    public boolean eliminar(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        if (stockRepository.existsByMedicamento_Id(id)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "El medicamento tiene stock asociado");
        }

        repository.deleteById(id);
        return true;
    }

    private void validar(Medicamento medicamento) {

        if (medicamento == null
                || medicamento.getNombre() == null
                || medicamento.getNombre().isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre del medicamento es obligatorio");
        }
    }
}