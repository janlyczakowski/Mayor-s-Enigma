package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseSuspectPanelFirst extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_suspect1);

    }

    public void onClickBackArrow(View view) {
        Intent intent = new Intent(ChooseSuspectPanelFirst.this, EvidenceList.class);
        startActivity(intent);
    }

    public void onClickJakobJonas(View view){
        Intent intent = new Intent(ChooseSuspectPanelFirst.this, JakobJonasFirstIncorrect.class);
        startActivity(intent);
    }
    public void onClickImmanuelNils(View view){
        Intent intent = new Intent(ChooseSuspectPanelFirst.this, ImmanuelNilsFirstIncorrect.class);
        startActivity(intent);
    }
    public void onClickClaraTheresa(View view){
        Intent intent = new Intent(ChooseSuspectPanelFirst.this, ClaraTheresaFirstIncorrect.class);
        startActivity(intent);
    }
    public void onClickAlexanderPhillip(View view){
        Intent intent = new Intent(ChooseSuspectPanelFirst.this, AlexanderPhilipFirstCorrect.class);
        startActivity(intent);
    }
}
