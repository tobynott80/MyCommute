package uk.ac.cardiff.c21048228.mycommute.retrofit;

import java.util.ArrayList;

public class SearchModel {

    public Location location;
    public Filter filter;
    public ArrayList<Service> services;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }


    public class Destination{
        public String name;
        public String crs;
        public String country;
        public String system;
        public String description;
        public String workingTime;
        public String publicTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCrs() {
            return crs;
        }

        public void setCrs(String crs) {
            this.crs = crs;
        }



        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWorkingTime() {
            return workingTime;
        }

        public void setWorkingTime(String workingTime) {
            this.workingTime = workingTime;
        }

        public String getPublicTime() {
            return publicTime;
        }

        public void setPublicTime(String publicTime) {
            this.publicTime = publicTime;
        }
    }

    public class Filter{
        public Destination destination;

        public Destination getDestination() {
            return destination;
        }

        public void setDestination(Destination destination) {
            this.destination = destination;
        }

    }

    public class Location{
        public String name;
        public String crs;
        public String country;
        public String system;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCrs() {
            return crs;
        }

        public void setCrs(String crs) {
            this.crs = crs;
        }


        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }
    }

    public class LocationDetail{
        public boolean realtimeActivated;
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
        public String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public boolean isRealtimeActivated() {
            return realtimeActivated;
        }

        public void setRealtimeActivated(boolean realtimeActivated) {
            this.realtimeActivated = realtimeActivated;
        }


        public String getCrs() {
            return crs;
        }

        public void setCrs(String crs) {
            this.crs = crs;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGbttBookedArrival() {
            return gbttBookedArrival;
        }

        public void setGbttBookedArrival(String gbttBookedArrival) {
            this.gbttBookedArrival = gbttBookedArrival;
        }

        public String getGbttBookedDeparture() {
            return gbttBookedDeparture;
        }

        public void setGbttBookedDeparture(String gbttBookedDeparture) {
            this.gbttBookedDeparture = gbttBookedDeparture;
        }

        public ArrayList<Origin> getOrigin() {
            return origin;
        }

        public void setOrigin(ArrayList<Origin> origin) {
            this.origin = origin;
        }

        public ArrayList<Destination> getDestination() {
            return destination;
        }

        public void setDestination(ArrayList<Destination> destination) {
            this.destination = destination;
        }

        public boolean isCall() {
            return isCall;
        }

        public void setCall(boolean call) {
            isCall = call;
        }

        public boolean isPublicCall() {
            return isPublicCall;
        }

        public void setPublicCall(boolean publicCall) {
            isPublicCall = publicCall;
        }

        public String getRealtimeArrival() {
            return realtimeArrival;
        }

        public void setRealtimeArrival(String realtimeArrival) {
            this.realtimeArrival = realtimeArrival;
        }

        public boolean isRealtimeArrivalActual() {
            return realtimeArrivalActual;
        }

        public void setRealtimeArrivalActual(boolean realtimeArrivalActual) {
            this.realtimeArrivalActual = realtimeArrivalActual;
        }

        public String getRealtimeDeparture() {
            return realtimeDeparture;
        }

        public void setRealtimeDeparture(String realtimeDeparture) {
            this.realtimeDeparture = realtimeDeparture;
        }

        public boolean isRealtimeDepartureActual() {
            return realtimeDepartureActual;
        }

        public void setRealtimeDepartureActual(boolean realtimeDepartureActual) {
            this.realtimeDepartureActual = realtimeDepartureActual;
        }

        public String getCancelReasonCode() {
            return cancelReasonCode;
        }

        public void setCancelReasonCode(String cancelReasonCode) {
            this.cancelReasonCode = cancelReasonCode;
        }

        public String getCancelReasonShortText() {
            return cancelReasonShortText;
        }

        public void setCancelReasonShortText(String cancelReasonShortText) {
            this.cancelReasonShortText = cancelReasonShortText;
        }

        public String getCancelReasonLongText() {
            return cancelReasonLongText;
        }

        public void setCancelReasonLongText(String cancelReasonLongText) {
            this.cancelReasonLongText = cancelReasonLongText;
        }

        public String getDisplayAs() {
            return displayAs;
        }

        public void setDisplayAs(String displayAs) {
            this.displayAs = displayAs;
        }
    }

    public class Origin{
        public String description;
        public String workingTime;
        public String publicTime;


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWorkingTime() {
            return workingTime;
        }

        public void setWorkingTime(String workingTime) {
            this.workingTime = workingTime;
        }

        public String getPublicTime() {
            return publicTime;
        }

        public void setPublicTime(String publicTime) {
            this.publicTime = publicTime;
        }
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

        public LocationDetail getLocationDetail() {
            return locationDetail;
        }

        public void setLocationDetail(LocationDetail locationDetail) {
            this.locationDetail = locationDetail;
        }

        public String getServiceUid() {
            return serviceUid;
        }

        public void setServiceUid(String serviceUid) {
            this.serviceUid = serviceUid;
        }

        public String getRunDate() {
            return runDate;
        }

        public void setRunDate(String runDate) {
            this.runDate = runDate;
        }

        public String getTrainIdentity() {
            return trainIdentity;
        }

        public void setTrainIdentity(String trainIdentity) {
            this.trainIdentity = trainIdentity;
        }

        public String getRunningIdentity() {
            return runningIdentity;
        }

        public void setRunningIdentity(String runningIdentity) {
            this.runningIdentity = runningIdentity;
        }

        public String getAtocCode() {
            return atocCode;
        }

        public void setAtocCode(String atocCode) {
            this.atocCode = atocCode;
        }

        public String getAtocName() {
            return atocName;
        }

        public void setAtocName(String atocName) {
            this.atocName = atocName;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public boolean isPassenger() {
            return isPassenger;
        }

        public void setPassenger(boolean passenger) {
            isPassenger = passenger;
        }

        public ArrayList<Origin> getOrigin() {
            return origin;
        }

        public void setOrigin(ArrayList<Origin> origin) {
            this.origin = origin;
        }

        public ArrayList<Destination> getDestination() {
            return destination;
        }

        public void setDestination(ArrayList<Destination> destination) {
            this.destination = destination;
        }
    }



}
