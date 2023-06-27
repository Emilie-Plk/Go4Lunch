package com.emplk.go4lunch.workmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.emplk.go4lunch.R;

import java.time.LocalDateTime;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

@HiltWorker
public class NotificationWorker extends Worker {

    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_id";
    private static final CharSequence CHANNEL_NAME = "channel_name";

    @AssistedInject
    public NotificationWorker(
        @Assisted @NonNull Context context,
        @Assisted @NonNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("NotificationWorker", "doWork triggered at " + LocalDateTime.now());
        showNotification();
        return Result.success();
    }

    @SuppressLint("MissingPermission")
    private void showNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setContentTitle("Time to Go4Lunch!")
            .setContentText("salut peau de fesses")
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("salut peau de fesses"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            );
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
