package uk.ac.cardiff.c21048228.mycommute.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import uk.ac.cardiff.c21048228.mycommute.retrofit.CommuteCallback;
import uk.ac.cardiff.c21048228.mycommute.ui.home.Commute;
import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.Station;

public class DailyNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationType = "";
        Station departureStation;
        Station arrivalStation;
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        if (intent.getAction().equals("homeNotification")) {
            notificationType = "home";
        } else if (intent.getAction().equals("workNotification")) {
            notificationType = "work";
        } else {
            System.out.println("Unknown notification type, defaulting to home");
            notificationType = "home";
        }
        if(notificationType.equals("home")) {
            departureStation = new Station(sharedPreferences.getString("homeDepartureName", "Home Departure"), sharedPreferences.getString("homeDepartureCRS", "CDF"));
            arrivalStation = new Station(sharedPreferences.getString("homeArrivalName", "Home Arrival"), sharedPreferences.getString("homeArrivalCRS", "NWP"));
        } else {
            departureStation = new Station(sharedPreferences.getString("workDepartureName", "Work Departure"), sharedPreferences.getString("workDepartureCRS", "CDF"));
            arrivalStation = new Station(sharedPreferences.getString("workArrivalName", "Work Arrival"), sharedPreferences.getString("workArrivalCRS", "NWP"));
        }
        NotificationBuilder notificationBuilder = new NotificationBuilder();
        String finalNotificationType = notificationType;
        notificationBuilder.getCommute(departureStation, arrivalStation, new CommuteCallback() {
                    @Override
                    public void onCommuteLoaded(Commute commute) {
                        // Create the notification
                        String title = String.format("%s to %s (%s notification)", departureStation.getStationName(), arrivalStation.getStationName(), finalNotificationType);
                        String message = String.format("Departs: %s from platform %s", commute.getServices().get(0).getDepartureTime(), commute.getServices().get(0).getPlatform());
                        NotificationHelper.showNotification(context, title, message);
                    }
                }
        );
    }
}
