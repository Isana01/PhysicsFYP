package com.sana.physicsbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sana.physicsbook.Activities.Ch4Data1Activity;
import com.sana.physicsbook.Activities.Ch4Data2Activity;
import com.sana.physicsbook.Activities.Ch4Data3Activity;
import com.sana.physicsbook.Activities.Ch4Data4Activity;
import com.sana.physicsbook.Activities.Ch5Data1Activity;
import com.sana.physicsbook.Activities.Ch5Data2Activity;
import com.sana.physicsbook.Activities.Ch5Data3Activity;
import com.sana.physicsbook.Activities.Ch5Data4Activity;
import com.sana.physicsbook.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChapterFiveFragment extends Fragment {

    TextView fiveTv1, fiveTv2, fiveTv3, fiveTv4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_five_fragment, container, false);
        getActivity().setTitle("Chapter 5");

        fiveTv1 = view.findViewById(R.id.fiveTv1);
        fiveTv2 = view.findViewById(R.id.fiveTv2);


        fiveTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch5Data1Activity.class));
            }
        });

        fiveTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch5Data2Activity.class));
            }
        });

        fiveTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch5Data3Activity.class));
            }
        });

        fiveTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch5Data4Activity.class));
            }
        });

        return view;
    }
}
