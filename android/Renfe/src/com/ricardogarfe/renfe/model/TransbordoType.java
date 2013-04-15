/**
 * 
 */
package com.ricardogarfe.renfe.model;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author ricardo
 * 
 */
public class TransbordoType {

    private String enlace;
    private XMLGregorianCalendar horaLlegada;
    private XMLGregorianCalendar horaSalida;
    private String linea;

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public XMLGregorianCalendar getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(XMLGregorianCalendar horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public XMLGregorianCalendar getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(XMLGregorianCalendar horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

}
