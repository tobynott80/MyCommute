package uk.ac.cardiff.c21048228.mycommute.retrofit;

import java.util.ArrayList;

public class SearchModel {

    public class Destination{
        public String name;
        public String crs;
        public String tiploc;
        public String country;
        public String system;
        public String description;
        public String workingTime;
        public String publicTime;
    }

    public class Filter{
        public Destination destination;
    }

    public class Location{
        public String name;
        public String crs;
        public String tiploc;
        public String country;
        public String system;
    }

    public class LocationDetail{
        public boolean realtimeActivated;
        public String tiploc;
        public String crs;
        public String description;
        public String gbttBookedArrival;
        public String gbttBookedDeparture;
        public ArrayList<Origin> origin;
        public ArrayList<Destination> destination;
        public boolean isCall;
        public boolean isPublicCall;
        public String realtimeArrival;
        public boolean realtimeArrivalActual;
        public String realtimeDeparture;
        public boolean realtimeDepartureActual;
        public String cancelReasonCode;
        public String cancelReasonShortText;
        public String cancelReasonLongText;
        public String displayAs;
    }

    public class Origin{
        public String tiploc;
        public String description;
        public String workingTime;
        public String publicTime;
    }

    public class Root{
        public Location location;
        public Filter filter;
        public ArrayList<Service> services;
    }

    public class Service{
        public LocationDetail locationDetail;
        public String serviceUid;
        public String runDate;
        public String trainIdentity;
        public String runningIdentity;
        public String atocCode;
        public String atocName;
        public String serviceType;
        public boolean isPassenger;
        public ArrayList<Origin> origin;
        public ArrayList<Destination> destination;
    }

}
