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

package com.ricardogarfe.renfe.services.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ricardogarfe.renfe.model.HorarioCercanias;
import com.ricardogarfe.renfe.model.TransbordoCercanias;

/**
 * @author ricardo
 * 
 */
public class HorariosCercaniasHandler extends DefaultHandler {

    private List<HorarioCercanias> horarioCercaniasList = new ArrayList<HorarioCercanias>();
    private HorarioCercanias horarioCercanias;
    private TransbordoCercanias transbordoCercanias;

    private String temp;

    public static final int TASK_COMPLETE = 1;
    public static final int TASK_ERROR = 2;

    private boolean inHorario = false;
    private boolean inLinea = false;
    private boolean inHoraSalida = false;
    private boolean inTransbordo = false;
    private boolean inHoraLlegada = false;
    private boolean inDuracion = false;
    private boolean inCodCivis = false;

    /*
     * When the parser encounters plain text (not XML elements), it calls(this
     * method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }

    /*
     * Every time the parser encounters the beginning of a new element, it calls
     * this method, which resets the string buffer
     */
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        temp = "";

        if (qName.equalsIgnoreCase("Horario")) {
            horarioCercanias = new HorarioCercanias();
            inHorario = true;
        } else if (qName.equalsIgnoreCase("Transbordo")) {
            transbordoCercanias = new TransbordoCercanias();
            inTransbordo = true;
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("Horario")) {
            // add horario to the list
            inHorario = false;
            horarioCercaniasList.add(horarioCercanias);

        } else if (qName.equalsIgnoreCase("Linea")) {
            if (inTransbordo) {
                transbordoCercanias.setLinea(temp);
            } else if (inHorario) {
                horarioCercanias.setLinea(temp);
            }

        } else if (qName.equalsIgnoreCase("CodCivis")) {
            if (inTransbordo) {
                transbordoCercanias.setCodCivis(temp);
            } else if (inHorario) {
                horarioCercanias.setCodCivis(temp);
            }

        } else if (qName.equalsIgnoreCase("HoraSalida")) {
            if (inTransbordo) {
                transbordoCercanias.setHoraSalida(temp);
            } else if (inHorario) {
                horarioCercanias.setHoraSalida(temp);
            }

        } else if (qName.equalsIgnoreCase("HoraLlegada")) {
            if (inTransbordo) {
                transbordoCercanias.setHoraLlegada(temp);
            } else if (inHorario) {
                horarioCercanias.setHoraLlegada(temp);
            }
        } else if (qName.equalsIgnoreCase("Enlace")) {
            transbordoCercanias.setEnlace(temp);
        } else if (qName.equalsIgnoreCase("Duracion")) {
            horarioCercanias.setDuracion(temp);
        } else if (qName.equalsIgnoreCase("Transbordo")) {
            // add transbordo to the list
            inTransbordo = false;
            horarioCercanias.getTransbordo().add(transbordoCercanias);
        }

    }

    public List<HorarioCercanias> getHorarioCercaniasList() {
        return horarioCercaniasList;
    }

}
