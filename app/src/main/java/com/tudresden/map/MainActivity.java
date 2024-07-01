package com.tudresden.map;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private de.hdodenhof.circleimageview.CircleImageView audio_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        System.out.println("onCreate");

        audio_button = findViewById(R.id.audio_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.instruction);
//        Utils.setAudioOnClickListener(mediaPlayer, audio_button);
        setAudioOnClickListener(mediaPlayer,audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer,audio_button);

        Button btn = findViewById(R.id.btn_ready);
        btn.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            Intent intent = new Intent(MainActivity.this, Newspaper.class);
            startActivity(intent);
        });
    }

        public void setAudioOnClickListener(final MediaPlayer mediaPlayer, de.hdodenhof.circleimageview.CircleImageView audio_icon) {
            audio_icon.setOnClickListener(v -> {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    audio_icon.setImageResource(R.drawable.pause_icon);
                } else {
                    mediaPlayer.pause();
                    audio_icon.setImageResource(R.drawable.play_icon);
                }
            }
            );

        }


    @Override
    protected void onResume() {
        super.onResume();
        audio_button = findViewById(R.id.audio_button);
        audio_button.setImageResource(R.drawable.ic_speaker_enabled);
        mediaPlayer = MediaPlayer.create(this, R.raw.instruction);
        setAudioOnClickListener(mediaPlayer, audio_button);
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
