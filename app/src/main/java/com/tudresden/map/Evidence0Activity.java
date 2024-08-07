package com.tudresden.map;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;


public class Evidence0Activity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private de.hdodenhof.circleimageview.CircleImageView audio_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspaper2);

        audio_button = findViewById(R.id.audio_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.introduction);
        Utils.setAudioOnClickListener(mediaPlayer, audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer, audio_button);

    }

    public void onClickBackArrow(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        super.onBackPressed();
    }

}