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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

import com.google.android.maps.GeoPoint;

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

        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpUriRequest request = new HttpGet(url);
        request.setHeader("Accept-Encoding", "gzip");

        // Execute request
        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        Header contentEncoding = entity.getContentEncoding();
        inputStream = entity.getContent();
        if (contentEncoding != null
                && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
            inputStream = new GZIPInputStream(inputStream);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        inputStream.close();
        json = sb.toString();

        // try parse the string to a JSON object
        // convert string to JSONObject
        JSONTokener jsonTokener = new JSONTokener(json);
        JSONObject jSONObject = new JSONObject(jsonTokener);

        Log.i(TAG, "Convert JSON from URL:\t" + jSONObject.toString());
        // return JSON String
        return jSONObject;

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

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String bufferString = new String(buffer);

        // convert string to JSONObject
        JSONTokener jsonTokener = new JSONTokener(bufferString);
        JSONObject jSONObject = new JSONObject(jsonTokener);

        Log.i(TAG, "Convert JSON from File:\t" + jSONObject.toString());
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
