package uk.ac.cardiff.c21048228.mycommute.ui.home;

import java.util.ArrayList;

import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainService;

public class Commute {
    String Arrival;
    String Destination;
    ArrayList<TrainService> services;
    Boolean isValid;

    public Commute(String arrival, String destination, ArrayList<TrainService> services, Boolean isValid) {
        Arrival = arrival;
        Destination = destination;
        this.services = services;
        this.isValid = isValid;
    }
}
