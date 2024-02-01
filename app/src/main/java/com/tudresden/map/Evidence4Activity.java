package com.tudresden.map;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

public class Evidence4Activity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private de.hdodenhof.circleimageview.CircleImageView audio_button;


int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence4);
        i = getIntent().getIntExtra("vari",i);

        int currentNextLocationId = MapActivity.getNextLocationId(getApplicationContext());
        if (currentNextLocationId >= 4) {

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
        mediaPlayer = MediaPlayer.create(this, R.raw.immanuel_office);
        Utils.setAudioOnClickListener(mediaPlayer,audio_button);
        Utils.setOnAudioCompletionListener(mediaPlayer,audio_button);


    }


    public void Solve(View view){

        AppCompatRadioButton mRadioBtn1 = findViewById(R.id.radio1);
        AppCompatRadioButton mRadioBtn2 = findViewById(R.id.radio2);
        AppCompatRadioButton mRadioBtn3 = findViewById(R.id.radio3);
        AppCompatRadioButton mRadioBtn4 = findViewById(R.id.radio4);

        if(mRadioBtn1.isChecked()){

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence4Activity.this);
            builder.setTitle("THAT'S CORRECT");
            if(i<5){i=5;}
            builder.setMessage("Brave move! New leads bring you closer to solving the case.");
            builder.setCancelable(false);

            builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // New Evidence Information Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Evidence4Activity.this);
                    builder.setTitle("NEW EVIDENCE UNLOCKED");
                    builder.setMessage("Read the new evidence and return to map to discover new location.");
                    builder.setCancelable(false);

                    builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // increment the next location id
                            int currentNextLocationId = MapActivity.getNextLocationId(getApplicationContext());
                            currentNextLocationId++;
                            MapActivity.setNextLocationId(getApplicationContext(), currentNextLocationId);

                            findViewById(R.id.solve_question_panel).setVisibility(View.GONE);
                            findViewById(R.id.success_msg_panel).setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
            });
            builder.show();


        } else if (mRadioBtn2.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence4Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("He becomes agitated and loses his nerves, refusing to respond to any additional questions and forcing you to leave. You seem to have missed a good opportunity.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }else if (mRadioBtn3.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence4Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("Upon your return later that night, you discover that the folder containing the documents has disappeared. The wait-and-see tactic is not working, it is sometimes better to act on the spot.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }else if (mRadioBtn4.isChecked()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Evidence4Activity.this);
            builder.setTitle("INCORRECT");
            builder.setMessage("While initially agreeing to collaborate, all of his provided testimony proves to be inaccurate and intentionally misleading, suggesting an attempt to hinder the investigation.");
            builder.setCancelable(false);

            builder.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }

    }

    public void onClickBackArrow(View view){
        onBackPressed();
    }

    public void onClickNextLocation(View view){
        Intent intent = new Intent(Evidence4Activity.this,MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        super.onBackPressed();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // Navigate back
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}