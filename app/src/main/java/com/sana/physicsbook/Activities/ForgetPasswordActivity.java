package com.sana.physicsbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sana.physicsbook.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText recoverEmailEt;
    Button forget_password_btn;
    ProgressDialog loadingbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recoverEmailEt = findViewById(R.id.recoverEmailId);
        forget_password_btn = findViewById(R.id.forget_password_btnId);
        loadingbar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        forget_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String emailRecover = recoverEmailEt.getText().toString();
                    beginRecovery(emailRecover);
                } catch (Exception e) {
                    Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void beginRecovery(String emailRecover) {

        loadingbar.setTitle("Sending new password to this email");
        loadingbar.show();

        mAuth.sendPasswordResetEmail(emailRecover)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            loadingbar.dismiss();
                            finish();
                            Toast.makeText(ForgetPasswordActivity.this, "Check Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}