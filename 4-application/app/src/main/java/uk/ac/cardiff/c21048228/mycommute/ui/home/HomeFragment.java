package uk.ac.cardiff.c21048228.mycommute.ui.home;

import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.DELAYED;
import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.ON_TIME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentHomeBinding;
import uk.ac.cardiff.c21048228.mycommute.retrofit.CommuteBuilder;
import uk.ac.cardiff.c21048228.mycommute.retrofit.CommuteCallback;
import uk.ac.cardiff.c21048228.mycommute.retrofit.RttMethods;
import uk.ac.cardiff.c21048228.mycommute.retrofit.RttRetroFit;
import uk.ac.cardiff.c21048228.mycommute.retrofit.SearchModel;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainService;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ConstraintLayout commuteLayout = binding.homeCommuteLayout;
        commuteLayout.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", 0);
        if((sharedPreferences.getBoolean("isCommuteSetup", false))){
            commuteLayout.setVisibility(View.VISIBLE);
            CommuteBuilder commuteBuilder = new CommuteBuilder();
            Station departureStation = new Station(sharedPreferences.getString("homeDepartureName", "Cardiff Central"), sharedPreferences.getString("homeDepartureCRS", "CDF"));
            Station arrivalStation = new Station(sharedPreferences.getString("homeArrivalName", "Newport"), sharedPreferences.getString("homeArrivalCRS", "NWP"));
            String departureTime = sharedPreferences.getString("homeTime", "Home");
            if(departureTime.equals("Home")){
                // If home departure time not set, use current time
                SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                departureTime = sdf.format(new Date());
            }
            String finalDepartureTime = departureTime;
            commuteBuilder.getCommute(departureStation, arrivalStation, departureTime, new CommuteCallback() {
                @Override
                public void onCommuteLoaded(Commute commute) {
                    // Load the fragment once the callback is complete
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    CommuteFragment commuteFragment = new CommuteFragment(commute, finalDepartureTime);
                    fragmentTransaction.replace(R.id.homeCommuteLayout, commuteFragment);
                    fragmentTransaction.commit();
                }
            });
            Station workDepartureStation = new Station(sharedPreferences.getString("workDepartureName", "Cardiff Central"), sharedPreferences.getString("workDepartureCRS", "CDF"));
            Station workArrivalStation = new Station(sharedPreferences.getString("workArrivalName", "Newport"), sharedPreferences.getString("workArrivalCRS", "NWP"));
            String workDepartureTime = sharedPreferences.getString("workTime", "Work");
            if(departureTime.equals("Work")){
                // If home departure time not set, use current time
                SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                workDepartureTime = sdf.format(new Date());
            }

            String finalWorkDepartureTime = workDepartureTime;
            commuteBuilder.getCommute(workDepartureStation, workArrivalStation, workDepartureTime, new CommuteCallback() {
                @Override
                public void onCommuteLoaded(Commute commute) {
                    // Load the fragment once the callback is complete
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    CommuteFragment commuteFragment = new CommuteFragment(commute, finalWorkDepartureTime);
                    fragmentTransaction.replace(R.id.workCommuteLayout, commuteFragment);
                    fragmentTransaction.commit();
                }
            });

        } else{
            final TextView textView = binding.tvSetup;
            homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}