package es.upm.flights.models;

import java.util.List;

public class ResponseData {
    private List<Airport> airports;

    public ResponseData(List<Airport> airports) {
        this.airports = airports;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}
