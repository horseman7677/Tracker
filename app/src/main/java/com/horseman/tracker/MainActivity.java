package com.horseman.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.horseman.tracker.onBoarding.OnBoarding;
import com.horseman.tracker.user.UserDashboard;

public class MainActivity extends AppCompatActivity {

    TextView signupTxt;
    AppCompatButton signBtn;
    TextInputLayout username, password;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2C132C"));
        bar.setBackgroundDrawable(colorDrawable);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            Intent intent =new Intent(MainActivity.this,UserDashboard.class);
            System.out.println("check ------------------------------------------------------: "+auth.getCurrentUser().getUid().trim());
            intent.putExtra("uid",auth.getCurrentUser().getUid());
            startActivity(intent);
            finish();
        }

        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);
        signBtn = findViewById(R.id.signInBtnLogin);
        signupTxt = findViewById(R.id.signupTxtLogin);
        pd = new ProgressDialog(this);


        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));

            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("hold on!!!");
                pd.show();

                if (isValid()) {
                    username.setError("");
                    password.setError("");

                    String name = username.getEditText().getText().toString().trim();
                    String pass = password.getEditText().getText().toString().trim();

                    auth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                startActivity(new Intent(MainActivity.this, UserDashboard.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Toast.makeText(MainActivity.this, "200", Toast.LENGTH_SHORT).show();
                } else {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "credential required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValid() {
        String name = username.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();
//        System.out.println(name + " " + pass);

        if (name.equals("") && pass.equals("")) {
            username.setError("e-mail required");
            password.setError("password required");
            return false;
        } else if (!name.equals("") && pass.equals("")) {
            username.setError("");
            password.setError("password required");
            return false;
        } else if (name.equals("") && !pass.equals("")) {
            password.setError("");
            username.setError("e-mail required");
            return false;
        }
        return true;
    }
}