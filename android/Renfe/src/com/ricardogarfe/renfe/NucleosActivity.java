/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 * 
 * Jon Segador <jonseg@gmail.com>
 * 
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.ricardogarfe.renfe;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ricardogarfe.renfe.adapter.NucleoAdapter;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONNucleosCercaniasParser;

public class NucleosActivity extends ListActivity {

    private static String NUCLEOS_JSON = "json/nucleos_cercanias.json";

    private SharedPreferences mPreferences;

    private String TAG = getClass().getSimpleName();

    private NucleoAdapter mNucleoAdapter;

    private List<NucleoCercanias> mNucleoCercaniasList;

    private JSONNucleosCercaniasParser mJSONNucleosCercaniasParser = new JSONNucleosCercaniasParser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mJSONNucleosCercaniasParser.setContext(this);

        try {
            mNucleoCercaniasList = mJSONNucleosCercaniasParser
                    .retrieveNucleoCercaniasFromJSON(NUCLEOS_JSON, true);
        } catch (Exception e) {
            Log.e(TAG, "Parsing JSON data:\t" + e.getMessage());
        }

        setContentView(R.layout.nucleos_main);

        mPreferences = getSharedPreferences("Renfe", MODE_PRIVATE);

        // Asociar el adapter para tratar la información.
        mNucleoAdapter = new NucleoAdapter(this);
        mNucleoAdapter.setmNucleoCercaniasList(mNucleoCercaniasList);
        setListAdapter(mNucleoAdapter);

    }

    /**
     * Create a new intent to call other Activity. Using the methods "putExtra"
     * we can send data to the new activity
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.i(TAG, "Nucleo "
                + mNucleoCercaniasList.get(position).getDescripcion()
                + " selected");

        Intent intentEstacionesNucleos = new Intent(NucleosActivity.this,
                EstacionesNucleoViajeActivity.class);
        intentEstacionesNucleos.putExtra("codigo_nucleo", mNucleoCercaniasList
                .get(position).getCodigo());
        intentEstacionesNucleos.putExtra("descripcion_nucleo",
                mNucleoCercaniasList.get(position).getDescripcion());

        intentEstacionesNucleos.putExtra("estaciones_json",
                mNucleoCercaniasList.get(position).getEstacionesJSON());

        startActivity(intentEstacionesNucleos);
    }

}
