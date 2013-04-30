/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 * 
 * Jon Segador <jonseg@gmail.com>
 * 
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.ricardogarfe.renfe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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

    private Thread t;
    private ProgressDialog dialog;

    private TextView textViewInfoTransbordo;
    private TextView textViewInfoStations;
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

        textViewInfoStations = (TextView) findViewById(R.id.textViewInfoStations);
        textViewInfoDate = (TextView) findViewById(R.id.textViewInfoDate);
        textViewInfoTransbordo = (TextView) findViewById(R.id.textViewInfoTransbordo);
        Button change_btn = (Button) findViewById(R.id.buttonVuelta);
        change_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        HorarioCercaniasActivity.class);
                intent.putExtra("nucleoId", Integer
                        .parseInt(mDatosPeticionHorarioCercanias.getNucleo()));
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

                for (HorarioCercanias horarioCercanias : mHorarioCercaniasList) {

                    TableRow tr = new TableRow(HorarioCercaniasActivity.this);

                    if (i % 2 != 0)
                        tr.setBackgroundColor((Color.argb(0xFF, 0xD9, 0xE2,
                                0xF3)));

                    TextView a = new TextView(HorarioCercaniasActivity.this);
                    a.setText(horarioCercanias.getHoraSalida());
                    a.setTextSize(15);
                    a.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    a.setPadding(3, 3, 5, 3);
                    a.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView b = new TextView(HorarioCercaniasActivity.this);
                    b.setText(horarioCercanias.getHoraLlegada());
                    b.setTextSize(15);
                    b.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    b.setPadding(3, 3, 3, 3);
                    b.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView d = new TextView(HorarioCercaniasActivity.this);
                    d.setText(horarioCercanias.getDuracion());
                    d.setTextSize(15);
                    d.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    d.setPadding(3, 3, 3, 3);
                    d.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView e = new TextView(HorarioCercaniasActivity.this);
                    e.setText(horarioCercanias.getLinea());
                    e.setTextSize(15);
                    e.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    e.setPadding(3, 3, 3, 3);
                    e.setGravity(Gravity.CENTER_HORIZONTAL);

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

                    tr.addView(a);
                    tr.addView(b);
                    tr.addView(d);
                    tr.addView(e);

                    tableLayoutHorarios.addView(tr);

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
