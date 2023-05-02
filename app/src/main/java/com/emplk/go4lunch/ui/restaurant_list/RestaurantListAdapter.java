package com.emplk.go4lunch.ui.restaurant_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.LoadingStateBinding;
import com.emplk.go4lunch.databinding.RestaurantItemBinding;
import com.emplk.go4lunch.databinding.RestaurantListErrorStateBinding;

public class RestaurantListAdapter extends ListAdapter<RestaurantListViewState, RecyclerView.ViewHolder> {

    private final OnRestaurantClickedListener listener;

    public RestaurantListAdapter(OnRestaurantClickedListener listener) {
        super(new ListRestaurantItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (RestaurantListViewState.Type.values()[viewType]) {
            case LOADING:
                return new RecyclerView.ViewHolder(LoadingStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot()
                ) {
                };
            case NO_GPS_CONNEXION:
                return new NoGPSConnexionViewHolder(RestaurantListErrorStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
                );
            case DISPLAY_RESTAURANT_LIST:
                return new RestaurantListViewHolder(RestaurantItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
                )
                    ;
            case NO_RESTAURANT_FOUND:
                return new NoRestaurantFoundViewHolder(RestaurantListErrorStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
                );
            case DATABASE_ERROR:
                return new DatabaseErrorViewHolder(RestaurantListErrorStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
                );
            default:
                throw new IllegalStateException("Unknown viewType : " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoGPSConnexionViewHolder) {
            ((NoGPSConnexionViewHolder) holder).bind((RestaurantListViewState.NoGpsConnexion) getItem(position));
        } else if (holder instanceof RestaurantListViewHolder) {
            ((RestaurantListViewHolder) holder).bind((RestaurantListViewState.RestaurantList) getItem(position), listener);
        } else if (holder instanceof NoRestaurantFoundViewHolder) {
            ((NoRestaurantFoundViewHolder) holder).bind((RestaurantListViewState.NoRestaurantFound) getItem(position));
        } else if (holder instanceof DatabaseErrorViewHolder) {
            ((DatabaseErrorViewHolder) holder).bind((RestaurantListViewState.DatabaseError) getItem(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    public static class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantItemBinding binding;

        public RestaurantListViewHolder(@NonNull RestaurantItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(
            @NonNull RestaurantListViewState.RestaurantList itemViewState,
            @NonNull OnRestaurantClickedListener listener
        ) {
            itemView.setOnClickListener(v -> listener.onRestaurantClicked(itemViewState.getId()));

            binding.listRestaurantName.setText(itemViewState.getName());
            binding.listRestaurantCuisine.setText(itemViewState.getCuisine());
            binding.listRestaurantAddress.setText(itemViewState.getAddress());
            binding.listRestaurantOpeningHours.setText(itemViewState.getOpeningHours());

            Glide.with(itemView.getContext())
                .load(itemViewState.getPictureUrl())
                .transform(new CenterCrop(), new RoundedCorners(25))
                .into(binding.listRestaurantPicture);

            binding.listRestaurantDistance.setText(itemViewState.getDistance());
            binding.listRestaurantAttendants.setText(itemViewState.getDistance());
            binding.listRestaurantRating.setRating(itemViewState.getRating());
        }
    }

    public static class NoGPSConnexionViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantListErrorStateBinding binding;

        public NoGPSConnexionViewHolder(RestaurantListErrorStateBinding binding) {
            super(binding.getRoot()); // mandatory when a class inherits from another (super) class
            this.binding = binding;
        }

        public void bind(@NonNull RestaurantListViewState.NoGpsConnexion item) {
            binding.restaurantListErrorTitle.setText(item.getNoGpsText());
            binding.restaurantListErrorImage.setImageResource(R.drawable.baseline_gps_off_24);
        }
    }

    public static class NoRestaurantFoundViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantListErrorStateBinding binding;

        public NoRestaurantFoundViewHolder(RestaurantListErrorStateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull RestaurantListViewState.NoRestaurantFound item) {
            binding.restaurantListErrorTitle.setText(item.getNoRestaurantFoundText());
            binding.restaurantListErrorImage.setImageResource(R.drawable.baseline_sad_face_24);
        }
    }

    public static class DatabaseErrorViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantListErrorStateBinding binding;

        public DatabaseErrorViewHolder(RestaurantListErrorStateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull RestaurantListViewState.DatabaseError item) {
            binding.restaurantListErrorTitle.setText(item.getDatabaseErrorText());
            binding.restaurantListErrorImage.setImageResource(R.drawable.baseline_network_off_24);
        }
    }


    private static class ListRestaurantItemCallback extends DiffUtil.ItemCallback<RestaurantListViewState> {
        @Override
        public boolean areItemsTheSame(@NonNull RestaurantListViewState oldItem, @NonNull RestaurantListViewState newItem) {
            return (
                oldItem instanceof RestaurantListViewState.Loading && newItem instanceof RestaurantListViewState.Loading
            ) || (
                oldItem instanceof RestaurantListViewState.RestaurantList && newItem instanceof RestaurantListViewState.RestaurantList &&
                    ((RestaurantListViewState.RestaurantList) oldItem).getId().equals(((RestaurantListViewState.RestaurantList) newItem).getId())
            ) || (
                oldItem instanceof RestaurantListViewState.NoGpsConnexion && newItem instanceof RestaurantListViewState.NoGpsConnexion
            ) || (
                oldItem instanceof RestaurantListViewState.NoRestaurantFound && newItem instanceof RestaurantListViewState.NoRestaurantFound
            ) || (
                oldItem instanceof RestaurantListViewState.DatabaseError && newItem instanceof RestaurantListViewState.DatabaseError
            );
        }

        @Override
        public boolean areContentsTheSame(@NonNull RestaurantListViewState oldItem, @NonNull RestaurantListViewState newItem) {
            return oldItem.equals(newItem);
        }
    }
}
