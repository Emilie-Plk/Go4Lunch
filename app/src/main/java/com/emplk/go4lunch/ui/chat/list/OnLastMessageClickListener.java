package com.emplk.go4lunch.ui.chat.list;

import androidx.annotation.NonNull;

public interface OnLastMessageClickListener {

    void onLastMessageClicked(
        @NonNull String workmateId,
        @NonNull String workmateName,
        @NonNull String workmatePictureUrl
    );
}
