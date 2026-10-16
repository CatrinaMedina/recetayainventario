
package com.java.recetayainventario.model;

public class SolicitudStock {

    private Long medicamentoId;
    private Long sucursalId;
    private Integer cantidad;

    public SolicitudStock() {
    }

    public SolicitudStock(Long medicamentoId,
                          Long sucursalId,
                          Integer cantidad) {
        this.medicamentoId = medicamentoId;
        this.sucursalId = sucursalId;
        this.cantidad = cantidad;
    }

    public Long getMedicamentoId() {
        return this.medicamentoId;
    }

    public Long getSucursalId() {
        return this.sucursalId;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
