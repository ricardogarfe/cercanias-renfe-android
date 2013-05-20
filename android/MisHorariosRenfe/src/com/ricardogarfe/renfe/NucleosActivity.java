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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ricardogarfe.renfe.adapter.NucleoAdapter;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.parser.JSONNucleosCercaniasParser;

public class NucleosActivity extends ListActivity {

    private String TAG = getClass().getSimpleName();

    private NucleoAdapter mNucleoAdapter;

    private EditText nucleosSearch;

    private List<NucleoCercanias> mNucleoCercaniasList;

    private JSONNucleosCercaniasParser mJSONNucleosCercaniasParser = new JSONNucleosCercaniasParser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nucleos_main);

        mJSONNucleosCercaniasParser.setContext(this);

        try {
            mNucleoCercaniasList = mJSONNucleosCercaniasParser
                    .retrieveNucleoCercaniasFromJSON(
                            JSONNucleosCercaniasParser.NUCLEOS_JSON, true);
        } catch (Exception e) {
            Log.e(TAG, "Parsing JSON data:\t" + e.getMessage());
        }

        configureWidgets();
    }

    /**
     * Configure Widget values to initialize ui.
     */
    public void configureWidgets() {

        // Asociar el adapter para tratar la información.
        mNucleoAdapter = new NucleoAdapter(this);
        mNucleoAdapter.setmNucleoCercaniasList(mNucleoCercaniasList);
        setListAdapter(mNucleoAdapter);

        // Autocomplete nucleos search
        nucleosSearch = (EditText) findViewById(R.id.nucleosSearch);

        nucleosSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                    int arg3) {
                // When user changed the Text
                mNucleoAdapter.getFilter().filter(cs);
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                    int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * Create a new intent to call other Activity. Using the methods "putExtra"
     * we can send data to the new activity
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.i(TAG, "Nucleo "
                + mNucleoCercaniasList.get(position).getDescripcion()
                + " selected");

        NucleoCercanias nucleosCercaniasSelected = (NucleoCercanias) mNucleoAdapter
                .getItem(position);

        Intent intentEstacionesNucleos = new Intent(NucleosActivity.this,
                EstacionesNucleoViajeActivity.class);
        intentEstacionesNucleos.putExtra("nucleoCercanias",
                nucleosCercaniasSelected);

        startActivity(intentEstacionesNucleos);
    }

}
