package com.java.recetayainventario.service;

import com.java.recetayainventario.model.*;
import com.java.recetayainventario.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final StockSucursalRepository repository;
    private final MedicamentoRepository medicamentoRepository;
    private final SucursalRepository sucursalRepository;

    public InventarioServiceImpl(
            StockSucursalRepository repository,
            MedicamentoRepository medicamentoRepository,
            SucursalRepository sucursalRepository) {

        this.repository = repository;
        this.medicamentoRepository = medicamentoRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public StockSucursal guardar(SolicitudStock datos) {

        validarSolicitud(datos, true);

        if (repository.findByMedicamento_IdAndSucursal_Id(
                datos.getMedicamentoId(),
                datos.getSucursalId()).isPresent()) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Ya existe stock para ese medicamento y sucursal");
        }

        Medicamento medicamento = medicamentoRepository
            .findById(datos.getMedicamentoId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Medicamento no encontrado"));

        Sucursal sucursal = sucursalRepository
            .findById(datos.getSucursalId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Sucursal no encontrada"));

        StockSucursal stock = new StockSucursal(
            medicamento, sucursal, datos.getCantidad());

        return repository.save(stock);
    }

    @Override
    public List<StockSucursal> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<StockSucursal> buscaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<StockSucursal> buscarPorSucursal(Long id) {
        return repository.findBySucursal_Id(id);
    }

    @Override
    public Optional<StockSucursal> actualizar(Long id, SolicitudStock datos) {

        if (datos == null
                || datos.getCantidad() == null
                || datos.getCantidad() < 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La cantidad debe ser mayor o igual a cero");
        }

        return repository.findById(id)
            .map(stock -> {
                stock.setCantidadDisponible(datos.getCantidad());
                return repository.save(stock);
            });
    }

    @Override
    public boolean eliminar(Long id) {

        Optional<StockSucursal> encontrado = repository.findById(id);

        if (encontrado.isEmpty()) {
            return false;
        }

        if (encontrado.get().getCantidadReservada() > 0) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No se puede eliminar stock reservado");
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public StockSucursal reservar(SolicitudStock datos) {

        validarSolicitud(datos, false);

        StockSucursal stock = obtenerStock(datos);

        if (stock.getCantidadDisponible() < datos.getCantidad()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Stock insuficiente");
        }

        stock.setCantidadDisponible(
            stock.getCantidadDisponible() - datos.getCantidad());

        stock.setCantidadReservada(
            stock.getCantidadReservada() + datos.getCantidad());

        return repository.save(stock);
    }

    @Override
    public StockSucursal dispensar(SolicitudStock datos) {

        validarSolicitud(datos, false);

        StockSucursal stock = obtenerStock(datos);

        if (stock.getCantidadReservada() < datos.getCantidad()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No existe suficiente stock reservado");
        }

        stock.setCantidadReservada(
            stock.getCantidadReservada() - datos.getCantidad());

        return repository.save(stock);
    }

    @Override
    public List<StockSucursal> dispensarLote(List<SolicitudStock> solicitudes) {

        if (solicitudes == null || solicitudes.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debe indicar al menos un medicamento");
        }

        List<StockSucursal> stocks = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for (SolicitudStock datos : solicitudes) {

            validarSolicitud(datos, false);

            StockSucursal stock = obtenerStock(datos);

            int posicion = -1;

            for (int i = 0; i < stocks.size(); i++) {
                if (stocks.get(i).getId().equals(stock.getId())) {
                    posicion = i;
                    break;
                }
            }

            if (posicion == -1) {
                stocks.add(stock);
                cantidades.add(datos.getCantidad());
            } else {

                long total = (long) cantidades.get(posicion) + datos.getCantidad();

                if (total > Integer.MAX_VALUE) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Cantidad demasiado grande");
                }

                cantidades.set(posicion, (int) total);
            }
        }

        for (int i = 0; i < stocks.size(); i++) {

            if (stocks.get(i).getCantidadReservada() < cantidades.get(i)) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No existe suficiente stock reservado");
            }
        }

        for (int i = 0; i < stocks.size(); i++) {

            StockSucursal stock = stocks.get(i);

            stock.setCantidadReservada(
                stock.getCantidadReservada() - cantidades.get(i));
        }

        return repository.saveAll(stocks);
    }

    @Override
    public StockSucursal reponer(SolicitudStock datos) {

        validarSolicitud(datos, false);

        StockSucursal stock = obtenerStock(datos);

        stock.setCantidadDisponible(
            stock.getCantidadDisponible() + datos.getCantidad());

        return repository.save(stock);
    }

    private StockSucursal obtenerStock(SolicitudStock datos) {

        return repository.findByMedicamento_IdAndSucursal_Id(
                datos.getMedicamentoId(),
                datos.getSucursalId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe stock para ese medicamento y sucursal"));
    }

    private void validarSolicitud(SolicitudStock datos, boolean permitirCero) {

        if (datos == null
                || datos.getMedicamentoId() == null
                || datos.getSucursalId() == null
                || datos.getCantidad() == null
                || datos.getCantidad() < (permitirCero ? 0 : 1)) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Datos de stock incompletos o cantidad invalida");
        }
    }
}