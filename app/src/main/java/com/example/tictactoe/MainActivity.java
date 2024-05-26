package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tictactoe.services.MyServiceClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView ic_music_on;

    TextView tv_playerOneName,tv_playerTwoName;
    LinearLayout playerOneLayout,playerTwoLayout;
    ImageView image1,image2,image3,image4,image5,image6,image7,image8,image9;

    private MediaPlayer mediaPlayer;

    private List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WinDialog dialog = null;

        ic_music_on = findViewById(R.id.ic_music_on);

        tv_playerOneName = findViewById(R.id.tv_playerOneName);
        tv_playerTwoName = findViewById(R.id.tv_playerTwoName);

        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});

        final String getPlayerOneName = getIntent().getStringExtra("playerOne");
        final String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        tv_playerOneName.setText(getPlayerOneName);
        tv_playerTwoName.setText(getPlayerTwoName);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(0)) {
                    performAction((ImageView) view, 0);
                }
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(1)) {
                    performAction((ImageView) view, 1);
                }
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(2)) {
                    performAction((ImageView) view, 2);
                }
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(3)) {
                    performAction((ImageView) view, 3);
                }
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(4)) {
                    performAction((ImageView) view, 4);
                }
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(5)) {
                    performAction((ImageView) view, 5);
                }
            }
        });
        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(6)) {
                    performAction((ImageView) view, 6);
                }
            }
        });
        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(7)) {
                    performAction((ImageView) view, 7);
                }
            }
        });
        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(8)) {
                    performAction((ImageView) view, 8);
                }
            }
        });


        ic_music_on.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                if (isMyServiceRunning(MyServiceClass.class)) {
                    stopService(new Intent(MainActivity.this, MyServiceClass.class));
                    ic_music_on.setImageResource(R.drawable.ic_baseline_music_on);
                } else if (!isMyServiceRunning(MyServiceClass.class)) {
                    startService(new Intent(MainActivity.this, MyServiceClass.class));
                    ic_music_on.setImageResource(R.drawable.ic_baseline_music_off_24);

                }
            }
        });
    }

    private void performAction(ImageView imageView, int selectedBoxPosition){
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1){
            imageView.setImageResource(R.drawable.crossirr);

            if (checkPlayerWin())
            {
                WinDialog winDialog = new WinDialog(MainActivity.this,
                        "Congratulations!\n" + tv_playerOneName.getText().toString() + " has won the match",MainActivity.this,"win");
                winDialog.setCancelable(false);
                winDialog.show();

                //
                winDialog.btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        winDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, AddPlayersActivity.class));
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.audio_click);
                        mp.start();
                    }
                });
                //

            }
            else if (totalSelectedBoxes == 9)
            {
                WinDialog winDialog = new WinDialog(MainActivity.this,
                "Ooooops!\nMatch is Drawn!",MainActivity.this,"draw");
                mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.wrong);
                mediaPlayer.start();
                winDialog.show();

                //
                winDialog.btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        winDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, AddPlayersActivity.class));
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.audio_click);
                        mp.start();
                    }
                });
                //

            }
            else
            {
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }
        }
        else
        {
            imageView.setImageResource(R.drawable.zeroirrchange);
            if (checkPlayerWin()){
                WinDialog winDialog = new WinDialog(MainActivity.this,
                        "Congratulations!\n" + tv_playerOneName.getText().toString() + " has won the match",MainActivity.this,"win");
                winDialog.setCancelable(false);
                winDialog.show();

                //
                winDialog.btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        winDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, AddPlayersActivity.class));
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.audio_click);
                        mp.start();
                    }
                });
                //

            }
            else if(selectedBoxPosition == 9)
            {
                WinDialog winDialog = new WinDialog(MainActivity.this,
                        "Ooooops!\nMatch is Drawn!",MainActivity.this,"draw");
                mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.wrong);
                mediaPlayer.start();
                winDialog.show();

                //
                winDialog.btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        winDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, AddPlayersActivity.class));
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.audio_click);
                        mp.start();
                    }
                });
                //

            }
            else
            {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }

    private void changePlayerTurn(int currentPlayerTurn){
        playerTurn = currentPlayerTurn;

        if (playerTurn == 1){
            playerOneLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
        else
        {
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerOneLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
    }

    private boolean checkPlayerWin(){
        boolean response = false;
        for (int i=0;i<combinationList.size();i++){
            final int[] combination = combinationList.get(i);
            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn){
                response = true;
            }
        }
        return response;
    }

    private boolean isBoxSelectable(int boxPosition){
        boolean response = false;
        if (boxPositions[boxPosition] == 0){
            response = true;
        }
        return response;
    }

    public void restartMatch(){

        boxPositions = new int[] {0,0,0,0,0,0,0,0,0};
        playerTurn = 1;
        totalSelectedBoxes = 1;

        image1.setImageResource(R.drawable.backgroundtransparent);
        image2.setImageResource(R.drawable.backgroundtransparent);
        image3.setImageResource(R.drawable.backgroundtransparent);
        image4.setImageResource(R.drawable.backgroundtransparent);
        image5.setImageResource(R.drawable.backgroundtransparent);
        image6.setImageResource(R.drawable.backgroundtransparent);
        image7.setImageResource(R.drawable.backgroundtransparent);
        image8.setImageResource(R.drawable.backgroundtransparent);
        image9.setImageResource(R.drawable.backgroundtransparent);

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