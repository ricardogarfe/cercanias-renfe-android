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
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
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

    /**
     * Retrieve {@link EstacionCercanias} from JSON data from file or URL
     * depending on fromFile parameter.
     * 
     * @param filePath
     *            File Path to retrive data.
     * @param fromFile
     *            boolean to check if data is retrieved from file or URL.
     * @return {@link List} of {@link EstacionCercanias}.
     * @throws IOException
     * @throws JSONException
     */
    public List<EstacionCercanias> retrieveEstacionCercaniasFromJSON(
            String filePath, boolean fromFile) throws IOException,
            JSONException {

        List<EstacionCercanias> estacionCercaniasList = new ArrayList<EstacionCercanias>();

        mJSONObjectEstacionCercanias = getJSONFromFile(filePath);

        JSONArray jsonArray = (mJSONObjectEstacionCercanias
                .getJSONObject("Estaciones")).getJSONArray("Estacion");

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
        GeoPoint geoPoint = retrieveGeoPoint(jsonObject.getDouble("Lat"),
                jsonObject.getDouble("Lon"));
        estacionCercanias.setGeoPoint(geoPoint);
        estacionCercanias.setZona(jsonObject.getInt("Zona"));

        // TODO: Obtener servicios asociados a la estacion.
        List<Servicio> servicioList;

        return estacionCercanias;
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
        return mJSONObjectEstacionCercanias;
    }

    public void setMJSONObjectNucleos(JSONObject mJSONObjectNucleos) {
        this.mJSONObjectEstacionCercanias = mJSONObjectNucleos;
    }
}
