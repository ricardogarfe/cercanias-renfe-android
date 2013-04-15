/**
 * 
 */
package com.ricardogarfe.renfe.model;

import java.util.List;

/**
 * @author ricardo
 * 
 */
public class PeticionEstaciones {

    private String nucleo;
    private List<EstacionType> estacion;

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public List<EstacionType> getEstacion() {
        return estacion;
    }

    public void setEstacion(List<EstacionType> estacion) {
        this.estacion = estacion;
    }

}
