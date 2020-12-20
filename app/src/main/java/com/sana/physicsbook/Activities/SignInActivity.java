package com.sana.physicsbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sana.physicsbook.R;

public class SignInActivity extends AppCompatActivity {

    EditText email_signinEt, password_signinEt;
    Button signinBtn;
    TextView gotosignupBtn;
    TextView forget_passwordTv;
    ProgressDialog loadingbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();


        email_signinEt = findViewById(R.id.email_signin);
        password_signinEt = findViewById(R.id.password_signin);
        signinBtn = findViewById(R.id.signinBtnId);
        gotosignupBtn = findViewById(R.id.gotosignupId);
        forget_passwordTv = findViewById(R.id.forget_passwordId);
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Log In");
                loadingbar.setMessage("Please Wait, while we check your credentials...");
                loadingbar.show();

                String emailL = email_signinEt.getText().toString();
                String passwordL = password_signinEt.getText().toString().trim();

                if (emailL.isEmpty()) {
                    email_signinEt.setError("Email Field can't be empty");
                    loadingbar.dismiss();
                } else if (passwordL.isEmpty()) {
                    password_signinEt.setError("Password Field can't be empty");
                    loadingbar.dismiss();
                } else {
                    mAuth.signInWithEmailAndPassword(emailL, passwordL)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismiss();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        loadingbar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        gotosignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        forget_passwordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(SignInActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}