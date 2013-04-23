/**
 * 
 */
package com.ricardogarfe.renfe.services.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.ricardogarfe.renfe.model.NucleoCercanias;

/**
 * 
 * JSON Parser for {@link NucleoCercanias} Objects.
 * 
 * @author ricardo
 * 
 */
public class JSONNucleosCercaniasParser extends JSONCercaniasParser {

    private JSONObject mJSONObjectNucleos;

    public List<NucleoCercanias> retrieveNucleoCercaniasFromJSON(String file)
            throws IOException, JSONException {

        List<NucleoCercanias> nucleoCercaniasList = new ArrayList<NucleoCercanias>();

        mJSONObjectNucleos = getJSONFromFile(file);

        JSONArray jsonArray = (mJSONObjectNucleos.getJSONObject("Nucleos"))
                .getJSONArray("Nucleo");

        NucleoCercanias nucleoCercanias;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectNucleo = jsonArray.getJSONObject(i);
            nucleoCercanias = retrieveNucleoCercanias(jsonObjectNucleo);
            nucleoCercaniasList.add(nucleoCercanias);
        }

        return nucleoCercaniasList;

    }

    /**
     * Retrieve {@link NucleoCercanias} from {@link JSONObject} data.
     * 
     * @param jsonObject
     *            Object to convert.
     * @return NucleoCercanias object.
     * @throws JSONException
     */
    public NucleoCercanias retrieveNucleoCercanias(JSONObject jsonObject)
            throws JSONException {

        NucleoCercanias nucleoCercanias = new NucleoCercanias();

        nucleoCercanias.setCodigo(jsonObject.getInt("Codigo"));
        nucleoCercanias.setDescripcion(jsonObject.getString("Descripcion"));
        GeoPoint geoPoint = retrieveGeoPoint(jsonObject.getDouble("Lat"),
                jsonObject.getDouble("Lon"));
        nucleoCercanias.setGeoPoint(geoPoint);
        nucleoCercanias.setIconoMapa(jsonObject.getString("IconoMapa"));
        nucleoCercanias.setTarifas(jsonObject.getString("Tarifas"));
        nucleoCercanias.setIncidencias(jsonObject.getString("Incidencias"));
        nucleoCercanias.setEstacionesJSON(jsonObject
                .getString("estacionesJSON"));
        nucleoCercanias.setEstacionesXML(jsonObject.getString("estacionesXML"));
        nucleoCercanias.setLineasJSON(jsonObject.getString("lineasJSON"));
        nucleoCercanias.setLineasXML(jsonObject.getString("lineasXML"));

        return nucleoCercanias;
    }

    /**
     * Find Nucleo Cercanías by Id Nucleo.
     * 
     * TODO:
     * 
     * @param idNucleo
     *            to find.
     * @return Nucleo object.
     */
    public NucleoCercanias findNucleoCercaniasByIdNucleo(String idNucleo) {

        return null;
    }

    public JSONObject getMJSONObjectNucleos() {
        return mJSONObjectNucleos;
    }

    public void setMJSONObjectNucleos(JSONObject mJSONObjectNucleos) {
        this.mJSONObjectNucleos = mJSONObjectNucleos;
    }
}
