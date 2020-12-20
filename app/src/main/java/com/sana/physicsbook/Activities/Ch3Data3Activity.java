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
import com.sana.physicsbook.papplets.DragForce;
import com.sana.physicsbook.papplets.ProjectileMotion;

import java.util.Locale;

import processing.android.PFragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Ch3Data3Activity extends AppCompatActivity {

    TextView ch3Data3DetailTv;
    Button Left;
    Button Right;
    TextView velocityview;
    double ogravity;
    SeekBar velbar;
    private TextToSpeech mTTS;
    ImageButton ch3Data3PlayBtn, ch3Data3PauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3_data3);
        FrameLayout frameLayout = findViewById(R.id.simulation_frame);
        final ProjectileMotion projectile = new ProjectileMotion();
        PFragment fragment = new PFragment(projectile);
        fragment.setView(frameLayout,this);

        velocityview = findViewById(R.id.VelocityView);
        velbar = findViewById(R.id.VelocitySeekbar);

        ogravity =  1;
        velbar.setProgress((int) ogravity);

        projectile.setOptgravity(ogravity*0.1);
        velocityview.setText("Acceleration due to Gravity" + ogravity*0.1);

        velbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                projectile.setOptgravity((progress*0.1));
                velocityview.setText("Acceleration due to Gravity: " + progress*0.1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        ch3Data3PlayBtn = findViewById(R.id.ch3Data3playBtnId);
        Left = findViewById(R.id.left);
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectile.Left();
            }
        });
        Right = findViewById(R.id.right);
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectile.Right();
            }
        });
        ch3Data3PauseBtn = findViewById(R.id.ch3Data3pauseBtnId);
        ch3Data3DetailTv = findViewById(R.id.ch3Data3DetailId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ch3Data3DetailTv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
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
                        ch3Data3PlayBtn.setSaveEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }


        });

        ch3Data3PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                ch3Data3PauseBtn.setVisibility(View.VISIBLE);
                ch3Data3PlayBtn.setVisibility(View.INVISIBLE);
            }
        });

        ch3Data3PauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTTS.stop();
                ch3Data3PauseBtn.setVisibility(View.INVISIBLE);
                ch3Data3PlayBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void speak() {

        String text = ch3Data3DetailTv.getText().toString();
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