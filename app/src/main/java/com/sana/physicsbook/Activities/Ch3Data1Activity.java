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
import com.sana.physicsbook.papplets.SimplePendulum;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch3Data1Activity extends AppCompatActivity {

    TextView ch3Data1DetailTv;
    SeekBar gseekbar;
    TextView gravityview;
    TextView lengthview;
    SeekBar lseekbar;
    float gravity1;
    double length;

    private TextToSpeech mTTS;
    ImageButton ch3Data1PlayBtn, ch3Data1PauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3_data1);

        ch3Data1PlayBtn = findViewById(R.id.ch3Data1playBtnId);
        ch3Data1PauseBtn = findViewById(R.id.ch3Data1pauseBtnId);
        ch3Data1DetailTv = findViewById(R.id.ch3Data1DetailId);
        gseekbar = findViewById(R.id.gravitySeekbar1);
        lseekbar = findViewById(R.id.lengthSeekbar);
        gravityview = findViewById(R.id.gravityView1);
        lengthview = findViewById(R.id.lengthView);
        gravity1 = 0;
        length = 1;

        gseekbar.setProgress((int)gravity1);
        lseekbar.setProgress((int)length);
        lseekbar.setMax(20);


        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final SimplePendulum pendulum = new SimplePendulum();
        PFragment fragment = new PFragment(pendulum);
        fragment.setView(frameLayout,this);

        pendulum.setGravityvalue1(gravity1);
        gravityview.setText("Gravity" + gravity1);

        pendulum.setLength((int) length);
        lengthview.setText("Length" + length*50);

        gseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pendulum.setGravityvalue1(progress);
                gravityview.setText("Gravity: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pendulum.setLength(progress*50);
                lengthview.setText("Length: " + progress*25);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch3Data1DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch3Data1PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch3Data1PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch3Data1PauseBtn.setVisibility(View.VISIBLE);
                ch3Data1PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch3Data1PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch3Data1PauseBtn.setVisibility(View.INVISIBLE);
                ch3Data1PlayBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void speak() {

        String text = ch3Data1DetailTv.getText().toString();
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