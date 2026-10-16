
package com.java.recetayainventario.controller;

import com.java.recetayainventario.model.Sucursal;
import com.java.recetayainventario.service.SucursalService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @PostMapping
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal) {

        Sucursal nuevaSucursal = sucursalService.guardar(sucursal);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
    }

    @GetMapping
    public List<Sucursal> obtenerTodos() {
        return sucursalService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscaPorId(@PathVariable Long id) {

        return sucursalService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(
            @PathVariable Long id,
            @RequestBody Sucursal sucursal) {

        return sucursalService.actualizar(id, sucursal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (sucursalService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
