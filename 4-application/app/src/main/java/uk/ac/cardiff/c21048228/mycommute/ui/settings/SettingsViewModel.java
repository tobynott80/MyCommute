package uk.ac.cardiff.c21048228.mycommute.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Station> homeDepartureStation = new MutableLiveData<>();
    private MutableLiveData<Station> homeArrivalStation = new MutableLiveData<>();

    private MutableLiveData<Station> workDepartureStation = new MutableLiveData<>();
    private MutableLiveData<Station> workArrivalStation = new MutableLiveData<>();

    public MutableLiveData<Station> getHomeDepartureStation() {
        return homeDepartureStation;
    }

    public void setHomeDepartureStation(MutableLiveData<Station> homeDepartureStation) {
        this.homeDepartureStation = homeDepartureStation;
    }

    public MutableLiveData<Station> getHomeArrivalStation() {
        return homeArrivalStation;
    }

    public void setHomeArrivalStation(MutableLiveData<Station> homeArrivalStation) {
        this.homeArrivalStation = homeArrivalStation;
    }

    public MutableLiveData<Station> getWorkDepartureStation() {
        return workDepartureStation;
    }

    public void setWorkDepartureStation(MutableLiveData<Station> workDepartureStation) {
        this.workDepartureStation = workDepartureStation;
    }

    public MutableLiveData<Station> getWorkArrivalStation() {
        return workArrivalStation;
    }

    public void setWorkArrivalStation(MutableLiveData<Station> workArrivalStation) {
        this.workArrivalStation = workArrivalStation;
    }
}