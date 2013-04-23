/**
 * 
 */
package com.ricardogarfe.renfe.model;

import com.google.android.maps.GeoPoint;

/**
 * 
 * {@link NucleoCercanias} represents data from cercanias city.
 * 
 * @author ricardo
 */
public class NucleoCercanias {

    private Integer codigo;
    private String descripcion;
    private GeoPoint geoPoint;
    private String iconoMapa;
    private String tarifas;
    private String incidencias;
    private String estacionesJSON;
    private String estacionesXML;
    private String lineasJSON;
    private String lineasXML;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getEstacionesJSON() {
        return estacionesJSON;
    }

    public void setEstacionesJSON(String estacionesJSON) {
        this.estacionesJSON = estacionesJSON;
    }

    public String getEstacionesXML() {
        return estacionesXML;
    }

    public void setEstacionesXML(String estacionesXML) {
        this.estacionesXML = estacionesXML;
    }

    public String getLineasJSON() {
        return lineasJSON;
    }

    public void setLineasJSON(String lineasJSON) {
        this.lineasJSON = lineasJSON;
    }

    public String getLineasXML() {
        return lineasXML;
    }

    public void setLineasXML(String lineasXML) {
        this.lineasXML = lineasXML;
    }

}
