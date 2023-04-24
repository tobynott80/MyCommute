package uk.ac.cardiff.c21048229.mycommute.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mTxtSetup;

    public HomeViewModel() {
        mTxtSetup = new MutableLiveData<>();
        mTxtSetup.setValue("Set a home and work route in settings.");
    }

    public LiveData<String> getText() {
        return mTxtSetup;
    }
}