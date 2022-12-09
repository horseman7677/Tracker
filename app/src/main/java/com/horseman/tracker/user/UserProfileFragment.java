package com.horseman.tracker.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.horseman.tracker.MainActivity;
import com.horseman.tracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UserProfileFragment extends Fragment {
    FirebaseAuth auth;
    TextView usernameText;
    DatabaseReference ref;
    String username, eDate, eTime;
    FloatingActionButton fab1, fab2, fab3;
    MaterialAlertDialogBuilder dialogBuilder;
    ImageView imageView, zoomImg;
    Button postbtn;


    private static final int CAM_CODE = 1337;

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

        fab1 = v.findViewById(R.id.fab1);
        fab2 = v.findViewById(R.id.fab2);
        fab3 = v.findViewById(R.id.fab3);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fab2.getVisibility() != View.VISIBLE) {
                    fab2.setVisibility(View.VISIBLE);
                    fab3.setVisibility(View.VISIBLE);
                } else {
                    fab2.setVisibility(View.INVISIBLE);
                    fab3.setVisibility(View.INVISIBLE);
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab2.setVisibility(View.INVISIBLE);
                fab3.setVisibility(View.INVISIBLE);
                Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camIntent, CAM_CODE);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_CODE) {
//            imageView.setImageURI(data.getData());
            Bitmap image = (Bitmap) data.getExtras().get("data");
            openDiolog(image);

        }
    }

    @SuppressLint("MissingInflatedId")
    private void openDiolog(Bitmap data) {
        dialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        final View pop = getLayoutInflater().inflate(R.layout.camera_capture, null);
        postbtn = pop.findViewById(R.id.postBtn);
        imageView = pop.findViewById(R.id.captureImage);
        imageView.setImageBitmap(data);

        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                final View v = getLayoutInflater().inflate(R.layout.zoom_image, null);
                zoomImg = v.findViewById(R.id.zoomImage);
                zoomImg.setImageBitmap(data);

                builder.setView(v);
                builder.create().show();
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String date = simpleDateFormat.format(now);
        extractDateAndTime(date);

        System.out.println("date and time : " + eDate + " " + eTime);


        dialogBuilder.setView(pop);
        dialogBuilder.create().show();
    }

    private void extractDateAndTime(String date) {
        eDate = date.substring(0, 10).replace("_","-");
        eTime = date.substring(11).replace("_",":");
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
