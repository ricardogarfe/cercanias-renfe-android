/*
 * Copyright [2013] [Ricardo García Fernández] [ricarodgarfe@gmail.com]
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardogarfe.renfe.asyncTask.HorarioCercaniasTask;
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

    private String fulldate;

    private TableLayout tableLayoutHorarios;

    private String day;
    private String month;
    private String year;

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

        tableLayoutHorarios = (TableLayout) findViewById(R.id.tableLayoutHorarios);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        mHorarioCercaniasContext = this;

        mDatosPeticionHorarioCercanias = retrieveDatosPeticonHorariosCercanias();

        textViewNucleo = (TextView) findViewById(R.id.textViewNucleo);
        textViewInfoStations = (TextView) findViewById(R.id.textViewInfoStations);
        textViewInfoDate = (TextView) findViewById(R.id.textViewInfoDate);
        textViewInfoTransbordo = (TextView) findViewById(R.id.textViewInfoTransbordo);

        Button buttonVuelta = (Button) findViewById(R.id.buttonVuelta);
        buttonVuelta.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        HorarioCercaniasActivity.class);
                intent.putExtra("nucleoId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getNucleo()));
                intent.putExtra("nucleoName",
                        mDatosPeticionHorarioCercanias.getNucleoName());

                intent.putExtra("estacionOrigenId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getDestino()));
                intent.putExtra("estacionDestinoId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getOrigen()));

                intent.putExtra("estacionOrigenName",
                        mDatosPeticionHorarioCercanias.getEstacionDestinoName());
                intent.putExtra("estacionDestinoName",
                        mDatosPeticionHorarioCercanias.getEstacionOrigenName());

                intent.putExtra("day", day);
                intent.putExtra("month", month);
                intent.putExtra("year", year);

                startActivity(intent);

            }
        });

        textViewInfoStations.setText(mDatosPeticionHorarioCercanias
                .getEstacionOrigenName()
                + " - "
                + mDatosPeticionHorarioCercanias.getEstacionDestinoName());
        textViewInfoDate.setText(day + "/" + month + "/" + year);

        textViewNucleo.setText(mDatosPeticionHorarioCercanias.getNucleoName());

        mHorarioCercaniasTask = new HorarioCercaniasTask();
        mHorarioCercaniasTask.execute(mDatosPeticionHorarioCercanias);
        mHorarioCercaniasTask
                .setMessageNucleoCercaniasHandler(messageHorariosCercaniasHandler);
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
        day = getIntent().getStringExtra("day");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");

        StringBuffer stringBufferDf = new StringBuffer();
        stringBufferDf.append(year);
        stringBufferDf.append(month);
        stringBufferDf.append(day);

        fulldate = stringBufferDf.toString();

        // Create object
        datosPeticionHorarioCercanias.setNucleo(Integer.toString(nucleoId));
        datosPeticionHorarioCercanias.setNucleoName(nucleoName);
        datosPeticionHorarioCercanias.setOrigen(Integer
                .toString(estacionOrigenId));
        datosPeticionHorarioCercanias.setDestino(Integer
                .toString(estacionDestinoId));
        datosPeticionHorarioCercanias.setFechaViaje(fulldate);
        datosPeticionHorarioCercanias.setEstacionOrigenName(estacionOrigenName);
        datosPeticionHorarioCercanias
                .setEstacionDestinoName(estacionDestinoName);

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

                    tableLayoutHorarios.addView(tableRowHorario);

                    i++;

                }

                break;
            case HorariosCercaniasHandler.TASK_ERROR:
                // Recibe un error y lo muestra al usuario.
                Toast.makeText(getApplicationContext(), msg.obj.toString(),
                        Toast.LENGTH_SHORT).show();
                break;
            }

        }
    };

}
