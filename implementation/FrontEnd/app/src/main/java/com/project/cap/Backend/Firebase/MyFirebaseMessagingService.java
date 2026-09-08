package com.project.cap.Backend.Firebase;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.cap.R;
import java.util.concurrent.CompletableFuture;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "New Token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        final String mesg = remoteMessage.getNotification().getBody();

        showNotification(
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody()
        );

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), mesg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static CompletableFuture<String> getCurrentToken() {
        var cfToken = new CompletableFuture<String>();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                cfToken.complete("");
                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }

            cfToken.complete(task.getResult());
        });

        return cfToken;
    }

    public void showNotification(String title, String message) {
        //var bld = new NotificationCompat.Builder(this);

        // Pass the intent to switch to the MainActivity
        //val intent = Intent(this, MainActivity::class.java)
        var intent = new Intent();

        // Assign channel ID
        var channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        var pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT
        );

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        var builder = new NotificationCompat.Builder(
                        this,
                        channel_id
                )
                .setAutoCancel(true)
                .setVibrate(
                        new long[]{
                                1000, 1000, 1000,
                                1000, 1000
                        }
                )
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        ) {
            builder = builder.setContent(
                getCustomDesign(title, message)
            )
        } // If Android Version is lower than Jelly Beans,
        else {
            builder = builder.setContentTitle(title)
                .setContentText(message)
        }*/

        builder = builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round);

        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        var notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationManager.createNotificationChannel(
                    notificationChannel
            );
        }

        notificationManager.notify(0, builder.build());
    }
}
