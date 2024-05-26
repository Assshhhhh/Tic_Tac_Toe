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

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    ImageView ic_music_on;
    Button btn_with_AI,btn_with_player;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
        ic_music_on = findViewById(R.id.ic_music_on);
        btn_with_AI = findViewById(R.id.btn_with_AI);
        btn_with_player = findViewById(R.id.btn_with_player);

        YoYo.with(Techniques.DropOut).duration(2000).repeat(9999).playOn(textView);
        YoYo.with(Techniques.Shake).duration(3000).repeat(9999).playOn(btn_with_player);


        btn_with_AI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,AI_Activity.class));
                MediaPlayer mp = MediaPlayer.create(SecondActivity.this,R.raw.audio_click);
                mp.start();
            }
        });
        btn_with_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, AddPlayersActivity.class));
                MediaPlayer mp = MediaPlayer.create(SecondActivity.this,R.raw.audio_click);
                mp.start();
            }
        });




        ic_music_on.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                if (isMyServiceRunning(MyServiceClass.class)) {
                    stopService(new Intent(SecondActivity.this, MyServiceClass.class));
                    ic_music_on.setImageResource(R.drawable.ic_baseline_music_on);
                } else if (!isMyServiceRunning(MyServiceClass.class)) {
                    startService(new Intent(SecondActivity.this, MyServiceClass.class));
                    ic_music_on.setImageResource(R.drawable.ic_baseline_music_off_24);

                }
            }
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
}