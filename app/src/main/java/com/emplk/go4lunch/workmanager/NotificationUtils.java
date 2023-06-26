package com.emplk.go4lunch.workmanager;

import android.util.Log;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class NotificationUtils {
    public static long calculateDelayUntilNoon() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime notificationTime = LocalTime.of(14, 11);

        LocalDateTime desiredDateTime = currentDateTime.with(notificationTime);

        // If the notification time has already passed today, move to the day after
        if (currentDateTime.isAfter(desiredDateTime)) {
            desiredDateTime = desiredDateTime.plusDays(1);
        }

        // Calculate the duration until the desired time
        Log.d("NotificationUtils", "Desired time: " + desiredDateTime);
        return ChronoUnit.MILLIS.between(currentDateTime, desiredDateTime);
    }
}
