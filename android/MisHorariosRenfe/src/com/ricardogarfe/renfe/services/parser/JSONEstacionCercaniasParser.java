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

import com.ricardogarfe.renfe.model.Correspondencia;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.LineaCercanias;
import com.ricardogarfe.renfe.model.Servicio;

/**
 * 
 * JSON Parser for {@link EstacionCercaniass} Objects.
 * 
 * @author ricardo
 * 
 */
public class JSONEstacionCercaniasParser extends JSONCercaniasParser {

    private JSONObject mJSONObjectEstacionCercanias;

    private final String TAG = getClass().getSimpleName();

    /**
     * Retrieve {@link EstacionCercanias} from JSON data from file or URL
     * depending on fromFile parameter.
     * 
     * @param filePath
     *            File Path or URL to retrieve data.
     * @param fromFile
     *            boolean to check if data is retrieved from file or URL.
     * @return {@link List} of {@link EstacionCercanias}.
     * @throws IOException
     * @throws JSONException
     */
    public List<EstacionCercanias> retrieveEstacionCercaniasFromJSON(
            String filePath, boolean fromFile) throws IOException,
            JSONException {

        if (fromFile) {
            setmJSONObjectEstacionCercanias(getJSONFromFile(filePath));
        } else {
            setmJSONObjectEstacionCercanias(getJSONFromUrl(filePath));
        }

        JSONArray jsonArray = (getmJSONObjectEstacionCercanias()
                .getJSONObject("Estaciones")).getJSONArray("Estacion");

        List<EstacionCercanias> estacionCercaniasList = retrieveEstacionCercaniasFromJSONArray(jsonArray);

        return estacionCercaniasList;

    }

    /**
     * Retrieve {@link EstacionCercanias} from {@link JSONArray}.
     * 
     * @param jsonArray
     *            Containing {@link EstacionCercanias} to parse.
     * @return {@link EstacionCercanias} list parsed.
     * @throws JSONException
     */
    public List<EstacionCercanias> retrieveEstacionCercaniasFromJSONArray(
            JSONArray jsonArray) throws JSONException {
        List<EstacionCercanias> estacionCercaniasList = new ArrayList<EstacionCercanias>();

        EstacionCercanias estacionCercanias;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectEstacionCercanias = jsonArray.getJSONObject(i);
            estacionCercanias = retrieveEstacionCercanias(jsonObjectEstacionCercanias);
            estacionCercaniasList.add(estacionCercanias);
        }
        return estacionCercaniasList;
    }

    /**
     * Retrieve {@link EstacionCercanias} from {@link JSONObject} data.
     * 
     * @param jsonObject
     *            Object to convert.
     * @return {@link EstacionCercanias} object.
     * @throws JSONException
     */
    public EstacionCercanias retrieveEstacionCercanias(JSONObject jsonObject)
            throws JSONException {

        EstacionCercanias estacionCercanias = new EstacionCercanias();

        estacionCercanias.setCodigo(jsonObject.getInt("Codigo"));
        estacionCercanias.setDescripcion(jsonObject.getString("Descripcion"));
        estacionCercanias.setLatitude(jsonObject.getDouble("Lat"));
        estacionCercanias.setLongitude(jsonObject.getDouble("Lon"));
        estacionCercanias.setZona(jsonObject.getString("Zona"));

        // TODO: Obtener servicios asociados a la estacion.
        List<Servicio> servicioList;

        return estacionCercanias;
    }

    /**
     * Retrieve {@link EstacionCercanias} from {@link JSONArray} by
     * {@link LineaCercanias}.
     * 
     * @param jsonArray
     *            Containing {@link EstacionCercanias} to parse.
     * @return {@link EstacionCercanias} list parsed.
     * @throws JSONException
     */
    public List<EstacionCercanias> retrieveLineasEstacionCercaniasFromJSONArray(
            JSONArray jsonArray) throws JSONException {
        List<EstacionCercanias> estacionCercaniasList = new ArrayList<EstacionCercanias>();

        EstacionCercanias estacionCercanias;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectEstacionCercanias = jsonArray.getJSONObject(i);
            estacionCercanias = retrieveLineasEstacionCercanias(jsonObjectEstacionCercanias);
            estacionCercaniasList.add(estacionCercanias);
        }
        return estacionCercaniasList;
    }

    /**
     * Retrieve {@link EstacionCercanias} from {@link JSONObject} data from
     * {@link LineaCercanias}.
     * 
     * @param jsonObject
     *            Object to convert.
     * @return {@link EstacionCercanias} object.
     * @throws JSONException
     */
    public EstacionCercanias retrieveLineasEstacionCercanias(
            JSONObject jsonObject) throws JSONException {

        EstacionCercanias estacionCercanias = new EstacionCercanias();

        estacionCercanias.setCodigo(jsonObject.getInt("Codigo"));
        estacionCercanias.setDescripcion(jsonObject.getString("Descripcion"));
        estacionCercanias.setViaSentidoDestino(jsonObject
                .getString("ViaSentidoDestino"));
        estacionCercanias.setViaSentidoOrigen(jsonObject
                .getString("ViaSentidoOrigen"));

        // TODO: Obtener Correspondencia asociados a la estacion.
        List<Correspondencia> correspondenciaList;

        return estacionCercanias;
    }

    public JSONObject getmJSONObjectEstacionCercanias() {
        return mJSONObjectEstacionCercanias;
    }

    public void setmJSONObjectEstacionCercanias(
            JSONObject mJSONObjectEstacionCercanias) {
        this.mJSONObjectEstacionCercanias = mJSONObjectEstacionCercanias;
    }

}
