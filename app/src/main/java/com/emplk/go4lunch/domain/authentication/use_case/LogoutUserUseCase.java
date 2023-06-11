package com.emplk.go4lunch.domain.authentication.use_case;

import androidx.annotation.NonNull;

<<<<<<< HEAD:app/src/main/java/com/emplk/go4lunch/domain/authentication/use_case/LogoutUserUseCase.java
import com.emplk.go4lunch.domain.authentication.AuthRepository;

=======
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283:app/src/main/java/com/emplk/go4lunch/domain/authentication/LogoutUserUseCase.java
import javax.inject.Inject;

public class LogoutUserUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public LogoutUserUseCase(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void invoke() {
        authRepository.signOut();
    }
}
