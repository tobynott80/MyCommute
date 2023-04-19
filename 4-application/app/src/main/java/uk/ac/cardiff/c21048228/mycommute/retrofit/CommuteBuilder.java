package uk.ac.cardiff.c21048228.mycommute.retrofit;

import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.DELAYED;
import static uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus.ON_TIME;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import uk.ac.cardiff.c21048228.mycommute.R;
import uk.ac.cardiff.c21048228.mycommute.ui.home.Commute;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainService;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainServiceRecyclerAdapter;
import uk.ac.cardiff.c21048228.mycommute.ui.timetable.TrainStatus;

public class CommuteBuilder {

    Commute builtCommute; //The commute that is to be returned

    public void getCommute(Station departureStation, Station arrivalStation, CommuteCallback callback) {

        //Instantiate the Retrofit instance
        RttMethods rttMethods = RttRetroFit.getRetrofitInstance().create(RttMethods.class);
        Call<SearchModel> call = rttMethods.getAllData(departureStation.getStationCRS(), arrivalStation.getStationCRS(), "XX-API-KEY-XX==");
        call.enqueue(new retrofit2.Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, retrofit2.Response<SearchModel> response) {
                System.out.println("Successful call " + response.code());
                if (response.isSuccessful()) {
                    SearchModel searchModel = response.body();
                    ArrayList<SearchModel.Service> services = searchModel.getServices();
                    ArrayList<TrainService> departures = new ArrayList<>();
                    //if no services are available - notify with toast
                    if (!(services == null)) {
                        // Create Train Service objects for the first 3 departures
                        for (int i = 0; i < 3; i++) {
                            // Create enum holder for service status
                            TrainStatus status;
                            // RTT api sometimes returns false isCall for delayed services
                            if (services.get(i).getLocationDetail().isCall) {
                                status = ON_TIME;
                            } else {
                                status = DELAYED;
                            }
                            // Get platform from service
                            String platform = services.get(i).getLocationDetail().getPlatform();
                            // RTT often cannot find the platform for various reasons, so the platform is just set as -- instead
                            if (platform == null || platform.equals("DPL")) {
                                platform = "--";
                            }
                            // Get the estimated departure time (departureTime). This can sometimes be null if RTT is unsure
                            String departureTime = services.get(i).locationDetail.getRealtimeDeparture();

                            // Get the origin (not super necessary, but it looks nicer in the UI lol)
                            String origin = "Arriving From: " + services.get(i).getLocationDetail().getOrigin().get(0).getDescription();
                            // If RTT cannot find an estimated departure, get the booked departure instead
                            if (departureTime == null) {
                                departureTime = services.get(i).locationDetail.getGbttBookedDeparture();
                            } else {
                                String bookedArrival = services.get(i).locationDetail.getGbttBookedDeparture();
                                // GBTT can also be null sometimes - very annoying...
                                if (!(bookedArrival == null)) {
                                    if (Integer.parseInt(departureTime) > Integer.parseInt(bookedArrival)) {
                                        // If the train is delayed, set status to delayed and calculate delay length
                                        status = DELAYED;
                                        // Set the origin text view to the delay length, as it isn't actually that important
                                        origin = String.format("Delayed by %s mins", (Integer.parseInt(departureTime) - Integer.parseInt(bookedArrival)));
                                    }
                                }
                            }
                            // Format the departure time from HHMM to HH:MM
                            String formattedDepartureTime = departureTime.substring(0, 2) + ":" + departureTime.substring(2, 4);
                            // Create a new train service object with all the data parsed above
                            TrainService trainService = new TrainService(platform, formattedDepartureTime, origin, services.get(i).getLocationDetail().getDestination().get(0).getDescription(), status);
                            // Add said train service object to the arraylist
                            departures.add(trainService);
                            // Add all services and data to builtCommute return object
                            builtCommute = new Commute(arrivalStation.getStationName(), departureStation.getStationName(), departures, true);
                            callback.onCommuteLoaded(builtCommute);
                        }
                    } else {
                        // If no departures found, return an empty and invalid commute object
                        builtCommute = new Commute(arrivalStation.getStationName(), departureStation.getStationName(), null, false);
                        callback.onCommuteLoaded(builtCommute);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                // If api fails, return an empty and invalid commute object
                builtCommute = new Commute(arrivalStation.getStationName(), departureStation.getStationName(), null, false);
                callback.onCommuteLoaded(builtCommute);
            }

        });
    }
}

