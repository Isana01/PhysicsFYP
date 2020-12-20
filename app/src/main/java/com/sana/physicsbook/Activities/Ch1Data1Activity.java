package com.sana.physicsbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sana.physicsbook.R;
import com.sana.physicsbook.papplets.FrictionForce;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch1Data1Activity extends AppCompatActivity {

    TextView ch1Data1DetailTv;
    private TextToSpeech mTTS;
    ImageButton ch1Data1PlayBtn, ch1Data1PauseBtn;
    TextView coefficient;
    SeekBar seekbar;
    int c;
    Button resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch1_data1);
        seekbar = findViewById(R.id.gravitySeekbar);
        coefficient = findViewById(R.id.gravityView);
        resetButton = findViewById(R.id.resetButton);




        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final FrictionForce force1 = new FrictionForce();
        PFragment fragment = new PFragment(force1);
        fragment.setView(frameLayout,this);



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                force1.reset();
            }
        });
        c=1;
        seekbar.setProgress((int)c);
        force1.setC(c);
        coefficient.setText("Coefficient: " + c*0.01);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                force1.setC((int) (progress*0.5));
                coefficient.setText("Coefficient: " + progress*0.01);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ch1Data1PlayBtn = findViewById(R.id.ch1Data1playBtnId);
        ch1Data1PauseBtn = findViewById(R.id.ch1Data1pauseBtnId);
        ch1Data1DetailTv = findViewById(R.id.ch1Data1DetailId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch1Data1DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch1Data1PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch1Data1PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch1Data1PauseBtn.setVisibility(View.VISIBLE);
                ch1Data1PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch1Data1PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch1Data1PauseBtn.setVisibility(View.INVISIBLE);
                ch1Data1PlayBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void speak() {

        String text = ch1Data1DetailTv.getText().toString();
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