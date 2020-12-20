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
import com.sana.physicsbook.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChapterOneFragment extends Fragment {

    TextView oneTv1, oneTv2, oneTv3, oneTv4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_one_fragment, container, false);
        getActivity().setTitle("Chapter 1");

        oneTv1 = view.findViewById(R.id.oneTv1);
        oneTv2 = view.findViewById(R.id.oneTv2);
        oneTv3 = view.findViewById(R.id.oneTv3);


        oneTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch1Data1Activity.class));
            }
        });

        oneTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch1Data2Activity.class));
            }
        });

        oneTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch1Data3Activity.class));
            }
        });

//        oneTv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Ch1Data4Activity.class));
//            }
//        });

        return view;
    }
}
