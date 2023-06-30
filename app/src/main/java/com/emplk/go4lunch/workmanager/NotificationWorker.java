package com.emplk.go4lunch.workmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.emplk.go4lunch.ui.restaurant_detail.RestaurantDetailActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

@HiltWorker
public class NotificationWorker extends Worker {

    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_id";
    private static final CharSequence CHANNEL_NAME = "channel_name";
    private static final String USERS_WITH_RESTAURANT_CHOICE = "usersWithRestaurantChoice";

    private final Context context;
    private final Clock clock;
    private final FirebaseFirestore firestore;
    private final FirebaseUser currentUser;

    @AssistedInject
    public NotificationWorker(
        @Assisted Context context,
        @Assisted WorkerParameters workerParams,
        @NonNull Clock clock,
        @NonNull FirebaseFirestore firestore,
        @NonNull FirebaseAuth firebaseAuth
    ) {
        super(context, workerParams);
        this.context = context;
        this.clock = clock;
        this.firestore = firestore;
        this.currentUser = firebaseAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public Result doWork() {

        DayOfWeek dayOfWeek = LocalDate.now(clock).getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return Result.success();
        }

        String currentUserChosenRestaurantId = getCurrentUserChosenRestaurantId();

        if (currentUserChosenRestaurantId != null) {
            List<UserWithRestaurantChoiceEntity> usersWithRestaurantChoice = getUsersWithRestaurantChoiceEntities(currentUserChosenRestaurantId);

            List<String> workmates = new ArrayList<>();
            String userId = currentUser.getUid();
            String restaurantName = "";

            for (UserWithRestaurantChoiceEntity user : usersWithRestaurantChoice) {
                if (user.getAttendingRestaurantId().equals(currentUserChosenRestaurantId) && !user.getId().equals(userId)) {
                    workmates.add(user.getAttendingRestaurantName());
                }

                // Get the restaurant name
                if (user.getAttendingRestaurantId().equals(currentUserChosenRestaurantId)) {
                    restaurantName = user.getAttendingRestaurantName();
                }
            }

            displayNotification(restaurantName, workmates, currentUserChosenRestaurantId);
        }
        return Result.success();
    }

    @SuppressLint("MissingPermission")
    private void displayNotification(
        String restaurantName,
        List<String> workmates,
        String restaurantId
    ) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra("restaurantId", restaurantId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setContentTitle("Time to Go4Lunch!")
            .setContentText(contentText.toString())
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

    private String getCurrentUserChosenRestaurantId() {
        Task<DocumentSnapshot> task =
            firestore
                .collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(currentUser.getUid())
                .get();

        try {
            Tasks.await(task); // Wait for the task to complete synchronously
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null) {
                    UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = documentSnapshot.toObject(UserWithRestaurantChoiceEntity.class);
                    if (userWithRestaurantChoiceEntity != null) {
                        return userWithRestaurantChoiceEntity.getAttendingRestaurantId();
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("NotificationWorker", "Error while getting current user chosen restaurant ID", e);
        }

        return null; // Return null if an error occurred or no restaurant ID is found
    }

    private List<UserWithRestaurantChoiceEntity> getUsersWithRestaurantChoiceEntities(String restaurantId) {
        TaskCompletionSource<List<UserWithRestaurantChoiceEntity>> taskCompletionSource = new TaskCompletionSource<>();

        firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
            .whereEqualTo("attendingRestaurantId", restaurantId)
            .get()
            .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntities = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = documentSnapshot.toObject(UserWithRestaurantChoiceEntity.class);
                                if (userWithRestaurantChoiceEntity != null) {
                                    userWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity);
                                }
                            }
                            taskCompletionSource.setResult(userWithRestaurantChoiceEntities);
                        } else {
                            taskCompletionSource.setResult(new ArrayList<>());
                        }
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                }
            );
        try {
            return Tasks.await(taskCompletionSource.getTask());
        } catch (ExecutionException | InterruptedException e) {
            Log.e("NotificationWorker", "Error while getting users with restaurant choice entities", e);
            return new ArrayList<>();
        }
    }
}