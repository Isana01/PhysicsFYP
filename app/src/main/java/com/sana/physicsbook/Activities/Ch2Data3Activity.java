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
import com.sana.physicsbook.papplets.Arrive;
import com.sana.physicsbook.papplets.pathhfollow;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch2Data3Activity extends AppCompatActivity {

    TextView ch2Data3DetailTv;
    private TextToSpeech mTTS;
    ImageButton ch2Data3PlayBtn, ch2Data3PauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch2_data3);

        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final pathhfollow path  = new pathhfollow();
        PFragment fragment = new PFragment(path);
        fragment.setView(frameLayout,this);




        ch2Data3PlayBtn = findViewById(R.id.ch2Data3playBtnId);
        ch2Data3PauseBtn = findViewById(R.id.ch2Data3pauseBtnId);
        ch2Data3DetailTv = findViewById(R.id.ch2Data3DetailId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch2Data3DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch2Data3PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch2Data3PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch2Data3PauseBtn.setVisibility(View.VISIBLE);
                ch2Data3PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch2Data3PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch2Data3PauseBtn.setVisibility(View.INVISIBLE);
                ch2Data3PlayBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void speak() {

        String text = ch2Data3DetailTv.getText().toString();
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