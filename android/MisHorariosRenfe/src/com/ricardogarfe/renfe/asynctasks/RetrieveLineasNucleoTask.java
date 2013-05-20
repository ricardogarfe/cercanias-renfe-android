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

package com.ricardogarfe.renfe.asynctasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.LineaCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.parser.JSONEstacionCercaniasParser;
import com.ricardogarfe.renfe.parser.JSONLineasCercaniasParser;

/**
 * @author ricardo
 * 
 */
public class RetrieveLineasNucleoTask extends
        AsyncTask<Object, Integer, List<LineaCercanias>> {

    private ProgressDialog progressDialog = null;

    private JSONLineasCercaniasParser mJSONLineasCercaniasParser;

    private JSONEstacionCercaniasParser mJSONEstacionCercaniasParser;

    private JSONObject mJSONObjectLineaCercanias;

    private List<LineaCercanias> mLineaCercaniasList = null;

    private List<EstacionCercanias> mEstacionCercaniasList;

    private NucleoCercanias mNucleoCercanias;

    private final String TAG = getClass().getSimpleName();

    private Handler messageLineasNucleoHandler;
    private Message messageLineasNucleo;

    protected void onPreExecute() {
        // Show progressDialog
        progressDialog.show();
    }

    @Override
    protected List<LineaCercanias> doInBackground(Object... nucleoCercanias) {

        mJSONLineasCercaniasParser = new JSONLineasCercaniasParser();

        mJSONEstacionCercaniasParser = new JSONEstacionCercaniasParser();
        mJSONLineasCercaniasParser
                .setmJsonEstacionCercaniasParser(mJSONEstacionCercaniasParser);

        mNucleoCercanias = (NucleoCercanias) nucleoCercanias[0];

        try {

            mEstacionCercaniasList = mJSONEstacionCercaniasParser
                    .retrieveEstacionCercaniasFromJSON(
                            mNucleoCercanias.getEstacionesJSON(), false);

            mLineaCercaniasList = retrieveLineasCercaniasFromJSON(
                    mNucleoCercanias.getLineasJSON(), false);

        } catch (IOException e) {
            Log.e(TAG,
                    "Error retrieving JSON file from nucleo Cercanias '"
                            + mNucleoCercanias.getDescripcion() + "':\t"
                            + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG,
                    "Error Parsing JSON file from nucleo Cercanias '"
                            + mNucleoCercanias.getDescripcion() + "':\t"
                            + e.getMessage());
        }

        return mLineaCercaniasList;
    }

    @Override
    protected void onProgressUpdate(final Integer... progress) {
        progressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(List<LineaCercanias> lineasCercaniasList) {
        progressDialog.dismiss();

        if (lineasCercaniasList != null && !lineasCercaniasList.isEmpty()) {

            messageLineasNucleo = new Message();

            messageLineasNucleo.obj = lineasCercaniasList;

            getMessageLineasNucleoHandler().sendMessage(messageLineasNucleo);

            Log.d(TAG,
                    "Retrieve Lineas correctly from:\t"
                            + mNucleoCercanias.getDescripcion());

        } else {
            Toast.makeText(
                    progressDialog.getContext(),
                    "Lineas retrieving error from 'nucleo':\t"
                            + mNucleoCercanias.getDescripcion(),
                    Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(lineasCercaniasList);

    }

    /**
     * Retrieve {@link LineaCercanias} from JSON data from file or URL depending
     * on fromFile parameter.
     * 
     * @param filePath
     *            File Path or URL to retrive data.
     * @param fromFile
     *            boolean to check if data is retrieved from file or URL.
     * @return {@link List} of {@link LineaCercanias}.
     * @throws IOException
     * @throws JSONException
     */
    public List<LineaCercanias> retrieveLineasCercaniasFromJSON(
            String filePath, boolean fromFile) throws IOException,
            JSONException {

        List<LineaCercanias> lineaCercaniasList = new ArrayList<LineaCercanias>();

        if (fromFile) {
            mJSONObjectLineaCercanias = mJSONLineasCercaniasParser
                    .getJSONFromFile(filePath);
        } else {
            mJSONObjectLineaCercanias = mJSONLineasCercaniasParser
                    .getJSONFromUrl(filePath);
        }

        JSONArray jsonArray = (mJSONObjectLineaCercanias
                .getJSONObject("Lineas")).optJSONArray("Linea");

        if (jsonArray == null) {
            jsonArray = new JSONArray();
            jsonArray.put((mJSONObjectLineaCercanias.getJSONObject("Lineas"))
                    .getJSONObject("Linea"));
        }
        LineaCercanias lineaCercanias;

        int total = jsonArray.length();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectEstacionCercanias = jsonArray.getJSONObject(i);
            lineaCercanias = mJSONLineasCercaniasParser
                    .retrieveLineaCercanias(jsonObjectEstacionCercanias);

            // Retrieve Lat and Long from estacionesCercaniasList
            retrieveLatLongFromEstacion(lineaCercanias);
            lineaCercaniasList.add(lineaCercanias);

            // publishing the progress....
            publishProgress((int) i * 100 / total);
        }

        Log.d(TAG, "Finished retrieving Lineas from nucleo: "
                + mNucleoCercanias.getCodigo());

        return lineaCercaniasList;

    }

    /**
     * Update Location values from {@link EstacionCercanias} from
     * {@link LineaCercanias}.
     * 
     * @param lineaCercanias
     *            to update.
     */
    public void retrieveLatLongFromEstacion(LineaCercanias lineaCercanias) {

        for (EstacionCercanias estacionCercanias : lineaCercanias
                .getEstacionCercaniasList()) {

            for (EstacionCercanias estacionCercaniasFromList : mEstacionCercaniasList) {

                if (estacionCercanias.getCodigo().compareTo(
                        estacionCercaniasFromList.getCodigo()) == 0) {

                    final Integer positionToUpdate = lineaCercanias
                            .getEstacionCercaniasList().indexOf(
                                    estacionCercanias);

                    estacionCercanias.setLatitude(estacionCercaniasFromList
                            .getLatitude());
                    estacionCercanias.setLongitude(estacionCercaniasFromList
                            .getLongitude());
                    estacionCercanias.setZona(estacionCercaniasFromList
                            .getZona());

                    lineaCercanias.getEstacionCercaniasList().set(
                            positionToUpdate, estacionCercanias);

                    break;
                }
            }
        }
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public Handler getMessageLineasNucleoHandler() {
        return messageLineasNucleoHandler;
    }

    public void setMessageLineasNucleoHandler(Handler messageLineasNucleoHandler) {
        this.messageLineasNucleoHandler = messageLineasNucleoHandler;
    }

}
