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

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardogarfe.renfe.adapter.LineaAdapter;
import com.ricardogarfe.renfe.asynctasks.RetrieveLineasNucleoTask;
import com.ricardogarfe.renfe.model.LineaCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;

public class LineasActivity extends ListActivity {

    private String TAG = getClass().getSimpleName();

    private Context mLineasActivityContext;

    private LineaAdapter mLineaAdapter;

    private NucleoCercanias mNucleoCercanias;

    private List<LineaCercanias> mLineaCercaniasList;

    private TextView nucleoNameTextView;

    private RetrieveLineasNucleoTask mRetrieveLineasNucleoTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lineas_main);

        mLineasActivityContext = this;

        configureWidgets();

        configureLineasPorNucleo();

    }

    /**
     * 
     */
    public void configureLineasPorNucleo() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(mNucleoCercanias.getDescripcion());
        progressDialog.setMessage(getResources().getString(
                R.string.retrievingLines));

        mRetrieveLineasNucleoTask = new RetrieveLineasNucleoTask();

        mRetrieveLineasNucleoTask.setProgressDialog(progressDialog);

        mRetrieveLineasNucleoTask.execute(mNucleoCercanias, null, null);
        mRetrieveLineasNucleoTask
                .setMessageLineasNucleoHandler(messageLineasNucleoHandler);
    }

    /**
     * Configure Widget values to initialize ui.
     */
    public void configureWidgets() {

        // Retrieve value from nucleos activity.
        Intent intentFromActivity = getIntent();

        if (intentFromActivity != null) {

            mNucleoCercanias = intentFromActivity
                    .getParcelableExtra("nucleoCercanias");
        }

        // Autocomplete nucleos search
        nucleoNameTextView = (TextView) findViewById(R.id.nucleo_name);
        nucleoNameTextView.setText(mNucleoCercanias.getDescripcion());

    }

    /**
     * Create a new intent to call other Activity. Using the methods "putExtra"
     * we can send data to the new activity
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.i(TAG, "Nucleo "
                + mLineaCercaniasList.get(position).getDescripcion()
                + " selected");

        Toast.makeText(this,
                "Nucleo " + mLineaCercaniasList.get(position).getDescripcion(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Handler to retrieve values from {@link RetrieveLineasNucleoTask} result.
     */
    private Handler messageLineasNucleoHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            mLineaCercaniasList = (ArrayList<LineaCercanias>) msg.obj;

            // Asociar el adapter para tratar la información.
            mLineaAdapter = new LineaAdapter(mLineasActivityContext);
            mLineaAdapter.setmLineaCercaniasList(mLineaCercaniasList);
            setListAdapter(mLineaAdapter);

        }
    };
}
