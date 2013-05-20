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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.model.LineaCercanias;

/**
 * @author ricardo
 * 
 */
public class LineaAdapter extends BaseAdapter {

    private Context mContext;

    private List<LineaCercanias> mLineaCercaniasList;

    public LineaAdapter(Context context) {
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
        return getmLineaCercaniasList().size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return getmLineaCercaniasList().get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        // Asignar el identificador asociado a cada nucleo para hacer las
        // peticiones de horarios.
        LineaCercanias lineaCercaniasSelected = (LineaCercanias) getItem(position);
        return 0;
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

        final LineaCercanias lineaCercanias = mLineaCercaniasList.get(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lineas_detail, parent, false);
        } else
            view = convertView;

        // Dar valores a los Widgets del elemento complejo de la lista
        // descrito en lineas_detail.xml

        // Código Línea.
        TextView codigoTextView = (TextView) view
                .findViewById(R.id.codigo_linea);
        codigoTextView.setText(lineaCercanias.getCodigo());

        // Descripcion recorrido.
        TextView descriptionTextView = (TextView) view
                .findViewById(R.id.descripcion_linea);
        descriptionTextView.setText(lineaCercanias.getDescripcion());

        return view;

    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<LineaCercanias> getmLineaCercaniasList() {
        return mLineaCercaniasList;
    }

    public void setmLineaCercaniasList(List<LineaCercanias> mLineaCercaniasList) {
        this.mLineaCercaniasList = mLineaCercaniasList;
    }

}
