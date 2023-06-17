package com.emplk.go4lunch.ui.workmate_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.NoWorkmateLayoutBinding;
import com.emplk.go4lunch.databinding.WorkmatesItemBinding;

public class WorkmateListAdapter extends ListAdapter<WorkmatesViewStateItem, RecyclerView.ViewHolder> {

    @NonNull
    private final OnWorkmateClickedListener listener;

    public WorkmateListAdapter(@NonNull OnWorkmateClickedListener listener) {
        super(new ListWorkmateItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        switch (WorkmatesViewStateItem.Type.values()[viewType]) {
            case ALL_WORKMATES:
                return new AllWorkMatesViewHolder(
                    WorkmatesItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false)
                );
            case WORKMATES_GOING_TO_SAME_RESTAURANT:
                return new WorkmatesGoingToSameRestaurantViewHolder(
                    WorkmatesItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false)
                );
            default:
                throw new IllegalStateException("Unknown viewType : " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(
        @NonNull RecyclerView.ViewHolder holder,
        int position
    ) {
        if (holder instanceof AllWorkMatesViewHolder) {
            ((AllWorkMatesViewHolder) holder).bind((WorkmatesViewStateItem.AllWorkmates) getItem(position), listener);
        } else if (holder instanceof WorkmatesGoingToSameRestaurantViewHolder) {
            ((WorkmatesGoingToSameRestaurantViewHolder) holder).bind((WorkmatesViewStateItem.WorkmatesGoingToSameRestaurant) getItem(position), listener);
        } else throw new IllegalStateException("Unknown item type at position: " + position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    public static class AllWorkMatesViewHolder extends RecyclerView.ViewHolder {
        private final TextView workmateNameAndAttendingRestaurant;
        private final ImageView workmateAvatar;

        private final WorkmatesItemBinding binding;

        public AllWorkMatesViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.workmateNameAndAttendingRestaurant = binding.listWorkmateNameAndRestaurant;
            this.workmateAvatar = binding.listWorkmateAvatar;
        }

        public void bind(
            @NonNull WorkmatesViewStateItem.AllWorkmates itemViewState,
            @NonNull OnWorkmateClickedListener listener
        ) {
            binding.getRoot().setOnClickListener(v -> listener.onWorkmateClicked(itemViewState.getId()));

            String workmateName = itemViewState.getName();
            String attendingRestaurant = itemViewState.getAttendingRestaurantName();

            String workmateWithRestaurantChoice = binding.getRoot().getContext().getString(
                R.string.list_workmate_name_and_attenting_restaurant,
                workmateName,
                attendingRestaurant
            );

            String workmateWithoutRestaurantChoice = binding.getRoot().getContext().getString(
                R.string.list_workmate_not_attending,
                workmateName
            );

            workmateNameAndAttendingRestaurant.setText(
                (itemViewState.getAttendingRestaurantId() != null) ? workmateWithRestaurantChoice : workmateWithoutRestaurantChoice);

            Glide.with(binding.getRoot())
                .load(itemViewState.getPictureUrl())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(workmateAvatar);
        }
    }

    public static class WorkmatesGoingToSameRestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView workmateName;
        private final ImageView workmateAvatar;
        private final ImageButton chatButton;
        private final WorkmatesItemBinding binding;


        public WorkmatesGoingToSameRestaurantViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.workmateName = binding.listWorkmateNameAndRestaurant;
            this.workmateAvatar = binding.listWorkmateAvatar;
            this.chatButton = binding.listWorkmateChatButton;
        }

        public void bind(
            @NonNull WorkmatesViewStateItem.WorkmatesGoingToSameRestaurant itemViewState,
            @NonNull OnWorkmateClickedListener listener
        ) {

            chatButton.setVisibility(View.VISIBLE);

            chatButton.setOnClickListener(v -> listener.onWorkmateClicked(itemViewState.getId()));  //TODO: add logic to start chat

            workmateName.setText(binding.getRoot().getContext().getString(
                R.string.detail_workmate_joining, itemViewState.getName()));

            Glide.with(binding.getRoot())
                .load(itemViewState.getPictureUrl())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(workmateAvatar);
        }
    }

    private static class ListWorkmateItemCallback extends DiffUtil.ItemCallback<WorkmatesViewStateItem> {
        @Override
        public boolean areItemsTheSame(
            @NonNull WorkmatesViewStateItem oldItem,
            @NonNull WorkmatesViewStateItem newItem
        ) {
            boolean bothAreAllWorkmatesList = oldItem instanceof WorkmatesViewStateItem.AllWorkmates && newItem instanceof WorkmatesViewStateItem.AllWorkmates;
            boolean bothAreWorkmatesGoingToSameRestaurantList = oldItem instanceof WorkmatesViewStateItem.WorkmatesGoingToSameRestaurant && newItem instanceof WorkmatesViewStateItem.WorkmatesGoingToSameRestaurant;
            return bothAreAllWorkmatesList || bothAreWorkmatesGoingToSameRestaurantList;
        }

        @Override
        public boolean areContentsTheSame(
            @NonNull WorkmatesViewStateItem oldItem,
            @NonNull WorkmatesViewStateItem newItem
        ) {
            return oldItem.equals(newItem);
        }
    }
}
