package com.tudresden.map;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EvidenceList extends AppCompatActivity implements EvidenceListItemAdapter.IEvidenceItemClickListener,
        SuspectNavigationAdapter.ISuspectItemClickListener {

    static String activateSuspectPanel = "";
    private static final String PREF_ACTIVE_SUSPECT_PANEL = "activateSuspectPanel";
    private ArrayList<Suspect> suspectNavigation = new ArrayList<>();
    private ArrayList<EvidenceListItem> evidenceList = new ArrayList<>();
    ArrayList<Integer> locations;
    boolean extraLocationUnlocked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferencesHelper.saveIsEvidenceListRecreated(getApplicationContext(),true);
        setContentView(R.layout.evidence_list);
        locations = getIntent().getIntegerArrayListExtra("locations");
        extraLocationUnlocked = MapActivity.getNewLocationUnlocked(getApplicationContext());

        System.out.println(extraLocationUnlocked);

        suspectNavigation.add(new Suspect(R.drawable.clara_theresa_pic, "Clara"));
        suspectNavigation.add(new Suspect(R.drawable.alexander_philipp_pic, "Alexander"));
        suspectNavigation.add(new Suspect(R.drawable.pic_immanuel_nils, "Immanuel"));
        suspectNavigation.add(new Suspect(R.drawable.jakob_jonas_pic, "Jakob"));

        // create and add recycle view top suspects navigation
        RecyclerView recyclerViewNavigation = findViewById(R.id.suspects_list);
        recyclerViewNavigation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNavigation.addItemDecoration(new EvenSpacingItemDecoration(90));
        SuspectNavigationAdapter suspectNavAdapter = new SuspectNavigationAdapter(suspectNavigation, EvidenceList.this);


        if (!extraLocationUnlocked) {
            // include just 5 items
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_0, R.string.location_0_title, "0"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_1, R.string.location_1_title, "1"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_2, R.string.location_2_title, "2"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_3, R.string.location_3_title, "3"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_4, R.string.location_4_title, "4"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_5, R.string.location_5_title, "5"));
        } else {
            //include all 6 items (including the extra location)
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_0, R.string.location_0_title, "0"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_1, R.string.location_1_title, "1"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_2, R.string.location_2_title, "2"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_3, R.string.location_3_title, "3"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_4, R.string.location_4_title, "4"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_5, R.string.location_5_title, "5"));
            evidenceList.add(new EvidenceListItem(R.drawable.evidencelist_location_6, R.string.location_6_title, "6"));
        }


        // create and add recycle view evidence list
        RecyclerView recyclerViewEvidenceList = findViewById(R.id.evidence_list);
        recyclerViewEvidenceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // recyclerViewEvidenceList.addItemDecoration(new EvenSpacingItemDecoration(90));
        EvidenceListItemAdapter evidenceAdapter = new EvidenceListItemAdapter(evidenceList, EvidenceList.this, locations);


        //Load the data
        recyclerViewNavigation.setAdapter(suspectNavAdapter);
        recyclerViewEvidenceList.setAdapter(evidenceAdapter);
    }

    public void setBtnStylingActive(com.google.android.material.button.MaterialButton choose_suspect_btn) {
        int deep_orange = ContextCompat.getColor(this, R.color.deep_orange);
        int slate_gray = ContextCompat.getColor(this, R.color.slate_gray);
        choose_suspect_btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(deep_orange));
        choose_suspect_btn.setTextColor(slate_gray);

    }

    public static void setActiveSuspectPanel(Context context, String value) {
        activateSuspectPanel = value;
        // Save the value to SharedPreferences
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_ACTIVE_SUSPECT_PANEL, Context.MODE_PRIVATE).edit();
        editor.putString("activateSuspectPanel", value);
        editor.apply();
    }

    // use this method to get the current value of nextLocationId
    public static String getActivateSuspectPanel(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_ACTIVE_SUSPECT_PANEL, Context.MODE_PRIVATE);
        activateSuspectPanel = prefs.getString("activateSuspectPanel", "");
        return activateSuspectPanel;
    }

    public void onClickBackArrow(View view) {
        // go to map activity
        Intent intent = new Intent(EvidenceList.this, MapActivity.class);
        startActivity(intent);
    }


    @Override
    public void onEvidenceItemClick(View v, int position) {

        Intent intent;
        switch (position) {
            case 0:
                //Introduction
                intent = new Intent(EvidenceList.this, Evidence0Activity.class);
                startActivity(intent);
                break;
            case 1:
                //The bridge
                intent = new Intent(EvidenceList.this, Evidence1Activity.class);
                startActivity(intent);
                break;
            case 2:
                //Jakob Jonas' factory
                intent = new Intent(EvidenceList.this, Evidence2Activity.class);
                startActivity(intent);
                break;
            case 3:
                //Clara Theresa's house
                intent = new Intent(EvidenceList.this, Evidence3Activity.class);
                startActivity(intent);
                break;
            case 4:
                //Immanuel Nil's office
                intent = new Intent(EvidenceList.this, Evidence4Activity.class);
                startActivity(intent);
                break;
            case 5:
                //Alexander Philipp's office
                intent = new Intent(EvidenceList.this, Evidence5Activity.class);
                startActivity(intent);
                break;
            case 6:
                //The mayor's office
                intent = new Intent(EvidenceList.this, Evidence6Activity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onSuspectItemClick(View v, int position) {

        Intent intent;
        switch (position) {
            case 0:
                //Clara
                intent = new Intent(EvidenceList.this, ClaraTheresaDescActivity.class);
                startActivity(intent);
                break;
            case 1:
                //Alexander
                intent = new Intent(EvidenceList.this, AlexanderPhilipDescActivity.class);
                startActivity(intent);
                break;
            case 2:
                //Immanuel
                intent = new Intent(EvidenceList.this, ImmanuelNilsDescActivity.class);
                startActivity(intent);
                break;
            case 3:
                //Jakob
                intent = new Intent(EvidenceList.this, JakobJonasDescActivity.class);
                startActivity(intent);
                break;

        }
    }

    public void toggleWhoIsGuiltyBtn(){

        com.google.android.material.button.MaterialButton choose_suspect_btn = findViewById(R.id.choose_suspect_btn);
        activateSuspectPanel = getActivateSuspectPanel(getApplicationContext());
        if (activateSuspectPanel.equals("4 suspects")) {
            // enable the button
            setBtnStylingActive(choose_suspect_btn);

        } else if (activateSuspectPanel.equals("3 suspects")) {
            // enable the button
            setBtnStylingActive(choose_suspect_btn);
        } else {
            // disable the button
            choose_suspect_btn.setEnabled(false);
            choose_suspect_btn.setOnClickListener(null);

        }
    }

    public void onChooseSuspectBtnClick(View view){
        activateSuspectPanel = getActivateSuspectPanel(getApplicationContext());
        if (activateSuspectPanel.equals("4 suspects")) {
            // when user clicks on it it goes to activity_choose_suspect1
            Intent intent = new Intent(EvidenceList.this, ChooseSuspectPanelFirst.class);
            startActivity(intent);

        } else if (activateSuspectPanel.equals("3 suspects")) {
            // when user clicks on it it goes to activity_choose_suspect2
            Intent intent = new Intent(EvidenceList.this, ChooseSuspectPanelTwo.class);
            startActivity(intent);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(getActivateSuspectPanel(getApplicationContext()).equals("3 suspects")||getActivateSuspectPanel(getApplicationContext()).equals("4 suspects")){
            toggleWhoIsGuiltyBtn();
        }
    }
}