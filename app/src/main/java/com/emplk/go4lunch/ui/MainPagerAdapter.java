package com.emplk.go4lunch.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.ui.restaurant_list.RestaurantListFragment;
import com.emplk.go4lunch.ui.workmate_list.WorkmateListFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    @StringRes
    private static final int[] NAV_TITLES = new int[]{R.string.map_bottom_nav, R.string.list_view_bottom_nav, R.string.workmates_bottom_nav};

    @NonNull
    private final Context context;

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        context = fragmentActivity.getApplicationContext();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return WorkmateListFragment.newInstance(); // to be replaced by the Map Fragment
            case 1:
                return RestaurantListFragment.newInstance();
            case 2:
                return WorkmateListFragment.newInstance();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }

    }

    @Override
    public int getItemCount() {
        return NAV_TITLES.length;
    }
}
