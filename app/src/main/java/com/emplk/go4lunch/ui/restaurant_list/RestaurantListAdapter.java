package com.emplk.go4lunch.ui.restaurant_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.emplk.go4lunch.databinding.RestaurantItemBinding;

public class RestaurantListAdapter extends ListAdapter<RestaurantItemViewState, RestaurantListAdapter.ViewHolder> {

    private final OnRestaurantClickedListener listener;

    public RestaurantListAdapter(OnRestaurantClickedListener listener) {
        super(new ListRestaurantItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemBinding binding = RestaurantItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView cuisine;
        private final TextView address;
        private final TextView openingHours;
        private final ImageView picture;
        private final TextView distance;
        private final TextView attendants;
        private final RatingBar rating;

        public ViewHolder(@NonNull RestaurantItemBinding binding) {
            super(binding.getRoot());
            name = binding.listRestaurantName;
            cuisine = binding.listRestaurantCuisine;
            address = binding.listRestaurantAddress;
            openingHours = binding.listRestaurantOpeningHours;
            picture = binding.listRestaurantPicture;
            distance = binding.listRestaurantDistance;
            attendants = binding.listRestaurantAttendants;
            rating = binding.listRestaurantRating;
        }

        public void bind(
            @NonNull RestaurantItemViewState itemViewState,
            @NonNull OnRestaurantClickedListener listener
        ) {
            itemView.setOnClickListener(v -> listener.onRestaurantClicked(itemViewState.getId()));

            name.setText(itemViewState.getName());
            cuisine.setText(itemViewState.getCuisine());
            address.setText(itemViewState.getAddress());
            openingHours.setText(itemViewState.getOpeningHours());

            Glide.with(itemView.getContext())
                .load(itemViewState.getPictureUrl())
                .transform(new CenterCrop(), new RoundedCorners(25))
                .into(picture);

            distance.setText(itemViewState.getDistance());
            attendants.setText(itemViewState.getDistance());
            rating.setRating(itemViewState.getRating());
        }

    }

    private static class ListRestaurantItemCallback extends DiffUtil.ItemCallback<RestaurantItemViewState> {
        @Override
        public boolean areItemsTheSame(@NonNull RestaurantItemViewState oldItem, @NonNull RestaurantItemViewState newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull RestaurantItemViewState oldItem, @NonNull RestaurantItemViewState newItem) {
            return oldItem.equals(newItem);
        }
    }
}
