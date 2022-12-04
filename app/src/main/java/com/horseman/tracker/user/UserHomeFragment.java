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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.horseman.tracker.R;

public class UserHomeFragment extends Fragment {
    FirebaseAuth auth;
    String uid ;
    String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Home");

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid().trim();
        //System.out.println("home uid : "+uid);

        View v = inflater.inflate(R.layout.home_user_fragment, null);

        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2C132C"));

        int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                bar.setBackgroundDrawable(colorDrawable);
                break;

            default:
                break;
        }

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("fullName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue().toString().trim();
                System.out.println("username home:-------------- " + username);
                //UserProfileFragment frag = new UserProfileFragment();
                Bundle args = new Bundle();
                args.putString("username",username);
                //frag.setArguments(args);
                getFragmentManager().setFragmentResult("data",args);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

    public String getUser() {
        return username;
    }
}
