package com.sana.physicsbook.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sana.physicsbook.R;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedBackFragment extends Fragment {

    EditText messageEt, subjectEt;
    Button sendFeedbackBtn;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog loadingbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);
        getActivity().setTitle("FeedBack");

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

        messageEt = view.findViewById(R.id.messageId);
        subjectEt = view.findViewById(R.id.subjectId);
        sendFeedbackBtn = view.findViewById(R.id.sendFeedbackBtnId);
        loadingbar = new ProgressDialog(getActivity());

        sendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Sending Feedback");
                loadingbar.show();

                String subject = subjectEt.getText().toString();
                String message = messageEt.getText().toString();

                if (subject.isEmpty()) {
                    loadingbar.dismiss();
                    subjectEt.setError("Enter Subject");
                }
                if (message.isEmpty()) {
                    loadingbar.dismiss();
                    messageEt.setError("Enter message");
                } else {

                    Map<String, Object> map = new HashMap<>();
                    map.put("subject", subject);
                    map.put("message", message);
                    map.put("student Email", mAuth.getCurrentUser().getEmail());

                    reference.child(mAuth.getCurrentUser().getUid()).push().setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismiss();
                                        Toast.makeText(getActivity(), "Feedback Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        loadingbar.dismiss();
                                        Toast.makeText(getActivity(), "Feedback Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });


        return view;
    }
}
