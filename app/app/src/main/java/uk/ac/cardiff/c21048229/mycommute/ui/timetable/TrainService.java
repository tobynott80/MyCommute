package uk.ac.cardiff.c21048229.mycommute.ui.timetable;

public class TrainService {
    private String platform;
    private String departureTime;
    private String origin;
    private String destination;
    private TrainStatus status;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TrainStatus getStatus() {
        return status;
    }

    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    public TrainService(String platform, String departureTime, String origin, String destination, TrainStatus status) {
        this.platform = platform;
        this.departureTime = departureTime;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
    }
}
