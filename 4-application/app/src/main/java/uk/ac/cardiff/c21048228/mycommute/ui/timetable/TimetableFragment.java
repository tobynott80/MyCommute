package uk.ac.cardiff.c21048228.mycommute.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentManager;


import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentTimetableBinding;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.LocationSelectorFragment;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;

public class TimetableFragment extends Fragment {

    private FragmentTimetableBinding binding;
    private Station departureStation;
    private Station arrivalStation;

    Button btnTextDeparture;
    Button btnTextArrival;

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimetableViewModel timetableViewModel =
                new ViewModelProvider(this).get(TimetableViewModel.class);

        binding = FragmentTimetableBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnTextDeparture = binding.btnTextDeparture;
        btnTextArrival = binding.btnTextArrival;

        btnTextDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LocationSelectorFragment locationSelectorFragment = new LocationSelectorFragment("departure");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, locationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        btnTextArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open location selector fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LocationSelectorFragment locationSelectorFragment = new LocationSelectorFragment("arrival");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, locationSelectorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}