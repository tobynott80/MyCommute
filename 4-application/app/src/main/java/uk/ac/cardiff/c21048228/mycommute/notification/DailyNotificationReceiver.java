package uk.ac.cardiff.c21048228.mycommute.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DailyNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationType = "";

        // Create the notification
        String title = "MyCommute";
        String message = "Daily " + notificationType + " commute notification";
        NotificationHelper.showNotification(context, title, message);
    }
}
