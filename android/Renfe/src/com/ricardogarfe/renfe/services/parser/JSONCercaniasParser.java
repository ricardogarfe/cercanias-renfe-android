/**
 * 
 */
package com.ricardogarfe.renfe.services.parser;

import com.google.android.maps.GeoPoint;

/**
 * 
 * JSON Parser for Cercanias data superclass.
 * 
 * @author ricardo
 */
public class JSONCercaniasParser {

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
}
