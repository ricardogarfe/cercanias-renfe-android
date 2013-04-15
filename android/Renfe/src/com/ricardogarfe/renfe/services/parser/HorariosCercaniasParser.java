package com.ricardogarfe.renfe.services.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ricardogarfe.renfe.model.HorarioType;

public class HorariosCercaniasParser {

    // We don't use namespaces
    private static final String ns = null;

    public List<HorarioType> parse(InputStream in)
            throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readHorarioRequest(parser);
        } finally {
            in.close();
        }
    }

    private List<HorarioType> readHorarioRequest(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<HorarioType> horarioTypeList = new ArrayList<HorarioType>();

        parser.require(XmlPullParser.START_TAG, ns, "Horarios");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Horario")) {
                horarioTypeList.add(readHorarioEntry(parser));
            } else {
                skip(parser);
            }
        }
        return horarioTypeList;
    }

    private HorarioType readHorarioEntry(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        /*
         * <Transbordo> <Enlace>65000</Enlace>
         * 
         * <HoraLlegada>14:42:00</HoraLlegada>
         * 
         * <HoraSalida>14:53:00</HoraSalida>
         * 
         * <Linea>C2 </Linea> <CodCivis> </CodCivis> </Transbordo>
         */
        parser.require(XmlPullParser.START_TAG, ns, "Horario");

        HorarioType horarioType = new HorarioType();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tag = parser.getName();
            if (tag.equals("Linea")) {
                horarioType.setLinea(readTitle(parser, "Linea"));
            } else if (tag.equals("CodCivis")) {
                horarioType.setCodCivis(readTitle(parser, "CodCivis"));
            } else if (tag.equals("HoraSalida")) {
                horarioType.setHoraSalida(readTitle(parser, "HoraSalida"));
            } else if (tag.equals("Transbordo")) {
                // TODO:
            } else if (tag.equals("HoraLlegada")) {
                horarioType.setHoraLlegada(readTitle(parser, "HoraLlegada"));
            } else if (tag.equals("Duracion")) {
                horarioType.setDuracion(readTitle(parser, "Duracion"));
            } else {
                skip(parser);
            }
        }
        return horarioType;
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser, String tagName)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tagName);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tagName);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException,
            IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
    }

}
