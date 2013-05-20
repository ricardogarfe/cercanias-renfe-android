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
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.ricardogarfe.renfe.R;
import com.ricardogarfe.renfe.maps.OnSingleTapListener;
import com.ricardogarfe.renfe.maps.overlay.CercaniasMyLocationOverlay;
import com.ricardogarfe.renfe.maps.overlay.SimpleItemizedOverlay;
import com.ricardogarfe.renfe.model.EstacionCercanias;
import com.ricardogarfe.renfe.model.LineaCercanias;
import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.service.ILocService;
import com.ricardogarfe.renfe.service.LocService;

public class MapTabView extends MapActivity implements ILocService {

    private TapControlledMapView mapView;
    private MapController mapController;

    private LineaCercanias mLineaCercanias;

    private NucleoCercanias mNucleoCercanias;

    public static final double FIT_FACTOR = 1.5;

    private String mLineaFileName;

    private TextView textViewLocation;

    private CercaniasMyLocationOverlay myLocationOverlay;

    private ObjectMapper objectMapper;

    private String TAG = getClass().getSimpleName();

    Drawable drawable;
    SimpleItemizedOverlay itemizedOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_tab_view);

        // Obtener mapView para configurar sus valores.
        mapView = (TapControlledMapView) findViewById(R.id.mapView);

        // Aplicar Zoom y clickable
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);

        // dismiss balloon upon single tap of MapView (iOS behavior)
        mapView.setOnSingleTapListener(new OnSingleTapListener() {

            public boolean onSingleTap(MotionEvent e) {
                itemizedOverlay.hideAllBalloons();
                return false;
            }
        });

        // Map controller para interactuar con la vista.
        mapController = mapView.getController();

        startService(new Intent(this, LocService.class));
        LocService.registerListener((ILocService) MapTabView.this);

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

    /**
     * Update map with new content.
     */
    private void refreshMap() {

        myLocationOverlay = new CercaniasMyLocationOverlay(this, mapView);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableCompass();

        FileInputStream lineaFileInputStream;
        objectMapper = new ObjectMapper();

        try {
            lineaFileInputStream = openFileInput(mLineaFileName);

            mLineaCercanias = objectMapper.readValue(lineaFileInputStream,
                    LineaCercanias.class);
        } catch (Exception e) {
            Log.e(TAG, "JSON lineaCercanias error reading file:\n"
                    + e.getStackTrace().toString());
        }

        // TexViewLocation
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewLocation.setText(mLineaCercanias.getDescripcion() + " - "
                + mLineaCercanias.getCodigo());

        if (mLineaCercanias == null) {
            Toast.makeText(getBaseContext(),
                    getString(R.string.no_location_values), Toast.LENGTH_LONG)
                    .show();

            return;
        }

        final List<Overlay> lineaOverlays = mapView.getOverlays();
        lineaOverlays.clear();

        drawable = getResources().getDrawable(R.drawable.icon);
        drawable.setBounds(0, 0, 50, 50);

        // Balloon test
        OverlayItem overlayItem;
        // first overlay
        itemizedOverlay = new SimpleItemizedOverlay(drawable, mapView);
        // set iOS behavior attributes for overlay
        itemizedOverlay.setShowClose(true);
        itemizedOverlay.setShowDisclosure(true);
        itemizedOverlay.setSnapToCenter(true);

        int minLat = Integer.MAX_VALUE;
        int maxLat = Integer.MIN_VALUE;
        int minLon = Integer.MAX_VALUE;
        int maxLon = Integer.MIN_VALUE;

        GeoPoint geoPoint;

        for (EstacionCercanias estacionCercanias : mLineaCercanias
                .getEstacionCercaniasList()) {

            geoPoint = retrieveGeoPoint(estacionCercanias.getLatitude(),
                    estacionCercanias.getLongitude());

            int lat = geoPoint.getLatitudeE6();
            int lon = geoPoint.getLongitudeE6();

            maxLat = Math.max(lat, maxLat);
            minLat = Math.min(lat, minLat);
            maxLon = Math.max(lon, maxLon);
            minLon = Math.min(lon, minLon);

            overlayItem = new OverlayItem(geoPoint,
                    estacionCercanias.getDescripcion(),
                    estacionCercanias.getZona());

            itemizedOverlay.addOverlay(overlayItem);

            lineaOverlays.add(itemizedOverlay);

        }

        lineaOverlays.add(myLocationOverlay);

        mapController.zoomToSpan(
                (int) (Math.abs(maxLat - minLat) * FIT_FACTOR),
                (int) (Math.abs(maxLon - minLon) * FIT_FACTOR));

        mapController.animateTo(new GeoPoint((maxLat + minLat) / 2,
                (maxLon + minLon) / 2));

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
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        myLocationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        myLocationOverlay.disableMyLocation();
        super.onPause();
    }

    /**
     * This method zooms to the user's location with a zoom level of 10.
     */
    private void zoomToMyLocation() {
        GeoPoint myLocationGeoPoint = myLocationOverlay.getMyLocation();
        if (myLocationGeoPoint != null) {
            mapView.getController().animateTo(myLocationGeoPoint);
            mapView.getController().setZoom(5);
        } else {
            Toast.makeText(this, "Cannot determine location",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        stopService(new Intent(this, LocService.class));
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

    public void updateLocation(Location loc) {
        // TODO Auto-generated method stub
        Log.d(TAG, "Location Updated:\n" + loc.toString());

    }
}
