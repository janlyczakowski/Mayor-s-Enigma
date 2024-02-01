package com.tudresden.map;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ClaraTheresaDescActivity extends AppCompatActivity {

    private de.hdodenhof.circleimageview.CircleImageView audio_button;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clara_theresa_desc);

        audio_button = findViewById(R.id.audio_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.clara_audio);
        Utils.setAudioOnClickListener(mediaPlayer,audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer,audio_button);

        }

    public void onClickBackArrow(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        super.onBackPressed();
    }

}

