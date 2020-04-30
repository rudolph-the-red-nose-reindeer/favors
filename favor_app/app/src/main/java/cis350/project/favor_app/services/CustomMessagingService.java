package cis350.project.favor_app.services;

import android.app.NotificationManager;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import cis350.project.favor_app.R;
import cis350.project.favor_app.ui.profile.ProfileActivity;

public class CustomMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage msg) {
        super.onMessageReceived(msg);

        RemoteMessage.Notification notif = msg.getNotification();
        Log.d("notification details", notif.getBody());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                getString(R.string.channel_id))
                .setSmallIcon(R.drawable.bell)
                .setContentText(notif.getBody())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify("Favor App", 0, builder.build());
    }
}
