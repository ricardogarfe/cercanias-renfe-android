/**
 * 
 */
package com.ricardogarfe.renfe.model;

/**
 * 
 * {@link TransbordoCercanias} represents a change to another Cercanias Line in
 * the same trip.
 * 
 * @author ricardo
 */
public class TransbordoCercanias {

    private String enlace;
    private String horaLlegada;
    private String horaSalida;
    private String linea;
    private String codCivis;

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getCodCivis() {
        return codCivis;
    }

    public void setCodCivis(String codCivis) {
        this.codCivis = codCivis;
    }

}
