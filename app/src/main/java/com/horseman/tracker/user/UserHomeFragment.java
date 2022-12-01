package com.horseman.tracker.user;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        auth = FirebaseAuth.getInstance();
        String user = auth.getCurrentUser().getEmail().toString().trim();
        View v = inflater.inflate(R.layout.home_user_fragment, null);

        userText = v.findViewById(R.id.topUsernameUser);
        //userText.setText(user);
        //System.out.println("user : " + user);


        return v;
    }
}
