/**
 * 
 */
package com.ricardogarfe.renfe.model;

import android.location.Geocoder;

/**
 * 
 * {@link NucleoCercanias} represents data from cercanias city.
 * 
 * @author ricardo
 */
public class NucleoCercanias {

    private String codigo;
    private String descripcion;
    private Geocoder geoCoder;
    private String iconoMapa;
    private String tarifas;
    private String incidencias;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIconoMapa() {
        return iconoMapa;
    }

    public void setIconoMapa(String iconoMapa) {
        this.iconoMapa = iconoMapa;
    }

    public String getTarifas() {
        return tarifas;
    }

    public void setTarifas(String tarifas) {
        this.tarifas = tarifas;
    }

    public String getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(String incidencias) {
        this.incidencias = incidencias;
    }

    public Geocoder getGeoCoder() {
        return geoCoder;
    }

    public void setGeoCoder(Geocoder geoCoder) {
        this.geoCoder = geoCoder;
    }

}
