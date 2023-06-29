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
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

@HiltWorker
public class NotificationWorker extends Worker {

    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_id";
    private static final CharSequence CHANNEL_NAME = "channel_name";

    private static final String USERS_WITH_RESTAURANT_CHOICE = "usersWithRestaurantChoice";

    private static final String USERS_COLLECTION = "users";

    private final Context context;
    private final FirebaseUser currentUser;

    private final FirebaseFirestore firestore;

    @AssistedInject
    public NotificationWorker(
        @Assisted @NonNull Context context,
        @Assisted @NonNull WorkerParameters workerParams,
        @NonNull FirebaseFirestore firestore,
        @NonNull FirebaseAuth firebaseAuth
    ) {
        super(context, workerParams);
        this.context = context;
        currentUser = firebaseAuth.getCurrentUser();
        this.firestore = firestore;
    }

    @NonNull
    @Override
    public Result doWork() {
        Task<String> restaurantIdTask = getCurrentUserChosenRestaurantId();

        restaurantIdTask.addOnSuccessListener(restaurantId -> {
                Task<List<UserWithRestaurantChoiceEntity>> getUsersTask = getUsersWithRestaurantChoiceEntities(restaurantId);

                getUsersTask.addOnSuccessListener(usersWithRestaurant -> {
                        if (usersWithRestaurant != null) {
                            List<String> workmates = new ArrayList<>();
                            String userId = "123";

                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            }

                            String restaurantName = "";

                            for (UserWithRestaurantChoiceEntity user : usersWithRestaurant) {
                                if (user.getAttendingRestaurantId().equals(restaurantId) && !user.getId().equals(userId)) {
                                    workmates.add(user.getAttendingRestaurantName());
                                }

                                // Get the restaurant name
                                if (user.getAttendingRestaurantId().equals(restaurantId)) {
                                    restaurantName = user.getAttendingRestaurantName();
                                }
                            }

                            displayNotification(restaurantName, workmates);
                        }
                    }
                );

                getUsersTask.addOnFailureListener(e -> {
                        // Handle the failure to get users with restaurant choice
                        Log.e("NotificationWorker", "Error while getting users with restaurant choice entities", e);
                    }
                );
            }
        );

        restaurantIdTask.addOnFailureListener(e -> {
                // Handle the failure to get current user's chosen restaurant id
                Log.e("NotificationWorker", "Error while getting current user's chosen restaurant id", e);
            }
        );

        return Result.success();
    }

    @SuppressLint("MissingPermission")
    private void displayNotification(
        String restaurantName,
        List<String> workmates
    ) {

        StringBuilder contentText = new StringBuilder();

        if (!workmates.isEmpty()) {
            contentText.append("You are going to eat at ")
                .append(restaurantName)
                .append(" with ");

            for (int i = 0; i < workmates.size(); i++) {
                contentText.append(workmates.get(i));

                if (i < workmates.size() - 2)
                    contentText.append(", ");
                else if (i == workmates.size() - 2)
                    contentText.append(" and ");
            }
        } else {
            contentText.append("You are going to eat at ")
                .append(restaurantName);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setContentTitle("Time to Go4Lunch!")
            .setContentText(contentText)
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(contentText))
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

    private Task<String> getCurrentUserChosenRestaurantId() {
        final TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();

        firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
            .document(currentUser.getUid())
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot != null) {
                        String restaurantId = documentSnapshot.getString("attendingRestaurantId");
                        taskCompletionSource.setResult(restaurantId);
                    } else {
                        taskCompletionSource.setException(new Exception("Document not found"));
                    }
                }
            )
            .addOnFailureListener(taskCompletionSource::setException);

        return taskCompletionSource.getTask();
    }

    private Task<List<UserWithRestaurantChoiceEntity>> getUsersWithRestaurantChoiceEntities(String restaurantId) {
        TaskCompletionSource<List<UserWithRestaurantChoiceEntity>> taskCompletionSource = new TaskCompletionSource<>();

        firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
            .whereEqualTo("attendingRestaurantId", restaurantId)
            .get()
            .addOnSuccessListener(querySnapshot -> {
                List<UserWithRestaurantChoiceEntity> users = querySnapshot.toObjects(UserWithRestaurantChoiceEntity.class);
                taskCompletionSource.setResult(users);
            })
            .addOnFailureListener(taskCompletionSource::setException);

        return taskCompletionSource.getTask();
    }
}
