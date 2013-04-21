/**
 * 
 */
package com.ricardogarfe.renfe.services.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

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

    private Context context;

    public List<NucleoCercanias> retrieveNucleoCercaniasFromJSON(String file)
            throws IOException, JSONException {

        InputStream is = context.getAssets().open(file);

        List<NucleoCercanias> nucleoCercaniasList = new ArrayList<NucleoCercanias>();
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String bufferString = new String(buffer);

        // convert string to JSONArray
        JSONTokener jsonTokener = new JSONTokener(bufferString);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        Log.i("MAIN", jsonObject.toString());
        // parse an Object from a random index in the JSONArray

        JSONArray jsonArray = (jsonObject.getJSONObject("Nucleos"))
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

        nucleoCercanias.setCodigo(jsonObject.getString("Codigo"));
        nucleoCercanias.setDescripcion(jsonObject.getString("Descripcion"));
        GeoPoint geoPoint = retrieveGeoPoint(jsonObject.getDouble("Lat"),
                jsonObject.getDouble("Lon"));
        nucleoCercanias.setGeoPoint(geoPoint);
        nucleoCercanias.setIconoMapa(jsonObject.getString("IconoMapa"));
        nucleoCercanias.setTarifas(jsonObject.getString("Tarifas"));
        nucleoCercanias.setIncidencias(jsonObject.getString("Incidencias"));

        return nucleoCercanias;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
