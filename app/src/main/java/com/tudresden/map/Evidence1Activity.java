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


public class Evidence1Activity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private de.hdodenhof.circleimageview.CircleImageView audio_button;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence1);
        i = getIntent().getIntExtra("vari", i);
        int currentNextLocationId = MapActivity.getNextLocationId(getApplicationContext());
        if (currentNextLocationId >= 1) {

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
        mediaPlayer = MediaPlayer.create(this, R.raw.bridge);
        Utils.setAudioOnClickListener(mediaPlayer, audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer,audio_button);
    }

    public void onClickBackArrow(View view) {
        onBackPressed();
    }

    public void onClickNextLocation(View view) {
        Intent intent = new Intent(Evidence1Activity.this, MapActivity.class);
        startActivity(intent);
    }

    public void Solve(View view) {

        AppCompatRadioButton mRadioBtn1 = findViewById(R.id.radio1);
        AppCompatRadioButton mRadioBtn2 = findViewById(R.id.radio2);
        AppCompatRadioButton mRadioBtn3 = findViewById(R.id.radio3);
        AppCompatRadioButton mRadioBtn4 = findViewById(R.id.radio4);

        if (mRadioBtn1.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence1Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("This assertion is inaccurate, given his esteemed status as a prominent and promising mayor of Dresden. He was held in high regard by the community, and there were no discernible personal motivations for such a drastic action.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", (dialog, id) -> dialog.dismiss());

            builder.show();

        } else if (mRadioBtn2.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence1Activity.this);
            builder.setTitle("THAT'S CORRECT");
            if (i < 2) {
                i = 2;
            }
            builder.setMessage("A plausible scenario for further investigation, detective. You unlocked the next location on the map. Keep on going!");
            builder.setCancelable(false);

            builder.setPositiveButton("CONTINUE", (dialog, id) -> {


                // New Evidence Information Dialog
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Evidence1Activity.this);
                builder1.setTitle("NEW EVIDENCE UNLOCKED");
                builder1.setMessage("Read the new evidence and return to map to discover new location.");

                builder1.setCancelable(false);

                builder1.setNegativeButton("CLOSE", (dialog1, id1) -> {

                    // increment the next location id
                    int currentNextLocationId = MapActivity.getNextLocationId(getApplicationContext());
                    currentNextLocationId++;
                    MapActivity.setNextLocationId(getApplicationContext(), currentNextLocationId);

                    findViewById(R.id.solve_question_panel).setVisibility(View.GONE);
                    findViewById(R.id.success_msg_panel).setVisibility(View.VISIBLE);
                    dialog1.dismiss();


                });
                builder1.show();

            });

            builder.show();

        } else if (mRadioBtn3.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence1Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("The mayor, known for his appreciation of his personal belongings, would not have left his hat behind willingly. Consider alternative explanations for the hat's displacement.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", (dialog, id) -> dialog.dismiss());

            builder.show();

        } else if (mRadioBtn4.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence1Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("The hat, adorned with the city hall pin and embroidered with the mayor's name, unequivocally identifies it as belonging to the mayor.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", (dialog, id) -> dialog.dismiss());

            builder.show();
        }


    }

    @Override
    public void onBackPressed() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        super.onBackPressed();
    }


}