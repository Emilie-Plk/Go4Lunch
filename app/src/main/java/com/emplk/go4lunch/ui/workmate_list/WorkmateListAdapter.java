package com.emplk.go4lunch.ui.workmate_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
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
import com.emplk.go4lunch.databinding.WorkmatesItemBinding;

public class WorkmateListAdapter extends ListAdapter<WorkmatesViewStateItem, WorkmateListAdapter.ViewHolder> {

    @NonNull
    private final OnStartChatWithWorkmateListener listener;

    public WorkmateListAdapter(@NonNull OnStartChatWithWorkmateListener listener) {
        super(new ListWorkmateItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        WorkmatesItemBinding binding = WorkmatesItemBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
        @NonNull ViewHolder holder,
        int position
    ) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView workmateNameAndAttendingRestaurant;
        private final ImageView workmateAvatar;

        private final WorkmatesItemBinding binding;

        public ViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            this.workmateNameAndAttendingRestaurant = binding.listWorkmateNameAndRestaurant;
            this.workmateAvatar = binding.listWorkmateAvatar;
            this.binding = binding;
        }

        public void bind(
            @NonNull WorkmatesViewStateItem itemViewState,
            @NonNull OnStartChatWithWorkmateListener listener
        ) {
            binding.getRoot().setOnClickListener(v -> listener.onStartChatWithWorker(itemViewState.getId()));

            String workmateName = itemViewState.getName();
            String attendingRestaurant = itemViewState.getAttendingRestaurantName();

            String formattedText = binding.getRoot().getContext().getString(
                R.string.list_workmate_name_and_attenting_restaurant,
                workmateName,
                attendingRestaurant
            );

            binding.listWorkmateNameAndRestaurant.setText(formattedText);

            Glide.with(binding.getRoot())
                .load(itemViewState.getPhotoUrl())
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
            return oldItem.getId().equals(newItem.getId());
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
