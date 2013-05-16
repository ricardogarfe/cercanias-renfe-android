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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardogarfe.renfe.asynctasks.HorarioCercaniasTask;
import com.ricardogarfe.renfe.model.DatosPeticionHorarioCercanias;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.HorarioCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.model.TransbordoCercanias;
import com.ricardogarfe.renfe.services.handler.HorariosCercaniasHandler;

/**
 * Activity that retrieves and represents values from a trip between two
 * {@link EstacionCercanias} selected inside a {@link NucleoCercanias}.
 * 
 * @author ricardo
 * 
 */
public class HorarioCercaniasActivity extends Activity {

    private TextView textViewInfoTransbordo;
    private TextView textViewInfoStations;
    private TextView textViewNucleo;
    private TextView textViewInfoDate;

    private String mFulldate;

    private TableLayout mTableLayoutHorarios;

    private String mDay;
    private String mMonth;
    private String mYear;
    private String mHoraInicio;
    private String mHoraFinal;
    private String mMinutoInicio;

    private Date mInitialDate;

    // Context
    public static Context mHorarioCercaniasContext;

    private List<HorarioCercanias> mHorarioCercaniasList;

    private HorarioCercaniasTask mHorarioCercaniasTask;

    private DatosPeticionHorarioCercanias mDatosPeticionHorarioCercanias;

    private String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);

        mTableLayoutHorarios = (TableLayout) findViewById(R.id.tableLayoutHorarios);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        mHorarioCercaniasContext = this;

        mDatosPeticionHorarioCercanias = retrieveDatosPeticonHorariosCercanias();

        configureWidgets();

        runHorariosCercaniasTask();
    }

    /**
     * Configure and execute horariosCercanias Task.
     */
    public void runHorariosCercaniasTask() {

        mHorarioCercaniasTask = new HorarioCercaniasTask();
        ProgressDialog progressDialog = new ProgressDialog(
                HorarioCercaniasActivity.mHorarioCercaniasContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(mDatosPeticionHorarioCercanias.getNucleoName());
        progressDialog.setMessage(mDatosPeticionHorarioCercanias
                .getEstacionOrigenName()
                + " - "
                + mDatosPeticionHorarioCercanias.getEstacionDestinoName());
        mHorarioCercaniasTask.setProgressDialog(progressDialog);

        mHorarioCercaniasTask.execute(mDatosPeticionHorarioCercanias);
        mHorarioCercaniasTask
                .setMessageNucleoCercaniasHandler(messageHorariosCercaniasHandler);
    }

    /**
     * Configure Widget values to initialize ui.
     */
    public void configureWidgets() {

        // Text font style.
        Typeface typeFaceHorarioFont = Typeface.createFromAsset(
                getAssets(), "fonts/LCDPHONE.ttf");

        textViewNucleo = (TextView) findViewById(R.id.textViewNucleo);
        textViewNucleo.setTypeface(typeFaceHorarioFont);
        textViewInfoStations = (TextView) findViewById(R.id.textViewInfoStations);
        textViewInfoStations.setTypeface(typeFaceHorarioFont);
        textViewInfoDate = (TextView) findViewById(R.id.textViewInfoDate);
        textViewInfoDate.setTypeface(typeFaceHorarioFont);
        textViewInfoTransbordo = (TextView) findViewById(R.id.textViewInfoTransbordo);
        textViewInfoTransbordo.setTypeface(typeFaceHorarioFont);

        Button buttonVuelta = (Button) findViewById(R.id.buttonVuelta);
        buttonVuelta.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        HorarioCercaniasActivity.class);
                intent.putExtra("nucleoId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getNucleo()));
                intent.putExtra("nucleoName",
                        mDatosPeticionHorarioCercanias.getNucleoName());

                // Invertir destino por origen y viceversa.
                intent.putExtra("estacionOrigenId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getDestino()));
                intent.putExtra("estacionDestinoId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getOrigen()));

                intent.putExtra("estacionOrigenName",
                        mDatosPeticionHorarioCercanias.getEstacionDestinoName());
                intent.putExtra("estacionDestinoName",
                        mDatosPeticionHorarioCercanias.getEstacionOrigenName());

                intent.putExtra("day", mDay);
                intent.putExtra("month", mMonth);
                intent.putExtra("year", mYear);
                intent.putExtra("horaInicio", mHoraInicio);
                intent.putExtra("horaFinal", mHoraFinal);
                intent.putExtra("minutoInicio", mMinutoInicio);

                startActivity(intent);

            }
        });

        textViewInfoStations.setText(mDatosPeticionHorarioCercanias
                .getEstacionOrigenName()
                + " - "
                + mDatosPeticionHorarioCercanias.getEstacionDestinoName());
        textViewInfoDate.setText(mDay + "/" + mMonth + "/" + mYear);

        textViewNucleo.setText(mDatosPeticionHorarioCercanias.getNucleoName());
    }

    /**
     * Create {@link DatosPeticionHorarioCercanias} object from data retrieved
     * from intent.
     * 
     * @return {@link DatosPeticionHorarioCercanias} to retrieve horarios.
     * 
     */
    public DatosPeticionHorarioCercanias retrieveDatosPeticonHorariosCercanias() {

        DatosPeticionHorarioCercanias datosPeticionHorarioCercanias = new DatosPeticionHorarioCercanias();

        // ids
        int nucleoId = getIntent().getIntExtra("nucleoId", 0);
        int estacionOrigenId = getIntent().getIntExtra("estacionOrigenId", 0);
        int estacionDestinoId = getIntent().getIntExtra("estacionDestinoId", 0);

        // Nucleo
        String nucleoName = getIntent().getStringExtra("nucleoName");
        // Estacion Names
        String estacionOrigenName = getIntent().getStringExtra(
                "estacionOrigenName");
        String estacionDestinoName = getIntent().getStringExtra(
                "estacionDestinoName");

        // Date
        mDay = getIntent().getStringExtra("day");
        mMonth = getIntent().getStringExtra("month");
        mYear = getIntent().getStringExtra("year");

        StringBuffer stringBufferDf = new StringBuffer();
        stringBufferDf.append(mYear);
        stringBufferDf.append(mMonth);
        stringBufferDf.append(mDay);

        mFulldate = stringBufferDf.toString();

        // Hora Inicio Final
        mHoraInicio = getIntent().getStringExtra("horaInicio");
        mHoraFinal = getIntent().getStringExtra("horaFinal");
        mMinutoInicio = getIntent().getStringExtra("minutoInicio");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            mInitialDate = dateFormat.parse(mYear + "-" + mMonth + "-" + mDay
                    + " " + mHoraInicio + ":" + mMinutoInicio);
        } catch (ParseException e) {
            Log.e(TAG,
                    "Error converting base data, result could have worng start date.");
        }

        // Create object
        datosPeticionHorarioCercanias.setNucleo(Integer.toString(nucleoId));
        datosPeticionHorarioCercanias.setNucleoName(nucleoName);
        datosPeticionHorarioCercanias.setOrigen(Integer
                .toString(estacionOrigenId));
        datosPeticionHorarioCercanias.setDestino(Integer
                .toString(estacionDestinoId));
        datosPeticionHorarioCercanias.setFechaViaje(mFulldate);
        datosPeticionHorarioCercanias.setEstacionOrigenName(estacionOrigenName);
        datosPeticionHorarioCercanias
                .setEstacionDestinoName(estacionDestinoName);
        datosPeticionHorarioCercanias.setHoraInicio(mHoraInicio);
        datosPeticionHorarioCercanias.setHoraFinal(mHoraFinal);

        return datosPeticionHorarioCercanias;
    }

    /**
     * Handler to convert data from {@link HorarioCercanias} list values to UI.
     * 
     * <p>
     * Checks if data is correct using {@link Message} what values:
     * <ul>
     * <li>HorariosCercaniasHandler.TASK_COMPLETE</li>
     * <li>HorariosCercaniasHandler.TASK_ERROR</li>
     * </ul>
     * </p>
     */
    private Handler messageHorariosCercaniasHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
            case HorariosCercaniasHandler.TASK_COMPLETE:

                mHorarioCercaniasList = (ArrayList<HorarioCercanias>) msg.obj;

                int i = 0;

                // Text font style.
                Typeface typeFaceHorarioFont = Typeface.createFromAsset(
                        getAssets(), "fonts/LCDPHONE.ttf");

                LayoutParams tableRowLayoutParams = new TableRow.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                        1.0f);

                for (HorarioCercanias horarioCercanias : mHorarioCercaniasList) {

                    if (!checkCorrectInitialDate(horarioCercanias))
                        continue;

                    TableRow tableRowHorario = new TableRow(
                            HorarioCercaniasActivity.this);

                    if (i % 2 != 0)
                        tableRowHorario.setBackgroundColor(Color.LTGRAY);

                    // Hora salida
                    TextView textViewHoraSalida = new TextView(
                            HorarioCercaniasActivity.this);
                    textViewHoraSalida
                            .setText(horarioCercanias.getHoraSalida());
                    textViewHoraSalida.setTextSize(15);
                    textViewHoraSalida.setTextColor(Color.YELLOW);
                    textViewHoraSalida.setPadding(3, 3, 5, 3);
                    textViewHoraSalida.setGravity(Gravity.CENTER_HORIZONTAL);
                    textViewHoraSalida.setTypeface(typeFaceHorarioFont);
                    textViewHoraSalida.setLayoutParams(tableRowLayoutParams);

                    // Hora llegada
                    TextView textViewHoraLlegada = new TextView(
                            HorarioCercaniasActivity.this);
                    textViewHoraLlegada.setText(horarioCercanias
                            .getHoraLlegada());
                    textViewHoraLlegada.setTextSize(15);
                    textViewHoraLlegada.setTextColor(Color.YELLOW);
                    textViewHoraLlegada.setPadding(3, 3, 3, 3);
                    textViewHoraLlegada.setGravity(Gravity.CENTER_HORIZONTAL);
                    textViewHoraLlegada.setTypeface(typeFaceHorarioFont);
                    textViewHoraLlegada.setLayoutParams(tableRowLayoutParams);

                    // Hora Duracion
                    TextView textViewDuracion = new TextView(
                            HorarioCercaniasActivity.this);
                    textViewDuracion.setText(horarioCercanias.getDuracion());
                    textViewDuracion.setTextSize(15);
                    textViewDuracion.setTextColor(Color.YELLOW);
                    textViewDuracion.setPadding(3, 3, 3, 3);
                    textViewDuracion.setGravity(Gravity.CENTER_HORIZONTAL);
                    textViewDuracion.setTypeface(typeFaceHorarioFont);
                    textViewDuracion.setLayoutParams(tableRowLayoutParams);

                    // Linea
                    TextView textViewLinea = new TextView(
                            HorarioCercaniasActivity.this);
                    textViewLinea.setText(horarioCercanias.getLinea());
                    textViewLinea.setTextSize(15);
                    textViewLinea.setTextColor(Color.YELLOW);
                    textViewLinea.setPadding(3, 3, 3, 3);
                    textViewLinea.setGravity(Gravity.CENTER_HORIZONTAL);
                    textViewLinea.setTypeface(typeFaceHorarioFont);
                    textViewLinea.setLayoutParams(tableRowLayoutParams);

                    // Transbordo
                    if (!horarioCercanias.getTransbordo().isEmpty()) {

                        for (TransbordoCercanias transbordoCercanias : horarioCercanias
                                .getTransbordo()) {

                            // Nombre de la estación para el transbordo.
                            String estacionNombre = transbordoCercanias
                                    .getEnlace();

                            // concatenando todo
                            String nuevoTexto = estacionNombre + " (Línea "
                                    + transbordoCercanias.getLinea() + ")";

                            textViewInfoTransbordo.setVisibility(0);
                            textViewInfoTransbordo.setText("Transbordo en:\n"
                                    + nuevoTexto);
                        }
                    } else {
                        textViewInfoTransbordo.setHeight(1);
                    }

                    tableRowHorario.addView(textViewHoraSalida);
                    tableRowHorario.addView(textViewHoraLlegada);
                    tableRowHorario.addView(textViewDuracion);
                    tableRowHorario.addView(textViewLinea);

                    mTableLayoutHorarios.addView(tableRowHorario);

                    i++;

                }

                break;
            case HorariosCercaniasHandler.TASK_ERROR:
                // Recibe un error y lo muestra al usuario.
                // TODO: Ha de seleccionar otra fecha.
                Toast.makeText(getApplicationContext(), msg.obj.toString(),
                        Toast.LENGTH_SHORT).show();
                break;
            }

        }

        /**
         * Comprueba si la hora de salida es posterior a la especificada.
         * 
         * @param horarioCercanias
         * @return true si es posterior.
         */
        public Boolean checkCorrectInitialDate(HorarioCercanias horarioCercanias) {

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");

            Date dateSalida;
            String horaSalida = horarioCercanias.getHoraSalida();
            try {
                dateSalida = dateFormat.parse(mYear + "-" + mMonth + "-" + mDay
                        + " " + horaSalida);

                // Si la fecha inicial es mayor que la respuesta,
                // continua la siguiente iteración.
                if (mInitialDate.compareTo(dateSalida) > 0) {
                    return false;
                }
            } catch (ParseException e) {
                Log.e(TAG,
                        "Error converting base data, result could have worng start date.");
            }

            return true;
        }
    };

}
