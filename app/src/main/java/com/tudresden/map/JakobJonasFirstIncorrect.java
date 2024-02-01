package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class JakobJonasFirstIncorrect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_incorrect_jj);

    }
public void onClickTryAgainBtn(View view){
    Intent intent = new Intent(JakobJonasFirstIncorrect.this, ChooseSuspectPanelFirst.class);
    startActivity(intent);
}
}

