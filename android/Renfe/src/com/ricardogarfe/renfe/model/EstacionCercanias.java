/**
 * 
 */
package com.ricardogarfe.renfe.model;

import java.util.List;

import com.google.android.maps.GeoPoint;

/**
 * 
 * {@link EstacionCercanias} represents cercanias station data.
 * 
 * @author ricardo
 */
public class EstacionCercanias {

    private Integer codigo;
    private String descripcion;
    private GeoPoint geoPoint;
    private Integer zona;
    private List<Servicio> servicioList;

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

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public List<Servicio> getServicioList() {
        return servicioList;
    }

    public void setServicioList(List<Servicio> servicioList) {
        this.servicioList = servicioList;
    }

}
