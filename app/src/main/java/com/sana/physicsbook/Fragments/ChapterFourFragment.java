package com.sana.physicsbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sana.physicsbook.Activities.Ch3Data1Activity;
import com.sana.physicsbook.Activities.Ch3Data2Activity;
import com.sana.physicsbook.Activities.Ch3Data3Activity;
import com.sana.physicsbook.Activities.Ch3Data4Activity;
import com.sana.physicsbook.Activities.Ch4Data1Activity;
import com.sana.physicsbook.Activities.Ch4Data2Activity;
import com.sana.physicsbook.Activities.Ch4Data3Activity;
import com.sana.physicsbook.Activities.Ch4Data4Activity;
import com.sana.physicsbook.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChapterFourFragment extends Fragment {

    TextView fourTv1, fourTv2, fourTv3, fourTv4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_four_fragment, container, false);
        getActivity().setTitle("Chapter 4");

        fourTv1 = view.findViewById(R.id.fourTv1);
        fourTv2 = view.findViewById(R.id.fourTv2);
        fourTv3 = view.findViewById(R.id.fourTv3);
        fourTv4 = view.findViewById(R.id.fourTv4);


        fourTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch4Data1Activity.class));
            }
        });

        fourTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch4Data2Activity.class));
            }
        });

        fourTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch4Data3Activity.class));
            }
        });

        fourTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch4Data4Activity.class));
            }
        });

        return view;
    }
}
