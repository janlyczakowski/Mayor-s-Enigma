package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseSuspectPanelTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_suspect2);

    }

    public void onClickBackArrow(View view) {
        Intent intent = new Intent(ChooseSuspectPanelTwo.this, EvidenceList.class);
        startActivity(intent);
    }

    public void onClickJakobJonas(View view) {
        Intent intent = new Intent(ChooseSuspectPanelTwo.this, JakobJonasSecondIncorrect.class);
        startActivity(intent);
    }

    public void onClickImmanuelNils(View view) {
        Intent intent = new Intent(ChooseSuspectPanelTwo.this, ImmanuelNilsSecondIncorrect.class);
        startActivity(intent);
    }

    public void onClickClaraTheresa(View view) {
        Intent intent = new Intent(ChooseSuspectPanelTwo.this, ClaraTheresaSecondCorrect.class);
        startActivity(intent);
    }
}