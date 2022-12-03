package com.horseman.tracker.user;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.horseman.tracker.R;

public class UserHomeFragment extends Fragment {
    FirebaseAuth auth;
    TextView userText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        auth = FirebaseAuth.getInstance();
        String user = auth.getCurrentUser().getEmail().toString().trim();
        View v = inflater.inflate(R.layout.home_user_fragment, null);

        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2C132C"));
        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                bar.setBackgroundDrawable(colorDrawable);
                break;

            default:
                break;
        }





        return v;
    }
}
