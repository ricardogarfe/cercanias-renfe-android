/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 * 
 * Jon Segador <jonseg@gmail.com>
 * 
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.ricardogarfe.renfe;

import java.util.List;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ricardogarfe.renfe.adapter.NucleoAdapter;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONNucleosCercaniasParser;

public class NucleosActivity extends ListActivity {

    private static String NUCLEOS_JSON = "json/nucleos_cercanias.json";

    private SharedPreferences mPreferences;

    private String TAG = getClass().getSimpleName();

    private NucleoAdapter mNucleoAdapter;

    private List<NucleoCercanias> mNucleoCercaniasList;

    private JSONNucleosCercaniasParser mJSONNucleosCercaniasParser = new JSONNucleosCercaniasParser();

    // Seguramente esto vaya mucho mejor en un fichero a parte

    private String[][] actual_station = { { "0", "Selecciona primero un núcleo" } };
    private String[][] empty_nucleo = { { "0", "Selecciona primero un núcleo" } };

    private String[][] nucleos = { { "0", "Seleccionar núcleo" },
            { "20", "Asturias" }, { "50", "Barcelona" }, { "60", "Bilbao" },
            { "31", "Cádiz" }, { "10", "Madrid" }, { "32", "Málaga" },
            { "41", "Murcia" }, { "62", "Santander" },
            { "61", "San Sebastián" }, { "30", "Sevilla" },
            { "40", "Valencia" }, { "70", "Zaragoza" } };

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

        mJSONNucleosCercaniasParser.setContext(this);

        try {
            loadNucleosCercanias();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Main", e.getMessage());
        }

        setContentView(R.layout.nucleos_main);

        mPreferences = getSharedPreferences("Renfe", MODE_PRIVATE);

        // Asociar el adapter para tratar la información.
        mNucleoAdapter = new NucleoAdapter(this);
        mNucleoAdapter.setmNucleoCercaniasList(mNucleoCercaniasList);
        setListAdapter(mNucleoAdapter);

    }

    /**
     * Retrieve Cercanias City nucleos from JSON file.
     * 
     * @throws Exception
     */
    public void loadNucleosCercanias() throws Exception {

        try {
            
            mNucleoCercaniasList = mJSONNucleosCercaniasParser
                    .retrieveNucleoCercaniasFromJSON(NUCLEOS_JSON);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("MAIN Activity", "Parsing JSON data:\t" + e.getMessage());
        }

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {

        // Create a new intent to call other Activity.
        // Using the methods "putExtra" we can
        // send data to the new activity

        Toast.makeText(this,
                mNucleoCercaniasList.get(position).getDescripcion(),
                Toast.LENGTH_SHORT).show();
    }

}
