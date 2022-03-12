package com.example.turtlepartiesapp;

public class Geolocation {
    private double lat;
    private double lon;

    /**
     * This class will record the geolocation for the map
     * @param lat
     * @param lon
     */
    public Geolocation(double lat, double lon) {

        this.lat = lat;
        this.lon = lon;
    }


    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
