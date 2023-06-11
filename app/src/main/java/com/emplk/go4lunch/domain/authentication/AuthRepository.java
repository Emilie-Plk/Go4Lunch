package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface AuthRepository {

    @Nullable
    LoggedUserEntity getCurrentLoggedUser();

    @Nullable
    String getCurrentLoggedUserId();

<<<<<<< HEAD
    LiveData<Boolean> isUserLoggedLiveData();

=======
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
    void signOut();
}
