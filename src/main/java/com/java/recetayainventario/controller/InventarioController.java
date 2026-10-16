
package com.java.recetayainventario.controller;

import com.java.recetayainventario.model.StockSucursal;
import com.java.recetayainventario.model.SolicitudStock;
import com.java.recetayainventario.service.InventarioService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping("/stock")
    public ResponseEntity<StockSucursal> guardar(
            @RequestBody SolicitudStock datos) {

        StockSucursal nuevoStock = inventarioService.guardar(datos);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoStock);
    }

    @GetMapping("/stock")
    public List<StockSucursal> obtenerTodos() {
        return inventarioService.obtenerTodos();
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<StockSucursal> buscaPorId(
            @PathVariable Long id) {

        return inventarioService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sucursal/{id}")
    public List<StockSucursal> buscarPorSucursal(
            @PathVariable Long id) {

        return inventarioService.buscarPorSucursal(id);
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<StockSucursal> actualizar(
            @PathVariable Long id,
            @RequestBody SolicitudStock datos) {

        return inventarioService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (inventarioService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reservas")
    public StockSucursal reservar(
            @RequestBody SolicitudStock datos) {

        return inventarioService.reservar(datos);
    }

    @PostMapping("/dispensaciones")
    public StockSucursal dispensar(
            @RequestBody SolicitudStock datos) {

        return inventarioService.dispensar(datos);
    }

    @PostMapping("/dispensaciones/lote")
    public List<StockSucursal> dispensarLote(
            @RequestBody List<SolicitudStock> solicitudes) {

        return inventarioService.dispensarLote(solicitudes);
    }

    @PostMapping("/reposiciones")
    public StockSucursal reponer(
            @RequestBody SolicitudStock datos) {

        return inventarioService.reponer(datos);
    }
}
