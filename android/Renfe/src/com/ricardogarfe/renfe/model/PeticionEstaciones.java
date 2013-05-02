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
 * {@link PeticionEstaciones} represents each {@link EstacionCercanias} per
 * {@link NucleoCercanias}.
 * 
 * @author ricardo
 */
public class PeticionEstaciones {

    private String nucleo;
    private List<EstacionCercanias> estacion;

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public List<EstacionCercanias> getEstacion() {
        return estacion;
    }

    public void setEstacion(List<EstacionCercanias> estacion) {
        this.estacion = estacion;
    }

}
