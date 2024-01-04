package com.example.attendanceapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        sendNotification(message.getNotification().getTitle(), message.getNotification().getBody());
    }

    private void sendNotification(String title, String messageBody) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification =
                new NotificationCompat.Builder(this, MyApp.CHANNEL_ID)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle(title)
                                .addLine(messageBody))
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setAutoCancel(true)
                        .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getNotificationID(), notification);
    }

    private int getNotificationID() {
        return (int) new Date().getTime();
    }
}
