package uk.ac.cardiff.c21048229.mycommute.ui.timetable;

import static uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainStatus.CANCELLED;
import static uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainStatus.DELAYED;
import static uk.ac.cardiff.c21048229.mycommute.ui.timetable.TrainStatus.ON_TIME;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import uk.ac.cardiff.c21048229.mycommute.R;
import uk.ac.cardiff.c21048229.mycommute.databinding.FragmentTimetableBinding;
import uk.ac.cardiff.c21048229.mycommute.retrofit.RttMethods;
import uk.ac.cardiff.c21048229.mycommute.retrofit.RttRetroFit;
import uk.ac.cardiff.c21048229.mycommute.retrofit.SearchModel;
import uk.ac.cardiff.c21048229.mycommute.ui.locationSelector.LocationSelectorFragment;
import uk.ac.cardiff.c21048229.mycommute.ui.locationSelector.Station;

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
        Button btnSwap = binding.btnSwap;
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
                // Set progress bar to visible while the api is called and the data is parsed into the recyclerview
                binding.progressBar.setVisibility(View.VISIBLE);
                if (getDepartureStation() != null && getArrivalStation() != null && getArrivalStation() != getDepartureStation()) {
                    //search with api using our retrofit instance
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
                            //if no services are available - notify with toast
                            if (!(services == null)){
                                // Sort services by departure time
                                services.sort((o1, o2) -> {
                                    String time1 = o1.getLocationDetail().getRealtimeDeparture();
                                    String time2 = o2.getLocationDetail().getRealtimeDeparture();
                                    if (time1 == null) {
                                        time1 = o1.getLocationDetail().getGbttBookedDeparture();
                                    }
                                    if (time2 == null) {
                                        time2 = o2.getLocationDetail().getGbttBookedDeparture();
                                    }
                                    return time1.compareTo(time2);
                                });
                                // Sort services by departure date
                                services.sort((o1, o2) -> {
                                    String date1 = o1.getRunDate();
                                    String date2 = o2.getRunDate();
                                    LocalDate date1Local = LocalDate.parse(date1);
                                    LocalDate date2Local = LocalDate.parse(date2);
                                    return date1Local.compareTo(date2Local);
                                });
                                // Create Train Service objects for each returned departure in the response body
                                for (int i = 0; i < services.size(); i++) {
                                    // Create enum holder for service status
                                    TrainStatus status;
                                    // RTT api sometimes returns false isCall for delayed services
                                    if (services.get(i).getLocationDetail().isCall){
                                        status = ON_TIME;
                                    }else {
                                        status = DELAYED;
                                    }
                                    // Get platform from service
                                    String platform = services.get(i).getLocationDetail().getPlatform();
                                    // RTT often cannot find the platform for various reasons, so the platform is just set as -- instead
                                    if (platform == null || platform.equals("DPL")){
                                        platform = "--";
                                    }
                                    // Get the estimated departure time (departureTime). This can sometimes be null if RTT is unsure
                                    String departureTime = services.get(i).locationDetail.getRealtimeDeparture();

                                    // Get the origin (not super necessary, but it looks nicer in the UI lol)
                                    String origin = getString(R.string.arr_from_colon) + services.get(i).getLocationDetail().getOrigin().get(0).getDescription();
                                    // If RTT cannot find an estimated departure, get the booked departure instead
                                    if (departureTime == null){
                                        departureTime = services.get(i).locationDetail.getGbttBookedDeparture();
                                    } else{
                                        String bookedArrival = services.get(i).locationDetail.getGbttBookedDeparture();
                                        // GBTT can also be null sometimes - very annoying...
                                        if (!(bookedArrival == null)){
                                            if (Integer.parseInt(departureTime) > Integer.parseInt(bookedArrival)){
                                                // If the train is delayed, set status to delayed and calculate delay length
                                                status = DELAYED;
                                                LocalTime departureTimeLT = LocalTime.parse(departureTime, DateTimeFormatter.ofPattern("HHmm"));
                                                LocalTime bookedArrivalLT = LocalTime.parse(bookedArrival, DateTimeFormatter.ofPattern("HHmm"));
                                                Duration delayDuration = Duration.between(bookedArrivalLT, departureTimeLT);
                                                long delayLength = delayDuration.toMinutes();

                                                // Set the origin text view to the delay length, as it isn't actually that important
                                                String delayedByFormat = getString(R.string.delayed_by);
                                                String delayLengthString = Long.toString(delayLength);
                                                origin = String.format(delayedByFormat, delayLengthString, (bookedArrival.substring(0,2) + ":" + bookedArrival.substring(2,4)));
                                            }
                                        }
                                    }
                                    // Find if train is cancelled
                                    if (services.get(i).getLocationDetail().getDisplayAs().equals("CANCELLED_CALL")){
                                        status = CANCELLED;
                                        origin = getString(R.string.cancelled_colon) + services.get(i).locationDetail.getCancelReasonShortText();
                                    }
                                    // Find if train is replacement bus
                                    if (services.get(i).getServiceType().equals("bus")){
                                        platform = getString(R.string.bus);
                                        origin = getString(R.string.replacement_bus);
                                    }
                                    // Format the departure time from HHMM to HH:MM
                                    String formattedDepartureTime = departureTime.substring(0, 2) + ":" + departureTime.substring(2, 4);
                                    // Create a new train service object with all the data parsed above
                                    TrainService trainService = new TrainService(platform, formattedDepartureTime, origin, services.get(i).getLocationDetail().getDestination().get(0).getDescription(), status);
                                    // Add said train service object to the arraylist
                                    departures.add(trainService);
                                }
                            }else if (departureStation.getStationCRS().equals("KGX") && arrivalStation.getStationCRS().equals("HOG")){
                                // Easter egg: remove before release
                                departures.add(new TrainService("9¾", "11:00", "Hogwarts Express", "Hogwarts", TrainStatus.ON_TIME));
                            }else{
                                Toast.makeText(binding.getRoot().getContext(), getContext().getText(R.string.no_departures_available), Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);
                                return;
                            }

                            // Populate the recycler view with the services found and display
                            recyclerView = root.findViewById(R.id.rvTrainServices);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new TrainServiceRecyclerAdapter(departures);
                            recyclerView.setAdapter(adapter);
                            // Remove progress bar
                            binding.progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<SearchModel> call, Throwable t) {
                            Toast.makeText(binding.getRoot().getContext(), getContext().getText(R.string.err_api_unavailable), Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    });

                }
                else{
                    //show error toast
                    Toast.makeText(binding.getRoot().getContext(), getContext().getText(R.string.select_arr_and_dep_stnt), Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSwap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!(arrivalStation == null || departureStation == null)){
                    //update UI
                    String tempBtnText = btnTextArrival.getText().toString();
                    btnTextArrival.setText(btnTextDeparture.getText().toString());
                    btnTextDeparture.setText(tempBtnText);

                    //update objects
                    String tempStationName = arrivalStation.getStationName();
                    String tempStationCRS = arrivalStation.getStationCRS();
                    arrivalStation.setStationName(departureStation.getStationName());
                    arrivalStation.setStationCRS(departureStation.getStationCRS());
                    departureStation.setStationName(tempStationName);
                    departureStation.setStationCRS(tempStationCRS);
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