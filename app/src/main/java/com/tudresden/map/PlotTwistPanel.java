package com.tudresden.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PlotTwistPanel extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plottwist);

    }
    public void onClickClose(View view){
        // disable "who is guilty button"
        EvidenceList.setActiveSuspectPanel(getApplicationContext(), "inactive");
        // activate new location
        // increment the next location id
        int currentNextLocationId = MapActivity.getNextLocationId(getApplicationContext());
        currentNextLocationId++;
        MapActivity.setNextLocationId(getApplicationContext(), currentNextLocationId);
        // go back to map
        Intent intent = new Intent(PlotTwistPanel.this, MapActivity.class);
        startActivity(intent);
    }
}
