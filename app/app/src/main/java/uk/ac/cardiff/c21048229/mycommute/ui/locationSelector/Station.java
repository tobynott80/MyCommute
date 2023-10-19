package uk.ac.cardiff.c21048229.mycommute.ui.locationSelector;

public class Station {
    private String StationName;
    private String StationCRS;

    public Station(String stationName, String stationCRS) {
        StationName = stationName;
        StationCRS = stationCRS;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getStationCRS() {
        return StationCRS;
    }

    public void setStationCRS(String stationCRS) {
        StationCRS = stationCRS;
    }
}
