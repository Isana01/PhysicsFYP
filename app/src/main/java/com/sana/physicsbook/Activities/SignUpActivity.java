package com.sana.physicsbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sana.physicsbook.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText email_signupEt, password_signupEt, cpassword_signupEt;
    TextView gotosigninTv;
    Button signupBtn;
    FirebaseAuth mAuth;
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        email_signupEt = findViewById(R.id.email_signup);
        password_signupEt = findViewById(R.id.password_signup);
        cpassword_signupEt = findViewById(R.id.cpassword_signup);
        signupBtn = findViewById(R.id.signupBtnId);
        gotosigninTv = findViewById(R.id.gotosigninId);
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        gotosigninTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingbar.setTitle("Registering");
                loadingbar.setMessage("Please Wait, while we register your data...");
                loadingbar.show();

                String emailR = email_signupEt.getText().toString();
                String passwordR = password_signupEt.getText().toString();
                String cpasswordR = cpassword_signupEt.getText().toString();

                if (!cpasswordR.equals(passwordR)) {
                    cpassword_signupEt.setError("Passwords don't match...");
                    loadingbar.dismiss();
                } else if (emailR.isEmpty()) {
                    email_signupEt.setError("Email Field can't be empty");
                    loadingbar.dismiss();
                } else if (passwordR.isEmpty()) {
                    password_signupEt.setError("Password Field can't be empty");
                    loadingbar.dismiss();
                } else {
                    mAuth.createUserWithEmailAndPassword(emailR, passwordR)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        loadingbar.dismiss();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {

                                        loadingbar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}