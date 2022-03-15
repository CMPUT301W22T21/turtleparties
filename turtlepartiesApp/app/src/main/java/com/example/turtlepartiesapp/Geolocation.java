package com.example.turtlepartiesapp;
// Geolocation class used with QR codes. No outstanding issues
public class Geolocation {
    private double lat;
    private double lon;

    /**
     * This class will record the geolocation for the map
     * @param lat
     * Double storing latiutude
     * @param lon
     * Double storing longitude
     */
    public Geolocation(double lat, double lon) {

        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Getter for latitude
     * @return
     * Double as latutude
     */
    public Double getLat() {
        return lat;
    }

    /**
     * Getter for longiute
     * @return
     * Longitude as double
     */
    public Double getLon() {
        return lon;
    }

    /**
     * Setter for latitude
     * @param lat
     * Takes double as latudyude
     * No reutrn
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * Setter for longituide
     * @param lon
     * Takes double as longiutyude
     * No return
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }
}
