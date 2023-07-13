package com.emplk.go4lunch.domain.notification;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetNotificationEntityUseCaseTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private GetNotificationEntityUseCase getNotificationEntityUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.TEST_USER_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        getNotificationEntityUseCase = new GetNotificationEntityUseCase(userRepository, getCurrentLoggedUserIdUseCase);
    }

    @Test
    public void notificationEntity_withTwoWorkmatesGoingToSameRestaurant() {
        // Given
        List<UserWithRestaurantChoiceEntity> usersWithRestaurantChoiceEntities = new ArrayList<>();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity1 = Stubs.getTestUserWithSameRestaurantChoiceEntity();
        UserWithRestaurantChoiceEntity currentUserWithRestaurantChoiceEntity = Stubs.getTestCurrentUserWithRestaurantChoiceEntity();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity2 = Stubs.getTestUserWithSameRestaurantChoiceEntity();
        usersWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity1);
        usersWithRestaurantChoiceEntities.add(currentUserWithRestaurantChoiceEntity);
        usersWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity2);
        doReturn(usersWithRestaurantChoiceEntities).when(userRepository).getUsersWithRestaurantChoiceEntitiesAsync();


        List<String> expectedWorkmatesNames = Arrays.asList(
            Stubs.getTestUserWithDifferentRestaurantChoiceEntity(null).getAttendingUsername(),
            Stubs.getTestUserWithDifferentRestaurantChoiceEntity( null).getAttendingUsername()
        );

        // When
        NotificationEntity expectedNotificationEntity = Stubs.getTestNotificationEntity(expectedWorkmatesNames);
        NotificationEntity result = getNotificationEntityUseCase.invoke();


        // Then

        verify(getCurrentLoggedUserIdUseCase).invoke();
        verify(userRepository).getUsersWithRestaurantChoiceEntitiesAsync();
        assertEquals(expectedNotificationEntity, result);
        verifyNoMoreInteractions(getCurrentLoggedUserIdUseCase, userRepository);
    }

    @Test
    public void notificationEntity_withNoWorkmatesGoingToSameRestaurant() {
        // Given
        List<UserWithRestaurantChoiceEntity> usersWithRestaurantChoiceEntities = new ArrayList<>();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity1 = Stubs.getTestUserWithDifferentRestaurantChoiceEntity("123");
        UserWithRestaurantChoiceEntity currentUserWithRestaurantChoiceEntity = Stubs.getTestCurrentUserWithRestaurantChoiceEntity();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity2 = Stubs.getTestUserWithDifferentRestaurantChoiceEntity("456");
        usersWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity1);
        usersWithRestaurantChoiceEntities.add(currentUserWithRestaurantChoiceEntity);
        usersWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity2);
        doReturn(usersWithRestaurantChoiceEntities).when(userRepository).getUsersWithRestaurantChoiceEntitiesAsync();

        // When

        // Then

        NotificationEntity result = getNotificationEntityUseCase.invoke();


        verify(getCurrentLoggedUserIdUseCase).invoke();
        verify(userRepository).getUsersWithRestaurantChoiceEntitiesAsync();
        assertEquals(0, result.getWorkmates().size());
        verifyNoMoreInteractions(getCurrentLoggedUserIdUseCase, userRepository);
    }

    @Test
    public void edgeCase() {


    }
}