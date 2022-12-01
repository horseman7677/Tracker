package com.horseman.tracker.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.horseman.tracker.R;

public class UserProfileFragment extends Fragment {
    FirebaseAuth auth;
    TextView userText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        String user = auth.getCurrentUser().getEmail().toString().trim();
        View v = inflater.inflate(R.layout.profile_user_fragment,null);

        userText = v.findViewById(R.id.topUsernameProfile);
        userText.setText(user);

        return v;
    }
}
