package com.sana.physicsbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sana.physicsbook.R;

import java.util.Locale;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch1Data4Activity extends AppCompatActivity {

    TextView ch1Data4DetailTv;
    private TextToSpeech mTTS;
    ImageButton ch1Data4PlayBtn, ch1Data4PauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch1_data4);





        ch1Data4PlayBtn = findViewById(R.id.ch1Data4playBtnId);
        ch1Data4PauseBtn = findViewById(R.id.ch1Data4pauseBtnId);
        ch1Data4DetailTv = findViewById(R.id.ch1Data4DetailId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch1Data4DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch1Data4PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch1Data4PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch1Data4PauseBtn.setVisibility(View.VISIBLE);
                ch1Data4PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch1Data4PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch1Data4PauseBtn.setVisibility(View.INVISIBLE);
                ch1Data4PlayBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void speak() {

        String text = ch1Data4DetailTv.getText().toString();
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