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

package com.ricardogarfe.renfe;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.ricardogarfe.renfe.adapter.NucleoAdapter;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONNucleosCercaniasParser;

public class NucleosActivity extends ListActivity {

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
                    .retrieveNucleoCercaniasFromJSON(
                            JSONNucleosCercaniasParser.NUCLEOS_JSON, true);
        } catch (Exception e) {
            Log.e(TAG, "Parsing JSON data:\t" + e.getMessage());
        }

        setContentView(R.layout.nucleos_main);

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
