package com.emplk.go4lunch.ui.main.searchview;

import androidx.annotation.NonNull;

public interface OnPredictionClickedListener {
    void onPredictionClicked(
        @NonNull String placeId,
        @NonNull String restaurantName
    );
}
