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
import com.sana.physicsbook.papplets.GravationalForce;
import com.sana.physicsbook.papplets.SHM;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch3Data2Activity extends AppCompatActivity {

    TextView ch3Data2DetailTv;
    private TextToSpeech mTTS;
    ImageButton ch3Data2PlayBtn, ch3Data2PauseBtn;
    SeekBar ampbar;
    TextView ampyview;
    TextView periodview;
    SeekBar periodbar;
    int period;
    int amplitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3_data2);

        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final SHM shm = new SHM();
        PFragment fragment = new PFragment(shm);
        fragment.setView(frameLayout,this);


        ch3Data2PlayBtn = findViewById(R.id.ch3Data2playBtnId);
        ch3Data2PauseBtn = findViewById(R.id.ch3Data2pauseBtnId);
        ch3Data2DetailTv = findViewById(R.id.ch3Data2DetailId);
        ampbar = findViewById(R.id.ampSeekbar);
        periodbar = findViewById(R.id.periodSeekbar);
        ampyview = findViewById(R.id.ampView);
        periodview = findViewById(R.id.periodView);

        int period = 2;
        int amplitude = 2;

        ampbar.setProgress((int)period);
        periodbar.setProgress((int)amplitude);

        shm.setAmplitude(amplitude);
        ampyview.setText("Amplitude" + amplitude*50);

        shm.setPeriod((int) period);
        periodview.setText("Period" + period*10);

        ampbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shm.setAmplitude(progress*50);
                ampyview.setText("Amplitude: " + progress*50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        periodbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shm.setPeriod(progress*10);
                periodview.setText("Period: " + progress*10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch3Data2DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch3Data2PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch3Data2PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch3Data2PauseBtn.setVisibility(View.VISIBLE);
                ch3Data2PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch3Data2PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch3Data2PauseBtn.setVisibility(View.INVISIBLE);
                ch3Data2PlayBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void speak() {

        String text = ch3Data2DetailTv.getText().toString();
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