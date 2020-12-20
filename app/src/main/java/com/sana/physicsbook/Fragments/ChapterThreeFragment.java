package com.sana.physicsbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sana.physicsbook.Activities.Ch2Data1Activity;
import com.sana.physicsbook.Activities.Ch2Data2Activity;
import com.sana.physicsbook.Activities.Ch2Data3Activity;
import com.sana.physicsbook.Activities.Ch2Data4Activity;
import com.sana.physicsbook.Activities.Ch3Data1Activity;
import com.sana.physicsbook.Activities.Ch3Data2Activity;
import com.sana.physicsbook.Activities.Ch3Data3Activity;
import com.sana.physicsbook.Activities.Ch3Data4Activity;
import com.sana.physicsbook.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChapterThreeFragment extends Fragment {

    TextView threeTv1, threeTv2, threeTv3, threeTv4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_three_fragment, container, false);
        getActivity().setTitle("Chapter 3");

        threeTv1 = view.findViewById(R.id.threeTv1);
        threeTv2 = view.findViewById(R.id.threeTv2);
        threeTv3 = view.findViewById(R.id.threeTv3);


        threeTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch3Data1Activity.class));
            }
        });

        threeTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch3Data2Activity.class));
            }
        });

        threeTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ch3Data3Activity.class));
            }
        });

//        threeTv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Ch3Data4Activity.class));
//            }
//        });

        return view;
    }
}
