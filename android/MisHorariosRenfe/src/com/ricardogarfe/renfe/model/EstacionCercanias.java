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

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * {@link EstacionCercanias} represents cercanias station data.
 * 
 * @author ricardo
 */
public class EstacionCercanias implements Parcelable {

    private Integer codigo;
    private String descripcion;
    private Double latitude;
    private Double longitude;
    private String zona;
    private List<Servicio> servicioList;

    // Values from Linea
    private String viaSentidoDestino;
    private String viaSentidoOrigen;

    private List<Correspondencia> correspondenciaList;

    public EstacionCercanias() {
        super();
        // TODO Auto-generated constructor stub
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public List<Servicio> getServicioList() {
        return servicioList;
    }

    public void setServicioList(List<Servicio> servicioList) {
        this.servicioList = servicioList;
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

    public String getViaSentidoDestino() {
        return viaSentidoDestino;
    }

    public void setViaSentidoDestino(String viaSentidoDestino) {
        this.viaSentidoDestino = viaSentidoDestino;
    }

    public String getViaSentidoOrigen() {
        return viaSentidoOrigen;
    }

    public void setViaSentidoOrigen(String viaSentidoOrigen) {
        this.viaSentidoOrigen = viaSentidoOrigen;
    }

    public List<Correspondencia> getCorrespondenciaList() {
        return correspondenciaList;
    }

    public void setCorrespondenciaList(List<Correspondencia> correspondenciaList) {
        this.correspondenciaList = correspondenciaList;
    }

    public static final Parcelable.Creator<EstacionCercanias> CREATOR = new Creator<EstacionCercanias>() {

        public EstacionCercanias[] newArray(int size) {
            // TODO Auto-generated method stub
            return new EstacionCercanias[size];
        }

        public EstacionCercanias createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new EstacionCercanias(source);
        }
    };

    /**
     * Constructor that takes a Parcel and gives you an object populated with
     * it's values
     * 
     * @param source
     *            Parcel values to compose object.
     */
    private EstacionCercanias(Parcel source) {

        codigo = source.readInt();
        descripcion = source.readString();
        latitude = source.readDouble();
        longitude = source.readDouble();
        viaSentidoDestino = source.readString();
        viaSentidoOrigen = source.readString();
//        source.readList(servicioList, null);
//        source.readList(correspondenciaList, null);
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
        dest.writeString(zona);
        dest.writeString(viaSentidoDestino);
        dest.writeString(viaSentidoOrigen);
//        dest.writeList(servicioList);
//        dest.writeList(correspondenciaList);

    }
}
