package edu.uwp.cs.csci340.A05.GTFH;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;


/**
 * The {@code AirportDistanceCalculator} class provides geolocation and distance calculation utilities
 * for airports and cities using the OpenCage Geocoder API. It supports geocoding a city name to
 * latitude/longitude coordinates and computing the great-circle distance (in kilometers) between
 * two geographic points using the Haversine formula.
 *
 * <p>This class relies on the {@code JOpenCageGeocoder} library and a valid API key.</p>
 *
 * Note: Longitude is returned as an absolute value to avoid negative coordinates in this context.
 *
 * Dependencies:
 * <ul>
 *   <li>JOpenCageGeocoder</li>
 *   <li>JOpenCageForwardRequest</li>
 *   <li>JOpenCageResponse</li>
 *   <li>JOpenCageLatLng</li>
 * </ul>
 *
 */
public class AirportDistanceCalculator {
    JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("399ce141d14f4b44ae7b866793e95462");

    /**
     * Converts a city name into geographic coordinates (latitude and longitude).
     *
     * @param cityName the name of the city (e.g., "San Francisco")
     * @return a double array of size 2 where index 0 is latitude and index 1 is longitude (absolute value)
     */
    public double[] geocodeCity(String cityName){
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(cityName);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();
        double[] coords = new double[2];
        coords[0] = firstResultLatLng.getLat();
        coords[1] = Math.abs(firstResultLatLng.getLng());
        return coords;
    }

    /**
     * Calculates the great-circle distance between two geographic points using the Haversine formula.
     *
     * @param start a double array representing [latitude, longitude] of the start point
     * @param end   a double array representing [latitude, longitude] of the end point
     * @return the distance in kilometers between the two points
     */
    public double getDistance(double[] start, double[] end){
        double lat1 = start[0];
        double lat2 = end[0];
        double lon1 = start[1];
        double lon2 = end[1];
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);

        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;
        return (c * r);
    }

    /**
     * Converts degrees to radians.
     *
     * @param deg the angle in degrees
     * @return the angle in radians
     */
    public double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}
