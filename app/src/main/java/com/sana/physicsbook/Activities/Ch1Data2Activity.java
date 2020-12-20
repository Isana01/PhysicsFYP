package com.sana.physicsbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sana.physicsbook.R;
import com.sana.physicsbook.papplets.DragForce;
import com.sana.physicsbook.papplets.FrictionForce;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch1Data2Activity extends AppCompatActivity {

    TextView ch1Data2DetailTv;
    private TextToSpeech mTTS;
    ImageButton ch1Data2PlayBtn, ch1Data2PauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch1_data2);


        ch1Data2PlayBtn = findViewById(R.id.ch1Data2playBtnId);
        ch1Data2PauseBtn = findViewById(R.id.ch1Data2pauseBtnId);
        ch1Data2DetailTv = findViewById(R.id.ch1Data2DetailId);
        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final DragForce force2 = new DragForce();
        PFragment fragment = new PFragment(force2);
        fragment.setView(frameLayout,this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch1Data2DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        ch1Data2PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch1Data2PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch1Data2PauseBtn.setVisibility(View.VISIBLE);
                ch1Data2PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch1Data2PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch1Data2PauseBtn.setVisibility(View.INVISIBLE);
                ch1Data2PlayBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void speak() {

        String text = ch1Data2DetailTv.getText().toString();
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}