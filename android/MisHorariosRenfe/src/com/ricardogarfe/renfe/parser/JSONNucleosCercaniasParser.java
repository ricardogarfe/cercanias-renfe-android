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

package com.ricardogarfe.renfe.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ricardogarfe.renfe.model.NucleoCercanias;

/**
 * 
 * JSON Parser for {@link NucleoCercanias} Objects.
 * 
 * @author ricardo
 * 
 */
public class JSONNucleosCercaniasParser extends JSONCercaniasParser {

    public static String NUCLEOS_JSON = "json/nucleos_cercanias.json";

    private JSONObject mJSONObjectNucleos;

    /**
     * Retrieve {@link NucleoCercanias} from JSON data from file or URL
     * depending on fromFile parameter.
     * 
     * @param filePath
     *            File Path to retrive data.
     * @param fromFile
     *            boolean to check if data is retrieved from file or URL.
     * @return {@link List} of {@link NucleoCercanias}.
     * @throws IOException
     * @throws JSONException
     */
    public List<NucleoCercanias> retrieveNucleoCercaniasFromJSON(
            String filePath, boolean fromFile) throws IOException,
            JSONException {

        List<NucleoCercanias> nucleoCercaniasList = new ArrayList<NucleoCercanias>();

        mJSONObjectNucleos = getJSONFromFile(filePath);

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
        nucleoCercanias.setLatitude(jsonObject.getDouble("Lat"));
        nucleoCercanias.setLongitude(jsonObject.getDouble("Lon"));
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
