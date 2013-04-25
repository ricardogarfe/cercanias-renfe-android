/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 * 
 * Jon Segador <jonseg@gmail.com>
 * 
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.ricardogarfe.renfe;

import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.jonsegador.Renfe.ParsedHorarioDataSet;
import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.R.id;
import com.ricardogarfe.renfe.R.layout;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.HorarioCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.model.TransbordoCercanias;
import com.ricardogarfe.renfe.services.handler.HorariosCercaniasHandler;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

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

    private List<HorarioCercanias> mHorarioCercaniasList;

    // URL to make requests.
    private final static String URL = "http://horarios.renfe.com/cer/horarios/horarios.jsp";

    private String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);

        tableLayoutHorarios = (TableLayout) findViewById(R.id.tableLayoutHorarios);

        final int nucleoId = getIntent().getIntExtra("nucleoId", 0);
        final int estacionOrigenId = getIntent().getIntExtra(
                "estacionOrigenId", 0);
        final int estacionDestinoId = getIntent().getIntExtra(
                "estacionDestinoId", 0);

        final String estacionOrigenName = getIntent().getStringExtra(
                "estacionOrigenName");
        final String estacionDestinoName = getIntent().getStringExtra(
                "estacionDestinoName");

        textViewInfoStations = (TextView) findViewById(R.id.textViewInfoStations);
        textViewInfoDate = (TextView) findViewById(R.id.textViewInfoDate);
        textViewInfoTransbordo = (TextView) findViewById(R.id.textViewInfoTransbordo);

        day = getIntent().getStringExtra("day");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");

        StringBuffer stringBufferDf = new StringBuffer();
        stringBufferDf.append(year);
        stringBufferDf.append(month);
        stringBufferDf.append(day);

        fulldate = stringBufferDf.toString();

        Button change_btn = (Button) findViewById(R.id.buttonVuelta);
        change_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        HorarioCercaniasActivity.class);
                intent.putExtra("nucleoId", nucleoId);
                intent.putExtra("estacionOrigenId", estacionDestinoId);
                intent.putExtra("estacionDestinoId", estacionOrigenId);

                intent.putExtra("estacionOrigenName", estacionDestinoName);
                intent.putExtra("estacionDestinoName", estacionOrigenName);

                intent.putExtra("day", day);
                intent.putExtra("month", month);
                intent.putExtra("year", year);

                startActivity(intent);

            }
        });

        textViewInfoStations.setText(estacionOrigenName + " - " + estacionDestinoName);
        textViewInfoDate.setText(day + "/" + month + "/" + year);

        fulldate = year + month + day;

        showDialog(0);
        t = new Thread() {
            public void run() {
                loadSchedule(nucleoId, estacionOrigenId, estacionDestinoId,
                        fulldate);
            }
        };
        t.start();

    }

    /**
     * Retreive train trip schedules from URL and selected parameters using XML
     * Parser {@link HorariosCercaniasHandler} to convert result to a Java
     * {@link HorarioCercanias} List and send correct or error message to
     * handler.
     * 
     * @param nucleoId
     *            Nucleo ID from Cercanias City.
     * @param estacionOrigenId
     *            Estacion ID to start the trip.
     * @param estacionDestinoId
     *            Estacion ID to end the trip.
     * @param fulldate
     *            Date in String using 'YYYYMMDD' format.
     */
    public void loadSchedule(int nucleoId, int estacionOrigenId, int estacionDestinoId,
            String fulldate) {

        Message myMessage = new Message();
        try {
            // nucleo=40&d=65003&df=20130306&hd=24&ho=07&o=64105
            URL url = new URL(URL + "?nucleo=" + nucleoId + "&o="
                    + estacionOrigenId + "&d=" + estacionDestinoId + "&df=" + fulldate
                    + "&hd=24&ho=07");

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            XMLReader xr = sp.getXMLReader();
            HorariosCercaniasHandler horariosCercaniasHandler = new HorariosCercaniasHandler();
            xr.setContentHandler(horariosCercaniasHandler);

            xr.parse(new InputSource(url.openStream()));

            mHorarioCercaniasList = horariosCercaniasHandler
                    .getHorarioCercaniasList();

            // Send result message
            if (mHorarioCercaniasList != null
                    && !mHorarioCercaniasList.isEmpty()) {

                myMessage.what = HorariosCercaniasHandler.TASK_COMPLETE;
                myMessage.obj = "SUCCESS";
            } else {
                myMessage.what = HorariosCercaniasHandler.TASK_ERROR;
                myMessage.obj = "No se han encontrado Horarios Cercanias.";
            }
            messageHorariosCercaniasHandler.sendMessage(myMessage);

        } catch (Exception e) {
            Log.e(TAG, "Error retrieving schedule in Nucleo '" + nucleoId
                    + "', from " + estacionOrigenId + " to " + estacionDestinoId + ":\t"
                    + e.getMessage());
            myMessage.obj = "Error obteniendo los horarios de cercanías.";
        }

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

            String objectMessage = (String) msg.obj;

            switch (msg.what) {
            case HorariosCercaniasHandler.TASK_COMPLETE:

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
                Toast.makeText(getApplicationContext(), objectMessage,
                        Toast.LENGTH_SHORT).show();
                break;
            }
            // Remove loading dialog (all dialogs).
            removeDialog(0);

        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case 0: {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Cargando...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            return dialog;
        }
        }
        return null;
    }

}
