package com.ricardogarfe.renfe.services.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ricardogarfe.renfe.model.HorarioType;

public class HorariosCercaniasHandler extends DefaultHandler {

    private List<HorarioType> horarioTypeList = new ArrayList<HorarioType>();
    private HorarioType horarioType;
    private String temp;

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
            horarioType = new HorarioType();
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("Horario")) {
            // add it to the list
            horarioTypeList.add(horarioType);

        } else if (qName.equalsIgnoreCase("Linea")) {
            horarioType.setLinea(temp);
        } else if (qName.equalsIgnoreCase("CodCivis")) {
            horarioType.setCodCivis(temp);
        } else if (qName.equalsIgnoreCase("HoraSalida")) {
            horarioType.setHoraSalida(temp);
        } else if (qName.equalsIgnoreCase("HoraLlegada")) {
            horarioType.setHoraLlegada(temp);
        } else if (qName.equalsIgnoreCase("Duracion")) {
            horarioType.setDuracion(temp);
        }

    }

    public List<HorarioType> getHorarioTypeList() {
        return horarioTypeList;
    }

}
