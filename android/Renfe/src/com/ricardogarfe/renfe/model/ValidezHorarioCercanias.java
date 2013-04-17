/**
 * 
 */
package com.ricardogarfe.renfe.model;

/**
 * 
 * {@link ValidezHorarioCercanias} Represents untill which time is valid
 * retrieved {@link HorarioCercanias}.
 * 
 * @author ricardo
 */
public class ValidezHorarioCercanias {

    private String desde;
    private String hasta;

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

}
