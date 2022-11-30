package com.horseman.tracker.onBoarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.horseman.tracker.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingHolder> {

//    private int[] images = {R.drawable.ic_baseline_looks_one_24,R.drawable.ic_baseline_looks_two_24,R.drawable.ic_baseline_looks_3_24};
    private List<OnboardingModel> items;
    Context context;

    public OnboardingAdapter(List<OnboardingModel> items) {
        this.items = items;
    }

    //    public OnboardingAdapter(Context context) {
//        this.context = context;
//    }

    @NonNull
    @Override
    public OnboardingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onboarding_items,parent,false);
        return new OnboardingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingHolder holder, int position) {
        holder.setOnBoardingData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class OnboardingHolder extends RecyclerView.ViewHolder
    {
        TextView title,description;
        ImageView images;
        public OnboardingHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imageOnboarding);
            title = itemView.findViewById(R.id.txtTitleOnboarding);
            description = itemView.findViewById(R.id.txtDescriptionOnboarding);
        }

        public void setOnBoardingData(OnboardingModel onboardingModel) {
            title.setText(onboardingModel.getTitle());
            description.setText(onboardingModel.getDescription());
            images.setImageResource(onboardingModel.getImage());
        }
    }
}
