package com.sana.physicsbook.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sana.physicsbook.R;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {

    String quiz;
    TextView r1Tv, r2Tv, r3Tv, r4Tv, r5Tv, r6Tv, r7Tv, r8Tv, r9Tv, r10Tv, totalMarksTv;
    RadioButton rb1Yes, rb1No, rb2Yes, rb2No, rb3Yes, rb3No, rb4Yes, rb4No, rb5Yes, rb5No,
            rb6Yes, rb6No, rb7Yes, rb7No, rb8Yes, rb8No, rb9Yes, rb9No, rb10Yes, rb10No;
    String r1Link, r2Link, r3Link, r4Link, r5Link, r6Link, r7Link, r8Link, r9Link, r10Link;
    String ans1, ans2, ans3, ans4, ans5, ans6, ans7, ans8, ans9, ans10;
    Button submitQuizBtn;
    ImageView true1, true2, true3, true4, true5, true6, true7, true8, true9, true10;
    ImageView false1, false2, false3, false4, false5, false6, false7, false8, false9, false10;
    DatabaseReference reference, reference2, refAnswer;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressDialog loadingbar;
    String ans;
    int counter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);

        quiz = getArguments().getString("quiz");
        getActivity().setTitle(quiz);

        totalMarksTv = view.findViewById(R.id.totalMarksTvId);
        r1Tv = view.findViewById(R.id.r1);
        r2Tv = view.findViewById(R.id.r2);
        r3Tv = view.findViewById(R.id.r3);
        r4Tv = view.findViewById(R.id.r4);
        r5Tv = view.findViewById(R.id.r5);
        r6Tv = view.findViewById(R.id.r6);
        r7Tv = view.findViewById(R.id.r7);
        r8Tv = view.findViewById(R.id.r8);
        r9Tv = view.findViewById(R.id.r9);
        r10Tv = view.findViewById(R.id.r10);

        rb1Yes = view.findViewById(R.id.ansR1Yes);
        rb1No = view.findViewById(R.id.ansR1No);
        rb2Yes = view.findViewById(R.id.ansR2Yes);
        rb2No = view.findViewById(R.id.ansR2No);
        rb3Yes = view.findViewById(R.id.ansR3Yes);
        rb3No = view.findViewById(R.id.ansR3No);
        rb4Yes = view.findViewById(R.id.ansR4Yes);
        rb4No = view.findViewById(R.id.ansR4No);
        rb5Yes = view.findViewById(R.id.ansR5Yes);
        rb5No = view.findViewById(R.id.ansR5No);
        rb6Yes = view.findViewById(R.id.ansR6Yes);
        rb6No = view.findViewById(R.id.ansR6No);
        rb7Yes = view.findViewById(R.id.ansR7Yes);
        rb7No = view.findViewById(R.id.ansR7No);
        rb8Yes = view.findViewById(R.id.ansR8Yes);
        rb8No = view.findViewById(R.id.ansR8No);
        rb9Yes = view.findViewById(R.id.ansR9Yes);
        rb9No = view.findViewById(R.id.ansR9No);
        rb10Yes = view.findViewById(R.id.ansR10Yes);
        rb10No = view.findViewById(R.id.ansR10No);

        true1 = view.findViewById(R.id.true1);
        true2 = view.findViewById(R.id.true2);
        true3 = view.findViewById(R.id.true3);
        true4 = view.findViewById(R.id.true4);
        true5 = view.findViewById(R.id.true5);
        true6 = view.findViewById(R.id.true6);
        true7 = view.findViewById(R.id.true7);
        true8 = view.findViewById(R.id.true8);
        true9 = view.findViewById(R.id.true9);
        true10 = view.findViewById(R.id.true10);

        false1 = view.findViewById(R.id.false1);
        false2 = view.findViewById(R.id.false2);
        false3 = view.findViewById(R.id.false3);
        false4 = view.findViewById(R.id.false4);
        false5 = view.findViewById(R.id.false5);
        false6 = view.findViewById(R.id.false6);
        false7 = view.findViewById(R.id.false7);
        false8 = view.findViewById(R.id.false8);
        false9 = view.findViewById(R.id.false9);
        false10 = view.findViewById(R.id.false10);

        submitQuizBtn = view.findViewById(R.id.submitQuizBtnId);

        loadingbar = new ProgressDialog(getActivity());

        reference = FirebaseDatabase.getInstance().getReference().child("Quiz").child(quiz);
        reference2 = FirebaseDatabase.getInstance().getReference().child("QuizResults").child(quiz);
        refAnswer = FirebaseDatabase.getInstance().getReference().child("Answers");

        refAnswer.child(quiz).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ans = snapshot.child("answer").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    String r1 = snapshot.child("R1").getValue().toString();

                    r1Tv.setText(r1);

                } catch (Exception e) {
                    r1Tv.setVisibility(View.GONE);
                    rb1Yes.setVisibility(View.GONE);
                    rb1No.setVisibility(View.GONE);
                }

                try {
                    String r2 = snapshot.child("R2").getValue().toString();

                    r2Tv.setText(r2);

                } catch (Exception e) {
                    r2Tv.setVisibility(View.GONE);
                    rb2Yes.setVisibility(View.GONE);
                    rb2No.setVisibility(View.GONE);
                }

                try {
                    String r3 = snapshot.child("R3").getValue().toString();

                    r3Tv.setText(r3);

                } catch (Exception e) {
                    r3Tv.setVisibility(View.GONE);
                    rb3Yes.setVisibility(View.GONE);
                    rb3No.setVisibility(View.GONE);
                }

                try {
                    String r4 = snapshot.child("R4").getValue().toString();

                    r4Tv.setText(r4);

                } catch (Exception e) {
                    r4Tv.setVisibility(View.GONE);
                    rb4Yes.setVisibility(View.GONE);
                    rb4No.setVisibility(View.GONE);
                }

                try {
                    String r5 = snapshot.child("R5").getValue().toString();

                    r5Tv.setText(r5);

                } catch (Exception e) {
                    r5Tv.setVisibility(View.GONE);
                    rb5Yes.setVisibility(View.GONE);
                    rb5No.setVisibility(View.GONE);
                }

                try {
                    String r6 = snapshot.child("R6").getValue().toString();

                    r6Tv.setText(r6);

                } catch (Exception e) {
                    r6Tv.setVisibility(View.GONE);
                    rb6Yes.setVisibility(View.GONE);
                    rb6No.setVisibility(View.GONE);
                }

                try {
                    String r7 = snapshot.child("R7").getValue().toString();

                    r7Tv.setText(r7);

                } catch (Exception e) {
                    r7Tv.setVisibility(View.GONE);
                    rb7Yes.setVisibility(View.GONE);
                    rb7No.setVisibility(View.GONE);
                }

                try {
                    String r8 = snapshot.child("R8").getValue().toString();

                    r8Tv.setText(r8);

                } catch (Exception e) {
                    r8Tv.setVisibility(View.GONE);
                    rb8Yes.setVisibility(View.GONE);
                    rb8No.setVisibility(View.GONE);
                }

                try {
                    String r9 = snapshot.child("R9").getValue().toString();

                    r9Tv.setText(r9);

                } catch (Exception e) {
                    r9Tv.setVisibility(View.GONE);
                    rb9Yes.setVisibility(View.GONE);
                    rb9No.setVisibility(View.GONE);
                }

                try {
                    String r10 = snapshot.child("R10").getValue().toString();

                    r10Tv.setText(r10);

                } catch (Exception e) {
                    r10Tv.setVisibility(View.GONE);
                    rb10Yes.setVisibility(View.GONE);
                    rb10No.setVisibility(View.GONE);
                }

                try {
                    ans1 = snapshot.child("Ans1").getValue().toString();
                    ans2 = snapshot.child("Ans2").getValue().toString();
                    ans3 = snapshot.child("Ans3").getValue().toString();
                    ans4 = snapshot.child("Ans4").getValue().toString();
                    ans5 = snapshot.child("Ans5").getValue().toString();
                    ans6 = snapshot.child("Ans6").getValue().toString();
                    ans7 = snapshot.child("Ans7").getValue().toString();
                    ans8 = snapshot.child("Ans8").getValue().toString();
                    ans9 = snapshot.child("Ans9").getValue().toString();
                    ans10 = snapshot.child("Ans10").getValue().toString();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submitQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to submit the quiz?");
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        loadingbar.setTitle("Submitting");
                        loadingbar.show();

                        if (rb1Yes.isChecked()) {
                            r1Link = "Yes";
                        }
                        if (rb1No.isChecked()) {
                            r1Link = "No";
                        }
                        if (!rb1Yes.isChecked() && !rb1No.isChecked()) {
                            r1Link = "Null";
                        }


                        if (rb2Yes.isChecked()) {
                            r2Link = "Yes";
                        }
                        if (rb2No.isChecked()) {
                            r2Link = "No";
                        }
                        if (!rb2Yes.isChecked() && !rb2No.isChecked()) {
                            r2Link = "Null";
                        }

                        if (rb3Yes.isChecked()) {
                            r3Link = "Yes";
                        }
                        if (rb3No.isChecked()) {
                            r3Link = "No";
                        }
                        if (!rb3Yes.isChecked() && !rb3No.isChecked()) {
                            r3Link = "Null";
                        }

                        if (rb4Yes.isChecked()) {
                            r4Link = "Yes";
                        }
                        if (rb4No.isChecked()) {
                            r4Link = "No";
                        }
                        if (!rb4Yes.isChecked() && !rb4No.isChecked()) {
                            r4Link = "Null";
                        }

                        if (rb5Yes.isChecked()) {
                            r5Link = "Yes";
                        }
                        if (rb5No.isChecked()) {
                            r5Link = "No";
                        }
                        if (!rb5Yes.isChecked() && !rb5No.isChecked()) {
                            r5Link = "Null";
                        }

                        if (rb6Yes.isChecked()) {
                            r6Link = "Yes";
                        }
                        if (rb6No.isChecked()) {
                            r6Link = "No";
                        }
                        if (!rb6Yes.isChecked() && !rb6No.isChecked()) {
                            r6Link = "Null";
                        }

                        if (rb7Yes.isChecked()) {
                            r7Link = "Yes";
                        }
                        if (rb7No.isChecked()) {
                            r7Link = "No";
                        }
                        if (!rb7Yes.isChecked() && !rb7No.isChecked()) {
                            r7Link = "Null";
                        }

                        if (rb8Yes.isChecked()) {
                            r8Link = "Yes";
                        }
                        if (rb8No.isChecked()) {
                            r8Link = "No";
                        }
                        if (!rb8Yes.isChecked() && !rb8No.isChecked()) {
                            r8Link = "Null";
                        }

                        if (rb9Yes.isChecked()) {
                            r9Link = "Yes";
                        }
                        if (rb9No.isChecked()) {
                            r9Link = "No";
                        }
                        if (!rb9Yes.isChecked() && !rb9No.isChecked()) {
                            r9Link = "Null";
                        }

                        if (rb10Yes.isChecked()) {
                            r10Link = "Yes";
                        }
                        if (rb10No.isChecked()) {
                            r10Link = "No";
                        }
                        if (!rb10Yes.isChecked() && !rb10No.isChecked()) {
                            r10Link = "Null";
                        }

                        if (r1Link.equals(ans1)) {
                            counter = counter + 1;
                            true1.setVisibility(View.VISIBLE);
                        } else {
                            false1.setVisibility(View.VISIBLE);
                        }

                        if (r2Link.equals(ans2)) {
                            counter = counter + 1;
                            true2.setVisibility(View.VISIBLE);
                        } else {
                            false2.setVisibility(View.VISIBLE);
                        }

                        if (r3Link.equals(ans3)) {
                            counter = counter + 1;
                            true3.setVisibility(View.VISIBLE);
                        } else {
                            false3.setVisibility(View.VISIBLE);
                        }

                        if (r4Link.equals(ans4)) {
                            counter = counter + 1;
                            true4.setVisibility(View.VISIBLE);
                        } else {
                            false4.setVisibility(View.VISIBLE);
                        }

                        if (r5Link.equals(ans5)) {
                            counter = counter + 1;
                            true5.setVisibility(View.VISIBLE);
                        } else {
                            false5.setVisibility(View.VISIBLE);
                        }

                        if (r6Link.equals(ans6)) {
                            counter = counter + 1;
                            true6.setVisibility(View.VISIBLE);
                        } else {
                            false6.setVisibility(View.VISIBLE);
                        }

                        if (r7Link.equals(ans7)) {
                            counter = counter + 1;
                            true7.setVisibility(View.VISIBLE);
                        } else {
                            false7.setVisibility(View.VISIBLE);
                        }

                        if (r8Link.equals(ans8)) {
                            counter = counter + 1;
                            true8.setVisibility(View.VISIBLE);
                        } else {
                            false8.setVisibility(View.VISIBLE);
                        }

                        if (r9Link.equals(ans9)) {
                            counter = counter + 1;
                            true9.setVisibility(View.VISIBLE);
                        } else {
                            false9.setVisibility(View.VISIBLE);
                        }

                        if (r10Link.equals(ans10)) {
                            counter = counter + 1;
                            true10.setVisibility(View.VISIBLE);
                        } else {
                            false10.setVisibility(View.VISIBLE);
                        }

                        totalMarksTv.setText(counter + "/10");

                        Map<String, Object> map = new HashMap<>();
                        map.put("R1", r1Link);
                        map.put("R2", r2Link);
                        map.put("R3", r3Link);
                        map.put("R4", r4Link);
                        map.put("R5", r5Link);
                        map.put("R6", r6Link);
                        map.put("R7", r7Link);
                        map.put("R8", r8Link);
                        map.put("R9", r9Link);
                        map.put("R10", r10Link);
                        map.put("Total Marks", totalMarksTv.getText().toString());

                        reference2.child(mAuth.getCurrentUser().getUid()).push().setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            loadingbar.dismiss();
                                            Toast.makeText(getActivity(), "Quiz Submitted", Toast.LENGTH_SHORT).show();
                                            counter = 0;
                                            submitQuizBtn.setVisibility(View.INVISIBLE);
                                        } else {
                                            Toast.makeText(getActivity(), "Quiz Submit Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog d = builder.create();
                d.show();
            }
        });

        return view;
    }
}