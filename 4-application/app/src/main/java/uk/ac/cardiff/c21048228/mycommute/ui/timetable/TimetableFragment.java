package uk.ac.cardiff.c21048228.mycommute.ui.timetable;

import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.DELAYED;
import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.ON_TIME;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

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

    private Button btnTextDeparture;
    private Button btnTextArrival;

    private RecyclerView recyclerView;
    private TrainServiceRecyclerAdapter adapter;

    private ArrayList<TrainService> departures;

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
        binding.progressBar.setVisibility(View.GONE);

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
                binding.progressBar.setVisibility(View.VISIBLE);
                if (getDepartureStation() != null && getArrivalStation() != null && getArrivalStation() != getDepartureStation()) {
                    //search with api
                    RttMethods rttMethods = RttRetroFit.getRetrofitInstance().create(RttMethods.class);
                    Call<SearchModel> call = rttMethods.getAllData(departureStation.getStationCRS(), arrivalStation.getStationCRS(), "XX-API-KEY-XX==");
                    call.enqueue(new retrofit2.Callback<SearchModel>() {
                        @Override
                        public void onResponse(Call<SearchModel> call, retrofit2.Response<SearchModel> response) {
                            System.out.println("Successful call " + response.code());
                            if (response.isSuccessful()) {
                                populateRecyclerView(response.body());
                            }
                        }

                        private void populateRecyclerView(SearchModel searchModel) {
                            ArrayList<SearchModel.Service> services = searchModel.getServices();
                            departures = new ArrayList<>();
                            if (services == null){
                                Toast.makeText(binding.getRoot().getContext(), "No Departures Available", Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);
                                return;
                            }
                            for (int i = 0; i < services.size(); i++) {
                                TrainStatus status;
                                if (services.get(i).getLocationDetail().isCall){
                                    // services.get(i).locationDetail.getPlatform()
                                    status = ON_TIME;
                                }else {
                                    status = DELAYED;
                                }
                                String platform = services.get(i).getLocationDetail().getPlatform();
                                if (platform == null || platform.equals("DPL")){
                                    platform = "--";
                                }
                                String departureTime = services.get(i).locationDetail.getRealtimeDeparture();
                                String origin = "Arriving From: " + services.get(i).getLocationDetail().getOrigin().get(0).getDescription();

                                if (departureTime == null){
                                    departureTime = services.get(i).locationDetail.getGbttBookedDeparture();
                                } else{
                                    String bookedArrival = services.get(i).locationDetail.getGbttBookedDeparture();
                                    if (!(bookedArrival == null)){
                                        if (Integer.valueOf(departureTime) > Integer.valueOf(bookedArrival)){
                                            status = DELAYED;
                                            origin = String.format("Delayed by %s mins", (Integer.valueOf(departureTime) - Integer.valueOf(bookedArrival)));
                                        }
                                    }
                                }
                                String formattedDepartureTime = departureTime.substring(0, 2) + ":" + departureTime.substring(2, 4);

                                TrainService trainService = new TrainService(platform, formattedDepartureTime, origin, services.get(i).getLocationDetail().getDestination().get(0).getDescription(), status);
                                departures.add(trainService);
                            }
                            recyclerView = root.findViewById(R.id.rvTrainServices);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new TrainServiceRecyclerAdapter(departures);
                            recyclerView.setAdapter(adapter);
                            binding.progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<SearchModel> call, Throwable t) {
                            Toast.makeText(binding.getRoot().getContext(), "Error: API unavailable", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    });

                }
                else{
                    //show error toast
                    Toast.makeText(binding.getRoot().getContext(), "Please select an Arrival and Departure station", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
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