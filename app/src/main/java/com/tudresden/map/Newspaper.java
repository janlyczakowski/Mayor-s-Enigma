package com.tudresden.map;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Newspaper extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private de.hdodenhof.circleimageview.CircleImageView audio_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspaper);

        audio_button = findViewById(R.id.audio_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.introduction);
        Utils.setAudioOnClickListener(mediaPlayer, audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer,audio_button);
    }

    public void onClickBeginListener(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        Intent intent = new Intent(Newspaper.this, MapActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        audio_button = findViewById(R.id.audio_button);
        audio_button.setImageResource(R.drawable.ic_speaker_enabled);
        mediaPlayer = MediaPlayer.create(this, R.raw.introduction);
        Utils.setAudioOnClickListener(mediaPlayer, audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer,audio_button);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}
