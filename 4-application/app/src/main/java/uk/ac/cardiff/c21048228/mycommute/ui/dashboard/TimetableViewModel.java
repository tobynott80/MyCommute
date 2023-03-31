package uk.ac.cardiff.c21048228.mycommute.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimetableViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TimetableViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}