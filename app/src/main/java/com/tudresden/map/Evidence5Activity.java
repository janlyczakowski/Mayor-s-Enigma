package com.tudresden.map;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;


public class Evidence5Activity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private de.hdodenhof.circleimageview.CircleImageView audio_button;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence5);
        i = getIntent().getIntExtra("vari", i);

        String suspectPanelId = EvidenceList.getActivateSuspectPanel(getApplicationContext());
        if (suspectPanelId.equals("inactive") || suspectPanelId.equals("4 suspects") || suspectPanelId.equals("3 suspects")) {

            // hide radio group
            RadioGroup radio_group = findViewById(R.id.radio_group);
            radio_group.setVisibility(View.GONE);

            androidx.appcompat.widget.AppCompatButton solve_btn = findViewById(R.id.btn_solve_evidence1);
            solve_btn.setVisibility(View.GONE);

            TextView title = findViewById(R.id.pick_your_choice_title);
            title.setVisibility(View.GONE);

            // show explanation
            LinearLayout layout = findViewById(R.id.success_msg_panel);
            layout.setVisibility(View.VISIBLE);
        }

        audio_button = findViewById(R.id.audio_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.alexander_exhibition);
        Utils.setAudioOnClickListener(mediaPlayer, audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer, audio_button);


    }


    public void Solve(View view) {

        AppCompatRadioButton mRadioBtn1 = findViewById(R.id.radio1);
        AppCompatRadioButton mRadioBtn2 = findViewById(R.id.radio2);
        AppCompatRadioButton mRadioBtn3 = findViewById(R.id.radio3);
        AppCompatRadioButton mRadioBtn4 = findViewById(R.id.radio4);

        if (mRadioBtn1.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence5Activity.this);
            builder.setTitle("THAT'S CORRECT");
            if (i < 6) {
                i = 6;
            }
            builder.setMessage("The boat captain successfully intercepted Alexander Philipp, impeding his escape. Well done! You have gathered the necessary information to draw your own conclusions.");
            builder.setCancelable(false);

            builder.setPositiveButton("CONTINUE", (dialog, id) -> {

                // New Evidence Information Dialog
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Evidence5Activity.this);
                builder1.setTitle("NEW EVIDENCE UNLOCKED");
                builder1.setMessage("Read the new evidence and return to map to discover new location");
                builder1.setCancelable(false);

                builder1.setNegativeButton("CLOSE", (dialog1, id1) -> {

                    // set active panel to enable to "who is guilty button"
                    EvidenceList.setActiveSuspectPanel(getApplicationContext(), "4 suspects");

                    findViewById(R.id.solve_question_panel).setVisibility(View.GONE);
                    findViewById(R.id.success_msg_panel).setVisibility(View.VISIBLE);
                    dialog1.dismiss();
                });
                builder1.show();

            });
            builder.show();


        } else if (mRadioBtn2.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence5Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("Despite your efforts to swim after him, the weight of your coat soaked with water proved unmanageable, hindering your ability to catch up. Unfortunately, Alexander already made it to the other side.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", (dialog, id) -> dialog.dismiss());
            builder.show();

        } else if (mRadioBtn3.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence5Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("While cycling, you observed Alexander submerge underwater, losing track of him. You'll have to keep a closer eye on him to make sure he doesn't escape. ");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", (dialog, id) -> dialog.dismiss());
            builder.show();

        } else if (mRadioBtn4.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence5Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("Due to the increasing distance between you and Alexander, your shots went astray, ultimately running out of ammunition. Now it’s too late to chase after him.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", (dialog, id) -> dialog.dismiss());
            builder.show();

        }

    }


    public void onClickBackArrow(View view) {
//        onBackPressed();
        Intent intent = new Intent(Evidence5Activity.this,EvidenceList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        super.onBackPressed();
    }


}