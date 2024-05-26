package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tictactoe.services.MyServiceClass;

public class FirstActivity extends AppCompatActivity {

    int count = 0;

    TextView textView;
    ImageView ic_settings,ic_music_on;
    Button btn_next;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        textView = findViewById(R.id.textView);
        ic_music_on = findViewById(R.id.ic_music_on);
        ic_settings = findViewById(R.id.ic_settings);
        btn_next = findViewById(R.id.btn_next);

        YoYo.with(Techniques.Shake).duration(3000).repeat(9999).playOn(btn_next);
        YoYo.with(Techniques.DropOut).duration(2000).repeat(9999).playOn(textView);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);

                MediaPlayer mp = MediaPlayer.create(FirstActivity.this, R.raw.audio_click);
                mp.start();

            }
        });


        ic_music_on.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                if(isMyServiceRunning(MyServiceClass.class)){
                    stopService(new Intent(FirstActivity.this,MyServiceClass.class));
                    ic_music_on.setImageResource(R.drawable.ic_baseline_music_on);
                }else if(!isMyServiceRunning(MyServiceClass.class)){
                    startService(new Intent(FirstActivity.this,MyServiceClass.class));
                    ic_music_on.setImageResource(R.drawable.ic_baseline_music_off_24);

                }

            }

                /*if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ic_music_off.setImageResource(R.drawable.ic_baseline_music_on);
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    ic_music_off.setImageResource(R.drawable.ic_baseline_music_off_24);
                }*/

        });
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count == 2) {
            super.onBackPressed();
            stopService(new Intent(FirstActivity.this, MyServiceClass.class));
        }
    }
}