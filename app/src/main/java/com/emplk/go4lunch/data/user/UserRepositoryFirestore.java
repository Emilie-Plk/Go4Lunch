package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.ChosenRestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserRepositoryFirestore implements UserRepository {

    private static final String USERS_COLLECTION = "users";

    private static final String USERS_WITH_RESTAURANT_CHOICE = "usersWithRestaurantChoice";

    @NonNull
    private final FirebaseFirestore firestore;
    @NonNull
    private final Clock clock;

    @Inject
    public UserRepositoryFirestore(
        @NonNull FirebaseFirestore firestore,
        @NonNull Clock clock
    ) {
        this.firestore = firestore;
        this.clock = clock;
    }

    @Override
    public void upsertLoggedUserEntity(@Nullable LoggedUserEntity loggedUserEntity) {
        if (loggedUserEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_COLLECTION)
                .document(loggedUserEntity.getId());

            userDocumentRef
                .set(
                    new LoggedUserDto(
                        loggedUserEntity.getId(),
                        loggedUserEntity.getName(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getPictureUrl()
                    )
                )
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User document successfully created!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error creating user document: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userEntity is null!");
        }
    }


    @Override
    public void upsertUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull ChosenRestaurantEntity chosenRestaurantEntity
    ) {
        if (loggedUserEntity != null) {
            String userId = loggedUserEntity.getId();

            DocumentReference userWithRestaurantChoiceDocumentRef =
                firestore
                    .collection(USERS_WITH_RESTAURANT_CHOICE)
                    .document(userId);

            userWithRestaurantChoiceDocumentRef
                .set(
                    new UserWithRestaurantChoiceDto(
                        chosenRestaurantEntity.getTimestamp(),
                        chosenRestaurantEntity.getAttendingRestaurantId(),
                        chosenRestaurantEntity.getAttendingRestaurantName(),
                        chosenRestaurantEntity.getAttendingRestaurantVicinity(),
                        chosenRestaurantEntity.getAttendingRestaurantPictureUrl()
                    )
                )
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User document successfully created!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error creating user document: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userEntity is null!");
        }
    }

    @Override
    public LiveData<List<UserWithRestaurantChoiceEntity>> getUsersWithRestaurantChoiceEntities() {
        MutableLiveData<List<UserWithRestaurantChoiceEntity>> usersWithRestaurantChoiceEntitiesMutableLiveData = new MutableLiveData<>();

        LocalDateTime startDateTime = getStartDateTime();
        Timestamp startTimestamp = getStartTimestamp(startDateTime);
        Timestamp endTimestamp = getEndTimestamp(startDateTime);

        firestore
            .collection(USERS_WITH_RESTAURANT_CHOICE)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                if (error != null) {
                    Log.e("UserRepositoryFirestore", "Error fetching users documents: " + error);
                    usersWithRestaurantChoiceEntitiesMutableLiveData.setValue(null);
                    return;
                }
                if (queryDocumentSnapshots != null) {
                    List<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntities = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        UserWithRestaurantChoiceDto userWithRestaurantChoiceDto = documentSnapshot.toObject(UserWithRestaurantChoiceDto.class);
                        Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");
                        if (timestamp != null &&
                            timestamp.compareTo(startTimestamp) >= 0 &&
                            timestamp.compareTo(endTimestamp) <= 0) {
                            String userId = documentSnapshot.getId();
                            UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = mapToUserWithRestaurantChoiceEntity(userWithRestaurantChoiceDto, userId);
                            userWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity);
                        }
                    }
                    if (userWithRestaurantChoiceEntities.isEmpty()) {
                        usersWithRestaurantChoiceEntitiesMutableLiveData.setValue(null);
                    } else {
                        usersWithRestaurantChoiceEntitiesMutableLiveData.setValue(userWithRestaurantChoiceEntities);
                    }
                }
            }
            );
        return usersWithRestaurantChoiceEntitiesMutableLiveData;
    }

    public LiveData<UserWithRestaurantChoiceEntity> getUserWithRestaurantChoiceEntity(@NonNull String userId) {
        MutableLiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityMutableLiveData = new MutableLiveData<>();

        LocalDateTime startDateTime = getStartDateTime();
        Timestamp startTimestamp = getStartTimestamp(startDateTime);
        Timestamp endTimestamp = getEndTimestamp(startDateTime);

        firestore
            .collection(USERS_WITH_RESTAURANT_CHOICE)
            .document(userId)
            .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching user document: " + error);
                        userWithRestaurantChoiceEntityMutableLiveData.setValue(null);
                        return;
                    }

                    if (documentSnapshot != null) {
                        UserWithRestaurantChoiceDto userWithRestaurantChoiceDto = documentSnapshot.toObject(UserWithRestaurantChoiceDto.class);
                        if (userWithRestaurantChoiceDto != null) {
                            Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");
                            if (timestamp != null &&
                                timestamp.compareTo(startTimestamp) >= 0 &&
                                timestamp.compareTo(endTimestamp) <= 0) {
                                UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = mapToUserWithRestaurantChoiceEntity(userWithRestaurantChoiceDto, userId);
                                userWithRestaurantChoiceEntityMutableLiveData.setValue(userWithRestaurantChoiceEntity);
                            } else {
                                userWithRestaurantChoiceEntityMutableLiveData.setValue(null);
                            }
                        } else {
                            userWithRestaurantChoiceEntityMutableLiveData.setValue(null);
                        }
                    } else {
                        userWithRestaurantChoiceEntityMutableLiveData.setValue(null);
                    }
                }
            );

        return userWithRestaurantChoiceEntityMutableLiveData;
    }

    @Override
    public void deleteUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity) {
        if (loggedUserEntity != null) {
            firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(loggedUserEntity.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User's restaurant choice successfully deleted!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error deleting user's restaurant choice: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error deleting user's restaurant choice: userEntity is null!");
        }
    }

    @Override
    public LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesLiveData() {
        MutableLiveData<List<LoggedUserEntity>> loggedUserEntitiesMutableLiveData = new MutableLiveData<>();

        firestore
            .collection(USERS_COLLECTION)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching users documents: " + error);
                        loggedUserEntitiesMutableLiveData.setValue(null);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        List<LoggedUserDto> loggedUserDtos = queryDocumentSnapshots.toObjects(LoggedUserDto.class);
                        List<LoggedUserEntity> loggedUserEntities = new ArrayList<>();
                        for (LoggedUserDto loggedUserDto : loggedUserDtos) {
                            loggedUserEntities.add(mapToLoggedUserEntity(loggedUserDto));
                        }
                        loggedUserEntitiesMutableLiveData.setValue(loggedUserEntities);
                    }
                }
            );
        return loggedUserEntitiesMutableLiveData;
    }

    private LoggedUserEntity mapToLoggedUserEntity(@Nullable LoggedUserDto result) {
        if (result != null &&
            result.getId() != null &&
            result.getName() != null &&
            result.getEmail() != null &&
            result.getPictureUrl() != null
        ) {
            return new LoggedUserEntity(
                result.getId(),
                result.getName(),
                result.getEmail(),
                result.getPictureUrl()
            );
        } else {
            return null;
        }
    }

    private UserWithRestaurantChoiceEntity mapToUserWithRestaurantChoiceEntity(
        UserWithRestaurantChoiceDto userWithRestaurantChoiceDto,
        String userId
    ) {
        if (userWithRestaurantChoiceDto.getTimestamp() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantId() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantName() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantVicinity() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantPictureUrl() != null
        ) {
            return new UserWithRestaurantChoiceEntity(
                userId,
                userWithRestaurantChoiceDto.getTimestamp(),
                userWithRestaurantChoiceDto.getAttendingRestaurantId(),
                userWithRestaurantChoiceDto.getAttendingRestaurantName(),
                userWithRestaurantChoiceDto.getAttendingRestaurantVicinity(),
                userWithRestaurantChoiceDto.getAttendingRestaurantPictureUrl()
            );
        } else {
            return null;
        }
    }

    private LocalDateTime getStartDateTime() {
        LocalDate currentDate = LocalDate.now(clock);
        return LocalDateTime.of(currentDate, LocalTime.of(12,30, 0, 0));
    }

    private Timestamp getStartTimestamp(LocalDateTime dateTime) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zone);
        return new Timestamp(zonedDateTime.toInstant().getEpochSecond(), zonedDateTime.toInstant().getNano());
    }

    private Timestamp getEndTimestamp(LocalDateTime dateTime) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime endDateTime = dateTime.plusDays(1).with(LocalTime.of(12, 29, 59, 999999999));
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, zone);
        return new Timestamp(endZonedDateTime.toInstant().getEpochSecond(), endZonedDateTime.toInstant().getNano());
    }
}


