package com.emplk.go4lunch.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class BitmapFromVector {
    public static BitmapDescriptor getBitmapFromVector(
        Context context,
        int vectorResId,
        @ColorRes int colorResId
    ) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        if (vectorDrawable != null) {
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
            int color = ContextCompat.getColor(context, colorResId);
            vectorDrawable.setTint(color);
        }
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable != null ? vectorDrawable.getIntrinsicWidth() : 0,
            vectorDrawable != null ? vectorDrawable.getIntrinsicHeight() : 0, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (vectorDrawable != null) {
            vectorDrawable.draw(canvas);
        }
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
