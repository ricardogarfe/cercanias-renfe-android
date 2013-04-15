/**
 * 
 */
package com.ricardogarfe.renfe.model;

import java.util.List;

/**
 * @author ricardo
 * 
 */
public class PeticionHorarios {

    private String error;
    private String fecha;
    private ValidezType validez;
    private DatosPeticionType peticion;
    private List<HorarioType> horarioTypeList;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ValidezType getValidez() {
        return validez;
    }

    public void setValidez(ValidezType validez) {
        this.validez = validez;
    }

    public DatosPeticionType getPeticion() {
        return peticion;
    }

    public void setPeticion(DatosPeticionType peticion) {
        this.peticion = peticion;
    }

    public List<HorarioType> getHorarioTypeList() {
        return horarioTypeList;
    }

    public void setHorarioTypeList(List<HorarioType> horarioTypeList) {
        this.horarioTypeList = horarioTypeList;
    }

}
