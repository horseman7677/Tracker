package com.horseman.tracker.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.horseman.tracker.MainActivity;
import com.horseman.tracker.R;


public class UserProfileFragment extends Fragment {
    FirebaseAuth auth;
    TextView usernameText;
    DatabaseReference ref;
    String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.profile_user_fragment, null);
        usernameText = v.findViewById(R.id.usernameProfile);
        setHasOptionsMenu(true);
        getActivity().setTitle("Profile");
        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                username = result.getString("username");
                usernameText.setText(username);
                //System.out.println("username profile:-------------- " + username);
            }
        });


        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.user_profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd = item.getItemId();

        switch (idd) {
            case R.id.logout:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Logout() {
        auth.signOut();
        Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }

}
