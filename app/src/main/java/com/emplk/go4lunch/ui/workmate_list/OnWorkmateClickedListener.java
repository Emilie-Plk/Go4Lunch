package com.emplk.go4lunch.ui.workmate_list;


import androidx.annotation.NonNull;

public interface OnWorkmateClickedListener {
    void onChatButtonClicked(
        @NonNull String workmateId,
        @NonNull String workmateName,
        @NonNull String workmatePhotoUrl
    );

    void onWorkmateClicked(@NonNull String restaurantId);
}
