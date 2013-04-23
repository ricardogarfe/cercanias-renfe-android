/**
 * 
 */
package com.ricardogarfe.renfe.services.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.ricardogarfe.renfe.model.NucleoCercanias;

/**
 * 
 * JSON Parser for Cercanias data superclass.
 * 
 * @author ricardo
 */
public class JSONCercaniasParser {

    private Context context;

    private String TAG = getClass().getSimpleName();

    /**
     * Retrieve {@link GeoPoint} object from latitude and longitude.
     * 
     * @param latitude
     *            Latitude values greater than -90 and less than 90
     * @param longitude
     *            Latitude values greater than -80 and less than 180.
     * 
     * @throws IllegalArgumentException
     *             if latitude is less than -90 or greater than 90
     * @throws IllegalArgumentException
     *             if longitude is less than -180 or greater than 180
     * @return {@link GeoPoint} object complete.
     */
    public GeoPoint retrieveGeoPoint(double latitude, double longitude) {
        return new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
    }

    /**
     * Retrieve JSON file from URL.
     * 
     * @param url
     *            URL value to retrieve JSON file.
     * @return {@link JSONObject} representing URL JSON values.
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject getJSONFromUrl(String url) throws IOException,
            JSONException {

        // Making HTTP request
        InputStream inputStream;
        String json;
        JSONObject jsonObject;

        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        inputStream.close();
        json = sb.toString();

        // try parse the string to a JSON object
        jsonObject = new JSONObject(json);

        // return JSON String
        return jsonObject;

    }

    /**
     * Retrive {@link JSONObject} from File.
     * 
     * @param file
     *            File location to convert to JSON.
     * @return {@link JSONObject} representing File.
     */
    public JSONObject getJSONFromFile(String file) throws IOException,
            JSONException {

        InputStream is = getContext().getAssets().open(file);

        List<NucleoCercanias> nucleoCercaniasList = new ArrayList<NucleoCercanias>();

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String bufferString = new String(buffer);

        // convert string to JSONObject
        JSONTokener jsonTokener = new JSONTokener(bufferString);
        JSONObject jSONObject = new JSONObject(jsonTokener);

        Log.i("TAG", jSONObject.toString());
        // parse an Object from a random index in the JSONArray

        return jSONObject;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
