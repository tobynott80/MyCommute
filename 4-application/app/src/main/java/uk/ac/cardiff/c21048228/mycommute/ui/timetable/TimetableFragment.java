package uk.ac.cardiff.c21048228.mycommute.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import retrofit2.Call;
import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.databinding.FragmentTimetableBinding;
import uk.ac.cardiff.c21048228.mycommute.retrofit.RttMethods;
import uk.ac.cardiff.c21048228.mycommute.retrofit.RttRetroFit;
import uk.ac.cardiff.c21048228.mycommute.retrofit.SearchModel;
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
        Button btnSearch = binding.btnSearch;


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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDepartureStation() != null && getArrivalStation() != null) {
                    //search with api
                    RttMethods rttMethods = RttRetroFit.getRetrofitInstance().create(RttMethods.class);
                    Call<SearchModel> call = rttMethods.getAllData(departureStation.getStationCRS(), arrivalStation.getStationCRS(), "XX-API-KEY-XX==");
                    call.enqueue(new retrofit2.Callback<SearchModel>() {
                        @Override
                        public void onResponse(Call<SearchModel> call, retrofit2.Response<SearchModel> response) {
                            System.out.println("Successful call " + response.code());
                            if (response.isSuccessful()) {
                                System.out.println(response.body().getServices().get(0).getLocationDetail().getGbttBookedArrival());
                            }
                        }

                        @Override
                        public void onFailure(Call<SearchModel> call, Throwable t) {
                            Toast.makeText(binding.getRoot().getContext(), "Error: API unavailable", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    //show error toast
                    Toast.makeText(binding.getRoot().getContext(), "Please select an Arrival and Departure station", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TimetableViewModel timetableViewModel = new ViewModelProvider(requireParentFragment()).get(TimetableViewModel.class);
        timetableViewModel.getSelectedDepartureStation().observe(getViewLifecycleOwner(), new Observer<Station>() {
            @Override
            public void onChanged(Station station) {
                btnTextDeparture.setText(station.getStationName());
                setDepartureStation(station);

            }
        });
        timetableViewModel.getSelectedArrivalStation().observe(getViewLifecycleOwner(), new Observer<Station>() {
                    @Override
                    public void onChanged(Station station) {
                        if (station != null) {
                            btnTextArrival.setText(station.getStationName());
                            setArrivalStation(station);
                        }
                    }
                }
        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}