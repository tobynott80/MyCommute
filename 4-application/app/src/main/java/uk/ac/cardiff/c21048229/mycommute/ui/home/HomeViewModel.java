package uk.ac.cardiff.c21048229.mycommute.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<Commute> homeCommuteMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> homeDepartureTimeMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Commute> workCommuteMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> workDepartureTimeMutableLiveData = new MutableLiveData<>();

    public void setHomeDepartureTime(String departureTime){
        homeDepartureTimeMutableLiveData.setValue(departureTime);
    }

    public LiveData<String> getHomeDepartureTime() {
        return homeDepartureTimeMutableLiveData;
    }


    public void setHomeCommute(Commute homeCommute) {
        homeCommuteMutableLiveData.setValue(homeCommute);
    }

    public LiveData<Commute> getHomeCommute() {
        return homeCommuteMutableLiveData;
    }

    public void setWorkDepartureTime(String departureTime){
        workDepartureTimeMutableLiveData.setValue(departureTime);
    }

    public LiveData<String> getWorkDepartureTime() {
        return workDepartureTimeMutableLiveData;
    }

    public void setWorkCommute(Commute workCommute) {
        workCommuteMutableLiveData.setValue(workCommute);
    }

    public LiveData<Commute> getWorkCommute() {
        return workCommuteMutableLiveData;
    }


}
