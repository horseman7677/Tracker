package com.horseman.tracker.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.horseman.tracker.R;

public class UserDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BottomNavigationView navigationView = findViewById(R.id.user_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new UserHomeFragment()).commit();

        Bundle intent = getIntent().getExtras();
        String uid = intent.getString("uid");
        System.out.println("uid : "+uid);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId())
        {
            case R.id.homeUser:
                fragment = new UserHomeFragment();
                break;
            case R.id.profileUser:
                fragment = new UserProfileFragment();
                break;
            default:
                fragment = new UserHomeFragment();
                break;
        }
        return loadChefFragment(fragment);
    }

    private boolean loadChefFragment(Fragment fragment) {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
            return true;
        }
        return false;
    }
}