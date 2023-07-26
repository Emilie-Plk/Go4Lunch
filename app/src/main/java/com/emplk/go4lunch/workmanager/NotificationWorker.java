package com.emplk.go4lunch.workmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.notification.GetNotificationEntityUseCase;
import com.emplk.go4lunch.domain.notification.NotificationEntity;
import com.emplk.go4lunch.ui.restaurant_detail.RestaurantDetailActivity;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

@HiltWorker
public class NotificationWorker extends Worker {
    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_id";
    private static final CharSequence CHANNEL_NAME = "channel_name";

    private final Context context;
    @NonNull
    private final Clock clock;
    @NonNull
    private final GetNotificationEntityUseCase getNotificationEntityUseCase;

    @AssistedInject
    public NotificationWorker(
        @Assisted Context context,
        @Assisted WorkerParameters workerParams,
        @NonNull Clock clock,
        @NonNull GetNotificationEntityUseCase getNotificationEntityUseCase
    ) {
        super(context, workerParams);
        this.context = context;
        this.clock = clock;
        this.getNotificationEntityUseCase = getNotificationEntityUseCase;
    }

    @NonNull
    @Override
    public Result doWork() {
        DayOfWeek dayOfWeek = LocalDate.now(clock).getDayOfWeek();

        // No notification scheduled on weekends :)
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return Result.success();
        }

        NotificationEntity notificationEntity = getNotificationEntityUseCase.invoke();
        if (notificationEntity != null) {
            displayNotification(notificationEntity);
        } else {
            return Result.success();
        }

        return Result.success();
    }

    @SuppressLint("MissingPermission")
    private void displayNotification(
        @NonNull NotificationEntity notificationEntity
    ) {
        StringBuilder contentText = new StringBuilder();

        List<String> workmates = notificationEntity.getWorkmates();
        String restaurantName = notificationEntity.getRestaurantName();
        String restaurantVicinity = notificationEntity.getRestaurantVicinity();

        if (workmates != null &&
            !workmates.isEmpty() &&
            restaurantName != null &&
            restaurantVicinity != null
        ) {
            contentText.append(context.getString(R.string.notification_content_start));

            if (workmates.size() == 1) {
                contentText.append(context.getString(R.string.notification_content_and)).append(workmates.get(0));
            } else if (workmates.size() == 2) {
                contentText.append(", ").append(workmates.get(0))
                    .append(context.getString(R.string.notification_content_and)).append(workmates.get(1));
            } else {
                for (int i = 0; i < workmates.size() - 1; i++) {
                    contentText.append(", ").append(workmates.get(i));
                }

                contentText.append(context.getString(R.string.notification_content_last_and)).append(workmates.get(workmates.size() - 1));
            }

            contentText.append(context.getString(R.string.notification_content_going_to_eat_at))
                .append(restaurantName)
                .append(context.getString(R.string.notification_content_located_at))
                .append(restaurantVicinity);
        } else {
            contentText.append(context.getString(R.string.notification_content_solo_user))
                .append(restaurantName)
                .append(context.getString(R.string.notification_content_located_at))
                .append(restaurantVicinity);
        }

        Intent intent = RestaurantDetailActivity.navigate(context, notificationEntity.getRestaurantId())
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            flag = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        PendingIntent pendingIntent =
            PendingIntent.getActivity(context, 0, intent, flag);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setContentTitle(context.getString(R.string.notification_content_title))
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(contentText.toString()))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}