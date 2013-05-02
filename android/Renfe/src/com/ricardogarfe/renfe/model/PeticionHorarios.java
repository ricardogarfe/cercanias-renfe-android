/*
 * Copyright [2013] [Ricardo García Fernández] [ricarodgarfe@gmail.com]
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

/**
 * 
 * {@link PeticionHorarios} represents whole data from {@link HorarioCercanias},
 * {@link ValidezHorarioCercanias} and {@link DatosPeticionHorarioCercanias}.
 * 
 * @author ricardo
 */
public class PeticionHorarios {

    private String error;
    private String fecha;
    private ValidezHorarioCercanias validez;
    private DatosPeticionHorarioCercanias peticion;
    private List<HorarioCercanias> horarioCercaniasList;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ValidezHorarioCercanias getValidez() {
        return validez;
    }

    public void setValidez(ValidezHorarioCercanias validez) {
        this.validez = validez;
    }

    public DatosPeticionHorarioCercanias getPeticion() {
        return peticion;
    }

    public void setPeticion(DatosPeticionHorarioCercanias peticion) {
        this.peticion = peticion;
    }

    public List<HorarioCercanias> getHorarioCercaniasList() {
        return horarioCercaniasList;
    }

    public void setHorarioCercaniasList(
            List<HorarioCercanias> horarioCercaniasList) {
        this.horarioCercaniasList = horarioCercaniasList;
    }

}
