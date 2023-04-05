package uk.ac.cardiff.c21048228.mycommute.ui.timetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;

public class TimetableViewModel extends ViewModel {
    private MutableLiveData<Station> selectedDepartureStation = new MutableLiveData<>();
    private MutableLiveData<Station> selectedArrivalStation = new MutableLiveData<>();

    public void setSelectedDepartureStation(Station station) {
        selectedDepartureStation.setValue(station);
    }

    public LiveData<Station> getSelectedDepartureStation() {
        return selectedDepartureStation;
    }

    public void setSelectedArrivalStation(Station station) {
        selectedArrivalStation.setValue(station);
    }

    public LiveData<Station> getSelectedArrivalStation() {
        return selectedArrivalStation;
    }

}