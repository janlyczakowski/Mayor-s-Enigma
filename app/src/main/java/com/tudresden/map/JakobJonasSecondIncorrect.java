package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class JakobJonasSecondIncorrect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_incorrect_jj);

    }
    public void onClickTryAgainBtn(View view){
        Intent intent = new Intent(JakobJonasSecondIncorrect.this, ChooseSuspectPanelTwo.class);
        startActivity(intent);
    }
}
