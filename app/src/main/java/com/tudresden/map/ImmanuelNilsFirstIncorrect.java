package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ImmanuelNilsFirstIncorrect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_incorrect_in);

    }
    public void onClickTryAgainBtn(View view){
        Intent intent = new Intent(ImmanuelNilsFirstIncorrect.this, ChooseSuspectPanelFirst.class);
        startActivity(intent);
    }
}
