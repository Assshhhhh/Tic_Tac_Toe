package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog {

    private final String message;
    ImageView imageView;
    private final MainActivity mainActivity;
    String status;

    Button btnBack,btnStartAgain;

    public WinDialog(@NonNull Context context, String message, MainActivity mainActivity,String status ) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
        this.status = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog);

        imageView = findViewById(R.id.imageTrophy);

        final TextView messageTxt = findViewById(R.id.messageTxt);
        btnStartAgain = findViewById(R.id.btnStartAgain);
        btnBack = findViewById(R.id.btnBack);

        if(status == "draw"){

            imageView.setImageResource(R.drawable.matchdrawn);
        }else{
            imageView.setImageResource(R.drawable.youwin);
        }

        messageTxt.setText(message);

        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.restartMatch();
                dismiss();
            }
        });


    }
}
