package com.tudresden.map;



import android.media.MediaPlayer;


public class Utils {

    public static void setAudioOnClickListener(final MediaPlayer mediaPlayer, de.hdodenhof.circleimageview.CircleImageView audio_icon) {
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
    public static void setOnAudioCompletionListener(final MediaPlayer mediaPlayer,de.hdodenhof.circleimageview.CircleImageView audio_icon) {
        mediaPlayer.setOnCompletionListener(mediaPlayer1 -> audio_icon.setImageResource(R.drawable.ic_speaker_enabled));
    }

}
