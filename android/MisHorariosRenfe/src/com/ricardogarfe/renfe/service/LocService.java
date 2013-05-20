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
 * 
 * Copyright (C) Roberto Calvo Palomino
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 * 
 * Author : Roberto Calvo Palomino <rocapal at gmail dot com>
 */

package com.ricardogarfe.renfe.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocService extends Service implements LocationListener {

    private final String TAG = getClass().getSimpleName();

    private final String LocProvider = LocationManager.GPS_PROVIDER;
    private static Location mCurrentLocation = null;
    public static Location lastKnownLocation = null;
    private static LocationManager mLocationManager;

    // Location Periodic in seconds
    private final Integer LocationPeriodic = 300;
    // Minimum distance of gps refresh
    private final Integer MinimumDistance = 500;

    private static ILocService mILocService;
    private NotificationManager mNM;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        startService();

    }

    public static void registerListener(ILocService i) {
        mILocService = i;
    }

    public static void unRegisterListener() {
        mILocService = null;
    }

    public static Location getLastLocation() {
        return mCurrentLocation;
    }

    private void startService() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocProvider, LocationPeriodic,
                MinimumDistance, this);

    }

    private void notifyLocation() {
        // The app is visible and enable
        if (mILocService != null) {
            mILocService.updateLocation(mCurrentLocation);
        }
    }

    private void showNotification() {

    }

    public void onLocationChanged(Location loc) {

        if (loc != null) {

            // Save the current location
            mCurrentLocation = loc;
            Log.d(TAG, String.valueOf(mCurrentLocation.getLatitude()) + " "
                    + String.valueOf(mCurrentLocation.getLongitude()));

            notifyLocation();

        }
    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

}
