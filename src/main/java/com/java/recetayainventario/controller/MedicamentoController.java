
package com.java.recetayainventario.controller;

import com.java.recetayainventario.model.Medicamento;
import com.java.recetayainventario.service.MedicamentoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @PostMapping
    public ResponseEntity<Medicamento> guardar(
            @RequestBody Medicamento medicamento) {

        Medicamento nuevoMedicamento = medicamentoService.guardar(medicamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMedicamento);
    }

    @GetMapping
    public List<Medicamento> obtenerTodos() {
        return medicamentoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscaPorId(@PathVariable Long id) {

        return medicamentoService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> actualizar(
            @PathVariable Long id,
            @RequestBody Medicamento medicamento) {

        return medicamentoService.actualizar(id, medicamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (medicamentoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
