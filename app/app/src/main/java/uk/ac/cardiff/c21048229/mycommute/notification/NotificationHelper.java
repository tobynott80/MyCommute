package uk.ac.cardiff.c21048229.mycommute.notification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import uk.ac.cardiff.c21048229.mycommute.R;

public class NotificationHelper {

    private static final String NOTIFICATION_CHANNEL_ID = "MyCommuteChannel";

    public static void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return;
        }

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Daily Commute Notifications", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("Notifications for MyCommute app");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_train_24)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);


        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }
}
