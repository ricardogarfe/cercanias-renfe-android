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
    private ValidezHorarioCercanias validez;
    private DatosPeticionHorarioCercanias peticion;
    private List<HorarioCercanias> horarioTypeList;

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

    public ValidezHorarioCercanias getValidez() {
        return validez;
    }

    public void setValidez(ValidezHorarioCercanias validez) {
        this.validez = validez;
    }

    public DatosPeticionHorarioCercanias getPeticion() {
        return peticion;
    }

    public void setPeticion(DatosPeticionHorarioCercanias peticion) {
        this.peticion = peticion;
    }

    public List<HorarioCercanias> getHorarioTypeList() {
        return horarioTypeList;
    }

    public void setHorarioTypeList(List<HorarioCercanias> horarioTypeList) {
        this.horarioTypeList = horarioTypeList;
    }

}
