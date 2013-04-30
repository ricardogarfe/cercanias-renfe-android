/**
 * 
 */
package com.ricardogarfe.renfe.asyncTask;

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

import com.ricardogarfe.renfe.EstacionesNucleoViajeActivity;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.services.parser.JSONEstacionCercaniasParser;

/**
 * @author ricardo
 * 
 */
public class RetrieveEstacionesNucleoTask extends
        AsyncTask<String, Integer, List<EstacionCercanias>> {

    private ProgressDialog progressDialog = null;
    private JSONEstacionCercaniasParser jsonEstacionCercaniasParser;

    private JSONObject mJSONObjectEstacionCercanias;

    private List<EstacionCercanias> estacionCercaniasList = null;

    private String urlJSON;

    private final String TAG = getClass().getSimpleName();

    private Handler messageEstacionesNucleoHandler;
    private Message messageEstacionesNucleo;

    protected void onPreExecute() {
        // Show progressDialog
        progressDialog = new ProgressDialog(
                EstacionesNucleoViajeActivity.mEstacionesNucleoViajeContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setMessage("Obteniendo estaciones...");
        progressDialog.show();
    }

    @Override
    protected List<EstacionCercanias> doInBackground(String... urlJSONString) {

        jsonEstacionCercaniasParser = new JSONEstacionCercaniasParser();

        urlJSON = urlJSONString[0].toString();
        Log.d(TAG, urlJSON);

        try {
            estacionCercaniasList = retrieveEstacionCercaniasFromJSON(urlJSON,
                    false);
        } catch (IOException e) {
            Log.e(TAG,
                    "Error retrieving JSON file from Estaciones Cercanias:\t"
                            + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "Error Parsing JSON file from Estaciones Cercanias:\t"
                    + e.getMessage());
        }

        return estacionCercaniasList;
    }

    @Override
    protected void onProgressUpdate(final Integer... progress) {
        progressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(List<EstacionCercanias> estacionesCercaniasList) {
        progressDialog.dismiss();

        if (estacionesCercaniasList != null
                && !estacionesCercaniasList.isEmpty()) {

            messageEstacionesNucleo = new Message();

            messageEstacionesNucleo.obj = estacionesCercaniasList;

            getMessageEstacionesNucleoHandler().sendMessage(messageEstacionesNucleo);

            Log.d(TAG, "Retrieve Estaciones correctly from:\t" + urlJSON);

        } else {
            Toast.makeText(
                    EstacionesNucleoViajeActivity.mEstacionesNucleoViajeContext,
                    "Estaciones retriving error :" + urlJSON, Toast.LENGTH_LONG)
                    .show();
        }
        super.onPostExecute(estacionesCercaniasList);

    }

    /**
     * Retrieve {@link EstacionCercanias} from JSON data from file or URL
     * depending on fromFile parameter.
     * 
     * @param filePath
     *            File Path or URL to retrive data.
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

        if (fromFile) {
            mJSONObjectEstacionCercanias = jsonEstacionCercaniasParser
                    .getJSONFromFile(filePath);
        } else {
            mJSONObjectEstacionCercanias = jsonEstacionCercaniasParser
                    .getJSONFromUrl(filePath);
        }

        JSONArray jsonArray = (mJSONObjectEstacionCercanias
                .getJSONObject("Estaciones")).getJSONArray("Estacion");

        EstacionCercanias estacionCercanias;

        int total = jsonArray.length();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectEstacionCercanias = jsonArray.getJSONObject(i);
            estacionCercanias = jsonEstacionCercaniasParser
                    .retrieveEstacionCercanias(jsonObjectEstacionCercanias);
            estacionCercaniasList.add(estacionCercanias);

            // publishing the progress....
            publishProgress((int) i * 100 / total);
        }

        Log.d(TAG, "Finished retrieving Estaciones from nucleo");

        return estacionCercaniasList;

    }

    public Handler getMessageEstacionesNucleoHandler() {
        return messageEstacionesNucleoHandler;
    }

    public void setMessageEstacionesNucleoHandler(
            Handler messageEstacionesNucleoHandler) {
        this.messageEstacionesNucleoHandler = messageEstacionesNucleoHandler;
    }
}
