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
import android.widget.SeekBar;
import android.widget.TextView;

import com.sana.physicsbook.R;
import com.sana.physicsbook.papplets.DragForce;
import com.sana.physicsbook.papplets.GravationalForce;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch1Data3Activity extends AppCompatActivity {

    TextView ch1Data3DetailTv;
    SeekBar constantmass;
    int mass;
    TextView massview;

    private TextToSpeech mTTS;
    ImageButton ch1Data3PlayBtn, ch1Data3PauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch1_data3);



        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final GravationalForce force3 = new GravationalForce();
        PFragment fragment = new PFragment(force3);
        fragment.setView(frameLayout,this);


        ch1Data3PlayBtn = findViewById(R.id.ch1Data3playBtnId);
        ch1Data3PauseBtn = findViewById(R.id.ch1Data3pauseBtnId);
        ch1Data3DetailTv = findViewById(R.id.ch1Data3DetailId);
        constantmass = findViewById(R.id.massSeekbar);
        massview = findViewById(R.id.MassView1);

        mass = 5;
        constantmass.setProgress((int)mass);

        force3.setMass(mass);
        massview.setText("Mass" + mass*4);

        constantmass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                force3.setMass(progress);
                massview.setText("Mass: " + progress*4);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch1Data3DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch1Data3PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch1Data3PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch1Data3PauseBtn.setVisibility(View.VISIBLE);
                ch1Data3PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch1Data3PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch1Data3PauseBtn.setVisibility(View.INVISIBLE);
                ch1Data3PlayBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void speak() {

        String text = ch1Data3DetailTv.getText().toString();
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