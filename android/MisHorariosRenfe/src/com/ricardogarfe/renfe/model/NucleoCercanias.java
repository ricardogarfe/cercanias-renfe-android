/*
 * Copyright [2013] [Ricardo García Fernández] [ricardogarfe@gmail.com]
 * 
 * This file is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This file is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ricardogarfe.renfe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * {@link NucleoCercanias} represents data from cercanias city.
 * 
 * @author ricardo
 */
public class NucleoCercanias implements Parcelable {

    private Integer codigo;
    private String descripcion;
    private Double latitude;
    private Double longitude;
    private String iconoMapa;
    private String tarifas;
    private String incidencias;
    private String estacionesJSON;
    private String estacionesXML;
    private String lineasJSON;
    private String lineasXML;

    public NucleoCercanias() {
        // TODO Auto-generated constructor stub
        super();
    }

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static final Parcelable.Creator<NucleoCercanias> CREATOR = new Creator<NucleoCercanias>() {

        public NucleoCercanias[] newArray(int size) {
            // TODO Auto-generated method stub
            return new NucleoCercanias[size];
        }

        public NucleoCercanias createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new NucleoCercanias(source);
        }
    };

    /**
     * Constructor that takes a Parcel and gives you an object populated with
     * it's values
     * 
     * @param source
     *            Parcel values to compose object.
     */
    private NucleoCercanias(Parcel source) {
        // TODO Auto-generated constructor stub
        codigo = source.readInt();
        descripcion = source.readString();
        latitude = source.readDouble();
        longitude = source.readDouble();
        iconoMapa = source.readString();
        tarifas = source.readString();
        incidencias = source.readString();
        estacionesJSON = source.readString();
        estacionesXML = source.readString();
        lineasJSON = source.readString();
        lineasXML = source.readString();
    }

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Write values from object to Parcel dest.
     * </p>
     */
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeInt(codigo);
        dest.writeString(descripcion);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(iconoMapa);
        dest.writeString(tarifas);
        dest.writeString(incidencias);
        dest.writeString(estacionesJSON);
        dest.writeString(estacionesXML);
        dest.writeString(lineasJSON);
        dest.writeString(lineasXML);
    }

}
