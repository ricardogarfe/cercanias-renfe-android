/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 * 
 * Jon Segador <jonseg@gmail.com>
 * 
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.ricardogarfe.renfe;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jonsegador.Renfe.HorariosActivity;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONCercaniasParser;
import com.ricardogarfe.renfe.services.parser.JSONEstacionCercaniasParser;

public class EstacionesNucleoViajeActivity extends Activity {

    private Button verHorariosButton;
    private Spinner origenSpinner;
    private Spinner destinoSpinner;

    private Spinner day;
    private Spinner month;
    private Spinner year;

    private SharedPreferences mPreferences;

    private int station1_id = 0;
    private int station2_id = 0;

    private int station1_id_to_set = 0;
    private int station2_id_to_set = 0;

    boolean can_change = false;

    private String TAG = getClass().getSimpleName();

    // Nucleo values
    private int codigoNucleo;
    private String descripcionNucleo;
    private String estacionesJSON;

    // Seguramente esto vaya mucho mejor en un fichero a parte
    private JSONEstacionCercaniasParser jsonEstacionCercaniasParser;
    private List<EstacionCercanias> estacionCercaniasList;

    private String[][] days = { { "01", "1" }, { "02", "2" }, { "03", "3" },
            { "04", "4" }, { "05", "5" }, { "06", "6" }, { "07", "7" },
            { "08", "8" }, { "09", "9" }, { "10", "10" }, { "11", "11" },
            { "12", "12" }, { "13", "13" }, { "14", "14" }, { "15", "15" },
            { "16", "16" }, { "17", "17" }, { "18", "18" }, { "19", "19" },
            { "20", "20" }, { "21", "21" }, { "22", "22" }, { "23", "23" },
            { "24", "24" }, { "25", "25" }, { "26", "26" }, { "27", "27" },
            { "28", "28" }, { "29", "29" }, { "30", "30" }, { "31", "31" } };

    private String[][] months = { { "01", "Enero" }, { "02", "Febrero" },
            { "03", "Marzo" }, { "04", "Abril" }, { "05", "Mayo" },
            { "06", "Junio" }, { "07", "Julio" }, { "08", "Agosto" },
            { "09", "Septiembre" }, { "10", "Octubre" }, { "11", "Noviembre" },
            { "12", "Diciembre" } };

    private String[][] years = { { "2012", "2012" }, { "2013", "2013" },
            { "2014", "2014" }, { "2015", "2015" } };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.estacion_nucleo_viaje);

        mPreferences = getSharedPreferences("Renfe", MODE_PRIVATE);

        origenSpinner = (Spinner) this.findViewById(R.id.origenSpinner);
        destinoSpinner = (Spinner) this.findViewById(R.id.destinoSpinner);

        configureEstacionesPorNucleo();

        verHorariosButton = (Button) findViewById(R.id.verHorariosButton);
        verHorariosButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                // Errores
                if (station1_id == station2_id) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Las estaciones de origen y destino no pueden ser iguales",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(),
                            HorariosActivity.class);
                    intent.putExtra("station1_id", station1_id);
                    intent.putExtra("station2_id", station2_id);

                    int origenSpinnerPosition = origenSpinner
                            .getSelectedItemPosition();
                    String station1_name = estacionCercaniasList.get(
                            origenSpinnerPosition).getDescripcion();

                    int destinoSpinnerPosition = destinoSpinner
                            .getSelectedItemPosition();
                    String station2_name = estacionCercaniasList.get(
                            destinoSpinnerPosition).getDescripcion();

                    // Configure day TODO: select day instance.
                    Calendar c = Calendar.getInstance();

                    String current_day = Integer.toString(c.get(Calendar.DATE));
                    String current_month = Integer.toString(c
                            .get(Calendar.MONTH));
                    String current_year2 = Integer.toString(c
                            .get(Calendar.YEAR));

                    intent.putExtra("day", current_day);
                    intent.putExtra("month", current_month);
                    intent.putExtra("year", current_year2);

                    intent.putExtra("station1_name", station1_name);
                    intent.putExtra("station2_name", station2_name);

                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putInt("station1", station1_id_to_set);
                    editor.putInt("station2", station2_id_to_set);
                    editor.commit();

                    startActivity(intent);

                }
            }
        });

        origenSpinner.setOnItemSelectedListener(estacionOrigenSelectedListener);
        destinoSpinner
                .setOnItemSelectedListener(estacionDestionSelectedListener);

        boolean station1_set = mPreferences.contains("station1");
        boolean station2_set = mPreferences.contains("station2");

        if (station1_set && station2_set) {
            can_change = true;
        }

    }

    /**
     * Configure {@link EstacionCercanias} using {@link NucleoCercanias} intent
     * values to retrieve each station and convert to spinner values.
     */
    public void configureEstacionesPorNucleo() {

        // Retrieve value from nucleos activity.
        Intent intentFromActivity = getIntent();

        TextView textViewNucleo = (TextView) this
                .findViewById(R.id.nucleo_text);

        if (intentFromActivity != null) {

            codigoNucleo = intentFromActivity.getIntExtra("codigo_nucleo", 0);
            descripcionNucleo = intentFromActivity
                    .getStringExtra("descripcion_nucleo");

            if (descripcionNucleo != null) {
                textViewNucleo.setText(descripcionNucleo);
            }
        }

        estacionesJSON = intentFromActivity.getStringExtra("estaciones_json");

        jsonEstacionCercaniasParser = new JSONEstacionCercaniasParser();

        try {
            estacionCercaniasList = jsonEstacionCercaniasParser
                    .retrieveEstacionCercaniasFromJSON(estacionesJSON, false);
        } catch (IOException e) {
            Log.e(TAG,
                    "Error retrieving JSON file from Estaciones Cercanias:\t"
                            + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "Error Parsing JSON file from Estaciones Cercanias:\t"
                    + e.getMessage());
        }

        ArrayAdapter<CharSequence> spinnerEstacionAdapter = new ArrayAdapter<CharSequence>(
                getApplicationContext(), android.R.layout.simple_spinner_item);

        for (EstacionCercanias estacionCercanias : estacionCercaniasList) {
            spinnerEstacionAdapter.add(estacionCercanias.getDescripcion());
        }

        spinnerEstacionAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origenSpinner.setAdapter(spinnerEstacionAdapter);

        destinoSpinner.setAdapter(spinnerEstacionAdapter);

        station1_id = estacionCercaniasList.get(0).getCodigo();
        station2_id = estacionCercaniasList.get(0).getCodigo();

    }

    private OnItemSelectedListener estacionOrigenSelectedListener = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position,
                long id) {

            if (can_change) {
                origenSpinner.setSelection(mPreferences.getInt("station1", 0));
            }

            if (origenSpinner.getSelectedItemPosition() >= 0) {
                int pos = origenSpinner.getSelectedItemPosition();
                station1_id_to_set = pos;
                station1_id = estacionCercaniasList.get(position).getCodigo();
            }
        }

        public void onNothingSelected(AdapterView arg0) {
        }
    };

    private OnItemSelectedListener estacionDestionSelectedListener = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position,
                long id) {

            if (can_change) {
                destinoSpinner.setSelection(mPreferences.getInt("station2", 0));
                can_change = false;
            }

            if (destinoSpinner.getSelectedItemPosition() >= 0) {
                int pos = destinoSpinner.getSelectedItemPosition();
                station2_id_to_set = pos;
                station2_id = estacionCercaniasList.get(position).getCodigo();
            }
        }

        public void onNothingSelected(AdapterView arg0) {
        }
    };

}
