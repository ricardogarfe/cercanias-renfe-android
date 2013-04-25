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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

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

import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONEstacionCercaniasParser;

public class EstacionesNucleoViajeActivity extends Activity {

    private Button verHorariosButton;
    private Spinner origenSpinner;
    private Spinner destinoSpinner;

    private Spinner day;
    private Spinner month;
    private Spinner year;

    private SharedPreferences mPreferences;

    private int estacionOrigenId = 0;
    private int estacionDestinoId = 0;

    private int estacionOrigenIdToSet = 0;
    private int estacionDestinoIdToSet = 0;

    boolean can_change = false;

    private String TAG = getClass().getSimpleName();

    // Nucleo values
    private int codigoNucleo;
    private String descripcionNucleo;
    private String estacionesJSON;

    // Seguramente esto vaya mucho mejor en un fichero a parte
    private JSONEstacionCercaniasParser jsonEstacionCercaniasParser;
    private List<EstacionCercanias> estacionCercaniasList;

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
                if (estacionOrigenId == estacionDestinoId) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Las estaciones de origen y destino no pueden ser iguales",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(),
                            HorarioCercaniasActivity.class);
                    intent.putExtra("estacionOrigenId", estacionOrigenId);
                    intent.putExtra("estacionDestinoId", estacionDestinoId);

                    int origenSpinnerPosition = origenSpinner
                            .getSelectedItemPosition();
                    String estacionOrigenName = estacionCercaniasList.get(
                            origenSpinnerPosition).getDescripcion();

                    int destinoSpinnerPosition = destinoSpinner
                            .getSelectedItemPosition();
                    String estacionDestinoName = estacionCercaniasList.get(
                            destinoSpinnerPosition).getDescripcion();

                    // Configure day TODO: select day instance.
                    Calendar c = Calendar.getInstance();

                    DecimalFormat mFormat = new DecimalFormat("00");
                    mFormat.setRoundingMode(RoundingMode.DOWN);

                    int dayInt = c.get(Calendar.DATE);
                    String current_day = mFormat.format(Double.valueOf(dayInt));

                    int monthInt = c.get(Calendar.MONTH);
                    String current_month = mFormat.format(Double
                            .valueOf(monthInt));

                    String current_year2 = Integer.toString(c
                            .get(Calendar.YEAR));

                    intent.putExtra("day", current_day);
                    intent.putExtra("month", current_month);
                    intent.putExtra("year", current_year2);

                    intent.putExtra("nucleoId", codigoNucleo);
                    intent.putExtra("estacionOrigenName", estacionOrigenName);
                    intent.putExtra("estacionDestinoName", estacionDestinoName);

                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putInt("estacionOrigenIdToSet",
                            estacionOrigenIdToSet);
                    editor.putInt("estacionDestinoIdToSet",
                            estacionDestinoIdToSet);
                    editor.commit();

                    startActivity(intent);

                }
            }
        });

        // Set listeners for each spinner.
        origenSpinner.setOnItemSelectedListener(estacionOrigenSelectedListener);
        destinoSpinner
                .setOnItemSelectedListener(estacionDestionSelectedListener);

        // Comprobar si existen en sharedPreferences estaciones seleccionadas.
        boolean station1_set = mPreferences.contains("estacionOrigenIdToSet");
        boolean station2_set = mPreferences.contains("estacionDestinoIdToSet");

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

        estacionOrigenId = estacionCercaniasList.get(0).getCodigo();
        estacionDestinoId = estacionCercaniasList.get(0).getCodigo();

    }

    private OnItemSelectedListener estacionOrigenSelectedListener = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position,
                long id) {

            if (can_change) {
                origenSpinner.setSelection(mPreferences.getInt("station1", 0));
            }

            if (origenSpinner.getSelectedItemPosition() >= 0) {
                int pos = origenSpinner.getSelectedItemPosition();
                estacionOrigenIdToSet = pos;
                estacionOrigenId = estacionCercaniasList.get(position)
                        .getCodigo();
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
                estacionDestinoIdToSet = pos;
                estacionDestinoId = estacionCercaniasList.get(position)
                        .getCodigo();
            }
        }

        public void onNothingSelected(AdapterView arg0) {
        }
    };

}
