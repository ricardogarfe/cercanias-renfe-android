/**
 * 
 */
package com.ricardogarfe.renfe.model;

import java.util.List;

/**
 * 
 * {@link PeticionHorarios} represents whole data from {@link HorarioCercanias},
 * {@link ValidezHorarioCercanias} and {@link DatosPeticionHorarioCercanias}.
 * 
 * @author ricardo
 */
public class PeticionHorarios {

    private String error;
    private String fecha;
    private ValidezHorarioCercanias validez;
    private DatosPeticionHorarioCercanias peticion;
    private List<HorarioCercanias> horarioCercaniasList;

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

    public List<HorarioCercanias> getHorarioCercaniasList() {
        return horarioCercaniasList;
    }

    public void setHorarioCercaniasList(
            List<HorarioCercanias> horarioCercaniasList) {
        this.horarioCercaniasList = horarioCercaniasList;
    }

}
