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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * {@link HorarioCercanias} represents schedule from a trip between two
 * {@link EstacionCercanias}
 * 
 * @author ricardo
 */
public class HorarioCercanias {

    private String linea;
    private String horaSalida;
    private List<TransbordoCercanias> transbordo = new ArrayList<TransbordoCercanias>();
    private String horaLlegada;
    private String duracion;
    private String codCivis;

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public List<TransbordoCercanias> getTransbordo() {
        return transbordo;
    }

    public void setTransbordo(List<TransbordoCercanias> transbordo) {
        this.transbordo = transbordo;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCodCivis() {
        return codCivis;
    }

    public void setCodCivis(String codCivis) {
        this.codCivis = codCivis;
    }

}
