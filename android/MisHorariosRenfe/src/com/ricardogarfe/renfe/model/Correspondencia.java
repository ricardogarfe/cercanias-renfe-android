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
 * {@link Correspondencia} represents values from {@link EstacionCercanias}
 * 
 * @author ricardo
 */
public class Correspondencia implements Parcelable {

    private String tipo;
    private String descripcion;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static final Parcelable.Creator<Correspondencia> CREATOR = new Creator<Correspondencia>() {

        public Correspondencia[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Correspondencia[size];
        }

        public Correspondencia createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Correspondencia(source);
        }
    };

    /**
     * Constructor that takes a Parcel and gives you an object populated with
     * it's values
     * 
     * @param source
     *            Parcel values to compose object.
     */
    private Correspondencia(Parcel source) {
        // TODO Auto-generated constructor stub
        tipo = source.readString();
        descripcion = source.readString();
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
        dest.writeString(tipo);
        dest.writeString(descripcion);
    }

}
