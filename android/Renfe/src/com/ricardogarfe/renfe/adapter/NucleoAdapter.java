/**
 * 
 */
package com.ricardogarfe.renfe.adapter;

import java.util.List;

import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.model.NucleoCercanias;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author ricardo
 * 
 */
public class NucleoAdapter extends BaseAdapter {

    private Context mContext;

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
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        // TODO Asignar el identificador asociado a cada nucleo para hacer las
        // peticiones de horarios.
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

}
