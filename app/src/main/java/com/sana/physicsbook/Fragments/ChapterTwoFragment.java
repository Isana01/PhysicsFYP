package com.sana.physicsbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sana.physicsbook.Activities.Ch1Data1Activity;
import com.sana.physicsbook.Activities.Ch1Data2Activity;
import com.sana.physicsbook.Activities.Ch1Data3Activity;
import com.sana.physicsbook.Activities.Ch1Data4Activity;
import com.sana.physicsbook.Activities.Ch2Data1Activity;
import com.sana.physicsbook.Activities.Ch2Data2Activity;
import com.sana.physicsbook.Activities.Ch2Data3Activity;
import com.sana.physicsbook.Activities.Ch2Data4Activity;
import com.sana.physicsbook.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ChapterTwoFragment extends Fragment {

    TextView twoTv1, twoTv2, twoTv3, twoTv4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_two_fragment, container, false);
        getActivity().setTitle("Chapter 2");

        twoTv1 = view.findViewById(R.id.twoTv1);
        twoTv2 = view.findViewById(R.id.twoTv2);
        twoTv3 = view.findViewById(R.id.twoTv3);
        //twoTv4 = view.findViewById(R.id.twoTv4);

        twoTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch2Data1Activity.class));
            }
        });

        twoTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch2Data2Activity.class));
            }
        });

        twoTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch2Data3Activity.class));
            }
        });

//        twoTv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Ch2Data4Activity.class));
//            }
//        });

        return view;
    }
}
