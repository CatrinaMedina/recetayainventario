
package com.java.recetayainventario.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "stock_sucursal",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"medicamento_id", "sucursal_id"}
    )
)
public class StockSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    private Integer cantidadDisponible;
    private Integer cantidadReservada;

    public StockSucursal() {
    }

    public StockSucursal(Medicamento medicamento,
                         Sucursal sucursal,
                         Integer cantidadDisponible) {
        this.medicamento = medicamento;
        this.sucursal = sucursal;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadReservada = 0;
    }

    public Long getId() {
        return this.id;
    }

    public Medicamento getMedicamento() {
        return this.medicamento;
    }

    public Sucursal getSucursal() {
        return this.sucursal;
    }

    public Integer getCantidadDisponible() {
        return this.cantidadDisponible;
    }

    public Integer getCantidadReservada() {
        return this.cantidadReservada;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setCantidadReservada(Integer cantidadReservada) {
        this.cantidadReservada = cantidadReservada;
    }
}
