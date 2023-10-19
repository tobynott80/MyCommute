package uk.ac.cardiff.c21048229.mycommute.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import uk.ac.cardiff.c21048229.mycommute.retrofit.CommuteCallback;
import uk.ac.cardiff.c21048229.mycommute.ui.home.Commute;
import uk.ac.cardiff.c21048229.mycommute.ui.locationSelector.Station;

public class DailyNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationType = "";
        String searchTime = "";
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
            searchTime = sharedPreferences.getString("homeCTime", "0800");
        } else {
            departureStation = new Station(sharedPreferences.getString("workDepartureName", "Work Departure"), sharedPreferences.getString("workDepartureCRS", "CDF"));
            arrivalStation = new Station(sharedPreferences.getString("workArrivalName", "Work Arrival"), sharedPreferences.getString("workArrivalCRS", "NWP"));
            searchTime = sharedPreferences.getString("workCTime", "0800");
        }
        NotificationBuilder notificationBuilder = new NotificationBuilder();
        String finalNotificationType = notificationType;
        notificationBuilder.getCommute(departureStation, arrivalStation, searchTime, new CommuteCallback() {
                    @Override
                    public void onCommuteLoaded(Commute commute) {
                        // Create the notification
                        String title = String.format("%s to %s", departureStation.getStationName(), arrivalStation.getStationName(), finalNotificationType);
                        String message = String.format("Departs: %s from platform %s", commute.getServices().get(0).getDepartureTime(), commute.getServices().get(0).getPlatform());
                        NotificationHelper.showNotification(context, title, message);
                    }
                }
        );
    }
}
