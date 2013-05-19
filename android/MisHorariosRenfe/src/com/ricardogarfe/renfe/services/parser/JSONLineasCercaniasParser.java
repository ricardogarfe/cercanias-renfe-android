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

package com.ricardogarfe.renfe.services.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.LineaCercanias;

/**
 * 
 * JSON Parser for {@link LineaCercanias} Objects.
 * 
 * @author ricardo
 * 
 */
public class JSONLineasCercaniasParser extends JSONCercaniasParser {

    private JSONObject mJSONObjectLineasCercanias;

    private JSONEstacionCercaniasParser mJsonEstacionCercaniasParser;

    private final String TAG = getClass().getSimpleName();

    /**
     * Retrieve {@link LineaCercanias} from JSON data from file or URL depending
     * on fromFile parameter.
     * 
     * @param filePath
     *            File Path or URL to retrieve data.
     * @param fromFile
     *            boolean to check if data is retrieved from file or URL.
     * @return {@link List} of {@link LineaCercanias}.
     * @throws IOException
     * @throws JSONException
     */
    public List<LineaCercanias> retrieveLineasCercaniasFromJSON(
            String filePath, boolean fromFile) throws IOException,
            JSONException {

        if (fromFile) {
            setmJSONObjectLineasCercanias(getJSONFromFile(filePath));
        } else {
            setmJSONObjectLineasCercanias(getJSONFromUrl(filePath));
        }

        JSONArray jsonArray = (getmJSONObjectLineasCercanias()
                .getJSONObject("Lineas")).getJSONArray("Linea");

        List<LineaCercanias> lineaCercaniasList = retrieveLineaCercaniasFromJSONArray(jsonArray);

        return lineaCercaniasList;

    }

    /**
     * Retrieve {@link LineaCercanias} from {@link JSONArray}.
     * 
     * @param jsonArray
     *            Containing {@link LineaCercanias} to parse.
     * @return {@link LineaCercanias} list parsed.
     * @throws JSONException
     */
    public List<LineaCercanias> retrieveLineaCercaniasFromJSONArray(
            JSONArray jsonArray) throws JSONException {
        List<LineaCercanias> lineaCercaniasList = new ArrayList<LineaCercanias>();

        LineaCercanias lineaCercanias;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectEstacionCercanias = jsonArray.getJSONObject(i);
            lineaCercanias = retrieveLineaCercanias(jsonObjectEstacionCercanias);
            lineaCercaniasList.add(lineaCercanias);
        }
        return lineaCercaniasList;
    }

    /**
     * Retrieve {@link LineaCercanias} from {@link JSONObject} data.
     * 
     * @param jsonObject
     *            Object to convert.
     * @return {@link EstacionCercanias} object.
     * @throws JSONException
     */
    public LineaCercanias retrieveLineaCercanias(JSONObject jsonObject)
            throws JSONException {

        LineaCercanias lineaCercanias = new LineaCercanias();

        lineaCercanias.setCodigo(jsonObject.getString("Codigo"));
        lineaCercanias.setDescripcion(jsonObject.getString("Descripcion"));

        lineaCercanias.setRgb(jsonObject.getString("rgb"));
        lineaCercanias.setOrigen(jsonObject.getString("Origen"));
        lineaCercanias.setDestino(jsonObject.getString("Destino"));

        // TODO: Obtener estaciones asociadas a la línea.
        JSONObject estacionesJsonObject = jsonObject
                .getJSONObject("Estaciones");

        if (estacionesJsonObject != null) {

            List<EstacionCercanias> estacionCercaniasList = mJsonEstacionCercaniasParser
                    .retrieveLineasEstacionCercaniasFromJSONArray(estacionesJsonObject
                            .getJSONArray("Estacion"));
            lineaCercanias.setEstacionCercaniasList(estacionCercaniasList);
        }

        return lineaCercanias;
    }

    public JSONObject getmJSONObjectLineasCercanias() {
        return mJSONObjectLineasCercanias;
    }

    public void setmJSONObjectLineasCercanias(
            JSONObject mJSONObjectLineasCercanias) {
        this.mJSONObjectLineasCercanias = mJSONObjectLineasCercanias;
    }

    public JSONEstacionCercaniasParser getmJsonEstacionCercaniasParser() {
        return mJsonEstacionCercaniasParser;
    }

    public void setmJsonEstacionCercaniasParser(
            JSONEstacionCercaniasParser mJsonEstacionCercaniasParser) {
        this.mJsonEstacionCercaniasParser = mJsonEstacionCercaniasParser;
    }

}
