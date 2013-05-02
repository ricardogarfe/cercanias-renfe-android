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

package com.ricardogarfe.renfe.fragments;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ricardogarfe.renfe.EstacionesNucleoViajeActivity;
import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.adapter.NucleoAdapter;
import com.ricardogarfe.renfe.asyncTaskLoader.NucleosLoader;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONNucleosCercaniasParser;

public class NucleosListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<NucleoCercanias>> {

    // Context
    public static Context mActivityContext;

    private String TAG = getClass().getSimpleName();

    private NucleoAdapter mNucleoAdapter;

    private List<NucleoCercanias> mNucleoCercaniasList;

    private JSONNucleosCercaniasParser mJSONNucleosCercaniasParser = new JSONNucleosCercaniasParser();

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater
     * , android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(android.R.id.list, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivityContext = getActivity().getApplicationContext();

        mJSONNucleosCercaniasParser.setContext(mActivityContext);

        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        // Incializar el adapter para tratar la información.
        mNucleoAdapter = new NucleoAdapter(mActivityContext);

        setListAdapter(mNucleoAdapter);
    }

    /**
     * Create a new intent to call other Activity. Using the methods "putExtra"
     * we can send data to the new activity
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "Nucleo "
                + mNucleoCercaniasList.get(position).getDescripcion()
                + " selected");

        Intent intentEstacionesNucleos = new Intent(mActivityContext,
                EstacionesNucleoViajeActivity.class);
        intentEstacionesNucleos.putExtra("codigo_nucleo", mNucleoCercaniasList
                .get(position).getCodigo());
        intentEstacionesNucleos.putExtra("descripcion_nucleo",
                mNucleoCercaniasList.get(position).getDescripcion());

        intentEstacionesNucleos.putExtra("estaciones_json",
                mNucleoCercaniasList.get(position).getEstacionesJSON());

        startActivity(intentEstacionesNucleos);

    }

    /**
     * Crear el loader indicando el id, este caso se crea el loader asociado a
     * los núcleos, por eso no se trata el identificador.
     */
    public Loader<List<NucleoCercanias>> onCreateLoader(int id, Bundle args) {

        return new NucleosLoader(mActivityContext);
    }

    /**
     * Asociar el Adapter al resultado del Loader.
     */
    public void onLoadFinished(Loader<List<NucleoCercanias>> loader,
            List<NucleoCercanias> data) {

        mNucleoCercaniasList = data;
        mNucleoAdapter.setmNucleoCercaniasList(mNucleoCercaniasList);
        setListAdapter(mNucleoAdapter);
    }

    /**
     * Eliminar el resultado del adapter.
     */
    public void onLoaderReset(Loader<List<NucleoCercanias>> loader) {

        mNucleoAdapter.setmNucleoCercaniasList(null);
    }

}
