package com.ricardogarfe.renfe.services.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ricardogarfe.renfe.model.HorarioCercanias;
import com.ricardogarfe.renfe.model.TransbordoCercanias;

public class HorariosCercaniasHandler extends DefaultHandler {

    private List<HorarioCercanias> horarioTypeList = new ArrayList<HorarioCercanias>();
    private HorarioCercanias horarioCercanias;
    private TransbordoCercanias transbordoCercanias;

    private String temp;

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
            horarioTypeList.add(horarioCercanias);

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

    public List<HorarioCercanias> getHorarioTypeList() {
        return horarioTypeList;
    }

}
