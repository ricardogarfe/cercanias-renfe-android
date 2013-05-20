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

package com.ricardogarfe.renfe.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.maps.overlay.MapOverlay;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.LineaCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.service.ILocService;

public class MapTabView extends MapActivity implements ILocService {

    private MapView mapView;
    private MapController mapController;

    private LineaCercanias mLineaCercanias;

    private NucleoCercanias mNucleoCercanias;
    private String mLineaFileName;

    private TextView textViewLocation;

    private ObjectMapper objectMapper;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_tab_view);

        // Obtener mapView para configurar sus valores.
        mapView = (MapView) findViewById(R.id.mapView);

        // TexViewLocation
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);

        // Aplicar Zoom y clickable
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);

        // Map controller para interactuar con la vista.
        mapController = mapView.getController();

        initializeValuesFromIntent();

        refreshMap();

    }

    /**
     * Inicializar valores para mostrar en el mapa.
     */
    private void initializeValuesFromIntent() {
        // Retrieve value from nucleos activity.
        Intent intentFromActivity = getIntent();

        if (intentFromActivity != null) {

            mLineaFileName = intentFromActivity.getStringExtra("lineaFileName");
            mNucleoCercanias = intentFromActivity
                    .getParcelableExtra("nucleoCercanias");

        }

    }

    private void refreshMap() {

        FileInputStream lineaFileInputStream;
        objectMapper = new ObjectMapper();

        try {
            lineaFileInputStream = openFileInput(mLineaFileName);

            mLineaCercanias = objectMapper.readValue(lineaFileInputStream,
                    LineaCercanias.class);
        } catch (JsonParseException e) {
            Log.e(TAG, "JSON lineaCercanias error reading file:\n"
                    + e.getStackTrace().toString());
        } catch (JsonMappingException e) {
            Log.e(TAG, "JSON lineaCercanias error reading file:\n"
                    + e.getStackTrace().toString());
        } catch (IOException e) {
            Log.e(TAG, "JSON lineaCercanias error reading file:\n"
                    + e.getStackTrace().toString());
        }

        if (mLineaCercanias == null) {
            Toast.makeText(getBaseContext(),
                    getString(R.string.no_location_values), Toast.LENGTH_LONG)
                    .show();

            return;
        }

        final List<Overlay> lineaOverlays = mapView.getOverlays();
        lineaOverlays.clear();

        MapOverlay estacionMapOverlay;

        for (EstacionCercanias estacionCercanias : mLineaCercanias
                .getEstacionCercaniasList()) {

            if (estacionCercanias == null) {
                continue;
            }

            Log.d(TAG, "Estación:\t" + estacionCercanias.getDescripcion());

            estacionMapOverlay = new MapOverlay();
            Drawable drawable = getResources().getDrawable(R.drawable.marker);
            drawable.setBounds(0, 0, 50, 50);

            estacionMapOverlay.setDrawable(drawable);
            estacionMapOverlay.setGeoPoint(retrieveGeoPoint(
                    estacionCercanias.getLatitude(),
                    estacionCercanias.getLongitude()));
            estacionMapOverlay
                    .setTextToShow(estacionCercanias.getDescripcion());

            lineaOverlays.add(estacionMapOverlay);

        }

        mapController.setZoom(18);

        mapView.setBuiltInZoomControls(true);

        mapView.setClickable(true);

        textViewLocation.setText(mLineaCercanias.getCodigo() + " - "
                + mLineaCercanias.getDescripcion());
    }

    /**
     * Retrieve {@link GeoPoint} object from latitude and longitude.
     * 
     * @param latitude
     *            Latitude values greater than -90 and less than 90
     * @param longitude
     *            Latitude values greater than -80 and less than 180.
     * 
     * @throws IllegalArgumentException
     *             if latitude is less than -90 or greater than 90
     * @throws IllegalArgumentException
     *             if longitude is less than -180 or greater than 180
     * @return {@link GeoPoint} object complete.
     */
    public GeoPoint retrieveGeoPoint(double latitude, double longitude) {
        return new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

    public void updateLocation(Location loc) {
        // TODO Auto-generated method stub

    }

}
