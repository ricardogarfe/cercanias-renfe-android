/**
 * 
 */
package com.ricardogarfe.renfe.model;

import java.util.List;

/**
 * {@link PeticionEstaciones} represents each {@link EstacionCercanias} per
 * {@link NucleoCercanias}.
 * 
 * @author ricardo
 */
public class PeticionEstaciones {

    private String nucleo;
    private List<EstacionCercanias> estacion;

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public List<EstacionCercanias> getEstacion() {
        return estacion;
    }

    public void setEstacion(List<EstacionCercanias> estacion) {
        this.estacion = estacion;
    }

}
