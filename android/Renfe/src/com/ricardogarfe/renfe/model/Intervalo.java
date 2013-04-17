/**
 * 
 */
package com.ricardogarfe.renfe.model;

/**
 * 
 * {@link Intervalo} represents validation time for {@link HorarioCercanias}
 * retrieved.
 * 
 * @author ricardo
 */
public class Intervalo {

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
