package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AlexanderPhilipFirstCorrect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_correct);

    }
    public void onClickClose(View view){
        // go to extra location panel
        Intent intent = new Intent(AlexanderPhilipFirstCorrect.this, PlotTwistPanel.class);
        startActivity(intent);
    }
}
