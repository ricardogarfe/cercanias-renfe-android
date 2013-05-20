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

/**
 * 
 * {@link DatosPeticionHorarioCercanias} represents travel data between two
 * {@link EstacionCercanias}.
 * 
 * @author ricardo
 */
public class DatosPeticionHorarioCercanias {

    private String origen;
    private String destino;
    private String fechaViaje;
    private String nucleo;
    private String nucleoName;
    private Intervalo intervalo;
    private String estacionOrigenName;
    private String estacionDestinoName;
    private String horaInicio;
    private String horaFinal;

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

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public Intervalo getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Intervalo intervalo) {
        this.intervalo = intervalo;
    }

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public String getEstacionOrigenName() {
        return estacionOrigenName;
    }

    public void setEstacionOrigenName(String estacionOrigenName) {
        this.estacionOrigenName = estacionOrigenName;
    }

    public String getEstacionDestinoName() {
        return estacionDestinoName;
    }

    public void setEstacionDestinoName(String estacionDestinoName) {
        this.estacionDestinoName = estacionDestinoName;
    }

    public String getNucleoName() {
        return nucleoName;
    }

    public void setNucleoName(String nucleoName) {
        this.nucleoName = nucleoName;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

}
