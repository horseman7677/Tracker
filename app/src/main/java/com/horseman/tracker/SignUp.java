package com.horseman.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    TextView signinTxt;
    AppCompatButton signUpBtn;
    TextInputLayout fullName, email, password;
    ProgressDialog pd;

    FirebaseAuth auth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signinTxt = findViewById(R.id.signinTxtLogin);
        signUpBtn = findViewById(R.id.signUpBtnSignup);
        fullName = findViewById(R.id.fullNameSignup);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        signinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("hold on!!!");
                pd.show();

                if (isValid()) {
                    fullName.setError("");
                    email.setError("");
                    password.setError("");

                    String name = fullName.getEditText().getText().toString().trim();
                    String mail = email.getEditText().getText().toString().trim();
                    String pass = password.getEditText().getText().toString().trim();


                    //System.out.println("cred : "+name + " " + mail + " " + pass);

                    auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().trim();
                            HashMap<String, String> data = new HashMap<>();

                            data.put("fullName", name);
                            data.put("email", mail);
                            data.put("password", pass);
                            data.put("uid", uid);

                            ref = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                            ref.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pd.dismiss();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(SignUp.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean isValid() {
        String name = fullName.getEditText().getText().toString().trim();
        String mail = email.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (name.equals("") && pass.equals("") && mail.equals("")) {
            fullName.setError("fullname required");
            email.setError("e-mail required");
            password.setError("password required");
            return false;
        } else if ((pass.equals("") && mail.equals(""))) {
            fullName.setError("");
            password.setError("password required");
            return false;
        } else if ((name.equals("") && mail.equals("")) && !pass.equals("")) {
            password.setError("");
            email.setError("e-mail required");
            return false;
        } else if (!mail.equals("") && (name.equals("") && pass.equals(""))) {
            return false;
        } else if (name.equals("")) {
            return false;
        } else if (mail.equals("")) {
            return false;
        } else if (pass.equals("")) {
            return false;
        }

        return true;
    }
}