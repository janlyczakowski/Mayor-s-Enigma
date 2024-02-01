package com.tudresden.map;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

public class Utils {
    public static void setAudioOnClickListener(final MediaPlayer mediaPlayer, de.hdodenhof.circleimageview.CircleImageView audio_icon) {
        audio_icon.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (!mediaPlayer.isPlaying()) {
                                                  mediaPlayer.start();
                                                  audio_icon.setImageResource(R.drawable.pause_icon);
                                              } else {
                                                  mediaPlayer.pause();
                                                  audio_icon.setImageResource(R.drawable.play_icon);
                                              }
                                          }
                                      }
        );

    }
    public static void setOnAudioCompletionListener(final MediaPlayer mediaPlayer,de.hdodenhof.circleimageview.CircleImageView audio_icon) {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                audio_icon.setImageResource(R.drawable.ic_speaker_enabled);
            }
        });
    }

}
