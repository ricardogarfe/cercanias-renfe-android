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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.model.HorarioCercanias;

/**
 * @author ricardo
 * 
 */
public class HorariosAdapter extends BaseAdapter {

    private static final String CIVIS = "VVV";

    private Context mContext;

    private Date mInitialDate;

    private List<HorarioCercanias> mHorarioCercaniasList;

    private String TAG = getClass().getSimpleName();

    // Text font style.
    private Typeface typeFaceHorarioFont;

    public HorariosAdapter(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;

        typeFaceHorarioFont = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/LCDPHONE.ttf");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return getmHorarioCercaniasList().size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return getmHorarioCercaniasList().get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        // Asignar el identificador asociado a cada nucleo para hacer las
        // peticiones de horarios.
        HorarioCercanias horarioCercaniasSelected = (HorarioCercanias) getItem(position);
        // TODO
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

        HorarioCercanias horarioCercanias = mHorarioCercaniasList.get(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.horarios_complex_list, parent,
                    false);
        } else
            view = convertView;

        // Dar valores a los Widgets del elemento complejo de la lista
        // descrito en complex_list_main.xml
        TextView minutesLeft = (TextView) view.findViewById(R.id.minutesLeft);
        minutesLeft.setText(String
                .valueOf(retrieveMinutesDifference(horarioCercanias)));
        minutesLeft.setTypeface(typeFaceHorarioFont);

        TextView minutesOrHour = (TextView) view
                .findViewById(R.id.minutesOrHour);
        minutesOrHour.setText("min");
        minutesOrHour.setTypeface(typeFaceHorarioFont);

        TextView horaSalida = (TextView) view.findViewById(R.id.horaSalida);
        horaSalida.setText(horarioCercanias.getHoraSalida());
        horaSalida.setTypeface(typeFaceHorarioFont);

        TextView duracionViaje = (TextView) view
                .findViewById(R.id.duracionViaje);
        duracionViaje.setText(horarioCercanias.getDuracion());
        duracionViaje.setTypeface(typeFaceHorarioFont);

        TextView transbordo = (TextView) view.findViewById(R.id.transbordo);
        transbordo.setText(horarioCercanias.getTransbordo().isEmpty() ? ""
                : "T");
        transbordo.setTypeface(typeFaceHorarioFont);

        TextView civis = (TextView) view.findViewById(R.id.civis);
        civis.setText(horarioCercanias.getCodCivis().compareTo(CIVIS) == 0 ? "C"
                : "");
        civis.setTypeface(typeFaceHorarioFont);

        TextView linea = (TextView) view.findViewById(R.id.linea);
        linea.setText(horarioCercanias.getLinea());
        linea.setTypeface(typeFaceHorarioFont);

        return view;
    }

    /**
     * return Long.valueOf("0");
     * 
     * Devuelve los minutos de diferencia entre la fecha actual y la próxima
     * salida.
     * 
     * @param horarioCercanias
     * @return Número de minutos de diferencia.
     */
    public Long retrieveMinutesDifference(HorarioCercanias horarioCercanias) {

        Long diffMinutes;

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date dateSalida;
        String horaSalida = horarioCercanias.getHoraSalida();

        Calendar initialCalendar = Calendar.getInstance();
        initialCalendar.setTime(getmInitialDate());
        int year = initialCalendar.get(Calendar.YEAR);
        int month = initialCalendar.get(Calendar.MONTH) + 1;
        int day = initialCalendar.get(Calendar.DAY_OF_MONTH);

        try {
            dateSalida = dateFormat.parse(year + "-" + month + "-" + day + " "
                    + horaSalida);

            // Si la fecha inicial es mayor que la respuesta,
            // continua la siguiente iteración.

            long diff = dateSalida.getTime() - getmInitialDate().getTime();
            diffMinutes = diff / (60 * 1000);

        } catch (ParseException e) {
            Log.e(TAG,
                    "Error converting base data, result could have worng start date.");
            return Long.valueOf("0");
        }

        return diffMinutes;

    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<HorarioCercanias> getmHorarioCercaniasList() {
        return mHorarioCercaniasList;
    }

    public void setmHorarioCercaniasList(
            List<HorarioCercanias> mHorarioCercaniasList) {
        this.mHorarioCercaniasList = mHorarioCercaniasList;
    }

    public Date getmInitialDate() {
        return mInitialDate;
    }

    public void setmInitialDate(Date mInitialDate) {
        this.mInitialDate = mInitialDate;
    }

}
