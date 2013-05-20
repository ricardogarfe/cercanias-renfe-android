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

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * {@link LineaCercanias} represents Linea and related {@link EstacionCercanias}
 * 
 * @author ricardo
 */
public class LineaCercanias implements Parcelable {

    private String codigo;
    private String rgb;
    private String descripcion;
    private String origen;
    private String destino;
    private List<EstacionCercanias> estacionCercaniasList;

    public LineaCercanias() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<EstacionCercanias> getEstacionCercaniasList() {
        return estacionCercaniasList;
    }

    public void setEstacionCercaniasList(
            List<EstacionCercanias> estacionCercaniasList) {
        this.estacionCercaniasList = estacionCercaniasList;
    }

    public static final Parcelable.Creator<LineaCercanias> CREATOR = new Creator<LineaCercanias>() {

        public LineaCercanias[] newArray(int size) {
            // TODO Auto-generated method stub
            return new LineaCercanias[size];
        }

        public LineaCercanias createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new LineaCercanias(source);
        }
    };

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
        dest.writeString(codigo);
        dest.writeString(rgb);
        dest.writeString(descripcion);
        dest.writeString(origen);
        dest.writeString(destino);
        dest.writeList(estacionCercaniasList);
    }

    /**
     * Constructor that takes a Parcel and gives you an object populated with
     * it's values
     * 
     * @param source
     *            Parcel values to compose object.
     */
    private LineaCercanias(Parcel source) {
        codigo = source.readString();
        rgb = source.readString();
        descripcion = source.readString();
        origen = source.readString();
        destino = source.readString();

        estacionCercaniasList = new ArrayList<EstacionCercanias>();
        source.readTypedList(estacionCercaniasList, EstacionCercanias.CREATOR);
    }

}
