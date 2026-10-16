package com.java.recetayainventario.repository;

import com.java.recetayainventario.model.StockSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockSucursalRepository extends JpaRepository<StockSucursal, Long> {

    List<StockSucursal> findBySucursal_Id(Long sucursalId);

    Optional<StockSucursal> findByMedicamento_IdAndSucursal_Id(
            Long medicamentoId, Long sucursalId);

    boolean existsByMedicamento_Id(Long medicamentoId);

    boolean existsBySucursal_Id(Long sucursalId);
}
