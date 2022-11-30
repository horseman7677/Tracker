package com.horseman.tracker.onBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.horseman.tracker.MainActivity;
import com.horseman.tracker.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {

    ViewPager2 vp;
    private OnboardingAdapter adapter;
    private LinearLayout linearLayout;
    private MaterialButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        //this.getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(restorePrefData())
        {
            startActivity(new Intent(OnBoarding.this,MainActivity.class));
            finish();
        }

        linearLayout = findViewById(R.id.layoutOnboardingIndicators);
        btn = findViewById(R.id.onBoardingBtn);

        setOnboardingItem();

        vp = findViewById(R.id.viewPager);
        vp.setAdapter(adapter);

        setOnboardingIndicator();
        setCurrentOnboardingIndicators(0);

        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vp.getCurrentItem()+1 < adapter.getItemCount())
                {
                    vp.setCurrentItem(vp.getCurrentItem()+1);
                }else
                {
                    startActivity(new Intent(OnBoarding.this, MainActivity.class));
                    savePreference();
                    finish();
                }
            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroOpened = pref.getBoolean("isIntroOpened",false);
        return isIntroOpened;

    }

    private void savePreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();

    }

    private void setOnboardingIndicator() {
        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_looks_3_24));
            indicators[i].setLayoutParams(layoutParams);
            linearLayout.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicators(int position) {
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if(i==position)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator));

            }else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator2));
            }
        }
        if(position==adapter.getItemCount()-1)
        {
            btn.setText("Get Started");
        }else
        {
            btn.setText("Next");
        }
    }

    private void setOnboardingItem() {
        List<OnboardingModel> onBoardingItems = new ArrayList<>();

        OnboardingModel model = new OnboardingModel();
        model.setTitle("Capture");
        model.setDescription("capture the issue around you");
        model.setImage(R.drawable.firstpage);

        OnboardingModel model2 = new OnboardingModel();
        model2.setTitle("Share");
        model2.setDescription("share the issue with people");
        model2.setImage(R.drawable.community);

        OnboardingModel model3 = new OnboardingModel();
        model3.setTitle("Comment");
        model3.setDescription("comment on the issue");
        model3.setImage(R.drawable.comment);

        onBoardingItems.add(model);
        onBoardingItems.add(model2);
        onBoardingItems.add(model3);

        adapter = new OnboardingAdapter(onBoardingItems);
    }
}