package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ClaraTheresaSecondCorrect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_correct);

    }

    public void onClickGoBack(View view){
        Intent intent = new Intent(ClaraTheresaSecondCorrect.this, ChooseSuspectPanelTwo.class);
        startActivity(intent);
    }
}
