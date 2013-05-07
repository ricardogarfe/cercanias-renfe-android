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

package com.ricardogarfe.renfe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.model.NucleoCercanias;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author ricardo
 * 
 */
public class NucleoAdapter extends BaseAdapter implements Filterable {

    private Context mContext;

    private List<NucleoCercanias> mFilteredNucleoCercaniasList;

    private List<NucleoCercanias> mNucleoCercaniasList;

    public NucleoAdapter(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return mNucleoCercaniasList.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mNucleoCercaniasList.get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        // Asignar el identificador asociado a cada nucleo para hacer las
        // peticiones de horarios.
        NucleoCercanias nucleoCercaniasSelected = (NucleoCercanias) getItem(position);
        return nucleoCercaniasSelected.getCodigo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        View view;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.complex_list_main, null);

        // Dar valores a los Widgets del elemento complejo de la lista
        // descrito en complex_list_main.xml

        // Text Tittle Nucleo.
        TextView titleTextView = (TextView) view.findViewById(R.id.nucleo_name);

        titleTextView.setText(mNucleoCercaniasList.get(position)
                .getDescripcion());
        titleTextView.setTextColor(Color.WHITE);

        // TODO: Añadir descripcion.
        TextView descriptionTextView = (TextView) view
                .findViewById(R.id.nucleo_description);

        descriptionTextView.setText("Descripción a cambiar por algo real.");
        descriptionTextView.setTextColor(Color.WHITE);

        // TODO: Obtener Images del JSON, donde se ha de definir la ruta ?
        ImageView imageViewLogo = (ImageView) view
                .findViewById(R.id.nucleo_image);

        imageViewLogo.setImageDrawable(mContext.getResources().getDrawable(
                R.drawable.logo_cercanias));

        view.setId(mNucleoCercaniasList.get(position).getCodigo());
        return view;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<NucleoCercanias> getmNucleoCercaniasList() {
        return mNucleoCercaniasList;
    }

    public void setmNucleoCercaniasList(
            List<NucleoCercanias> mNucleoCercaniasList) {
        this.mNucleoCercaniasList = mNucleoCercaniasList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Filterable#getFilter()
     */
    public Filter getFilter() {
        // TODO Auto-generated method stub
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                    FilterResults results) {

                // arraylist original mNucleoCercaniasList moriganal filter
                // mFilteredNucleoCercaniasList
                mNucleoCercaniasList = (List<NucleoCercanias>) results.values; // has
                                                                               // the
                                                                               // filtered
                                                                               // values
                notifyDataSetChanged(); // notifies the data with new filtered
                                        // values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults(); // Holds the
                                                             // results of a
                                                             // filtering
                                                             // operation in
                                                             // values
                List<NucleoCercanias> FilteredArrList = new ArrayList<NucleoCercanias>();

                if (mFilteredNucleoCercaniasList == null) {
                    mFilteredNucleoCercaniasList = new ArrayList<NucleoCercanias>(
                            mNucleoCercaniasList); // saves the original data in
                                                   // mOriginalValues
                }

                /********
                 * 
                 * If constraint(CharSequence that is received) is null returns
                 * the mOriginalValues(Original) values else does the Filtering
                 * and returns FilteredArrList(Filtered)
                 * 
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mFilteredNucleoCercaniasList.size();
                    results.values = mFilteredNucleoCercaniasList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mFilteredNucleoCercaniasList.size(); i++) {
                        NucleoCercanias nucleoCercanias = mFilteredNucleoCercaniasList
                                .get(i);
                        if (nucleoCercanias.getDescripcion().toLowerCase()
                                .startsWith(constraint.toString())) {
                            FilteredArrList.add(nucleoCercanias);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}
