package com.java.recetayainventario.service;

import com.java.recetayainventario.model.Sucursal;
import com.java.recetayainventario.repository.SucursalRepository;
import com.java.recetayainventario.repository.StockSucursalRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository repository;
    private final StockSucursalRepository stockRepository;

    public SucursalServiceImpl(
            SucursalRepository repository,
            StockSucursalRepository stockRepository) {
        this.repository = repository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Sucursal guardar(Sucursal sucursal) {

        validar(sucursal);

        sucursal.setId(null);
        return repository.save(sucursal);
    }

    @Override
    public List<Sucursal> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Sucursal> buscaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Sucursal> actualizar(Long id, Sucursal datos) {

        validar(datos);

        return repository.findById(id)
            .map(sucursal -> {
                sucursal.setNombre(datos.getNombre());
                sucursal.setDireccion(datos.getDireccion());
                return repository.save(sucursal);
            });
    }

    @Override
    public boolean eliminar(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        if (stockRepository.existsBySucursal_Id(id)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "La sucursal tiene stock asociado");
        }

        repository.deleteById(id);
        return true;
    }

    private void validar(Sucursal sucursal) {

        if (sucursal == null
                || sucursal.getNombre() == null
                || sucursal.getNombre().isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre de la sucursal es obligatorio");
        }
    }
}