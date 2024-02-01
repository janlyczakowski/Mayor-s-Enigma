package com.tudresden.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    SQLiteDatabase database = null;
    Cursor dbCursor; //go through data records
    LatLng nextLocation;
    static int nextLocationId = 0;
    private static final String PREF_NEXT_LOCATION_ID = "nextLocationId";
    static boolean newLocationUnlocked = false;
    private static final String PREF_NEW_LOCATION_UNLOCKED = "newLocationUnlocked";
    static ArrayList<Integer> locationStatusArray = new ArrayList<>();



    private GoogleMap mMap;
    ActivityResultLauncher<String[]> locationPermissionRequest; //dialog to ask for the permission given by the user

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        ArrayList<PlaceModal> locations = getData();
        SharedPreferencesHelper.saveLocations(getApplicationContext(), locations); // save in shared preferences
        nextLocation = new LatLng(locations.get(nextLocationId).getLatitude(), locations.get(nextLocationId).getLongitude());

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                nextLocationId = getNextLocationId(getApplicationContext());
                if (location != null) {
                    // Handle location changes
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    double accuracy = location.getAccuracy();

                    // calculate distance between current location and next location
                    nextLocation = new LatLng(locations.get(nextLocationId).getLatitude(), locations.get(nextLocationId).getLongitude());
                    double distance = SphericalUtil.computeDistanceBetween(new LatLng(latitude, longitude), nextLocation);
                    ArrayList<PlaceModal> updatedLocations = SharedPreferencesHelper.getLocations(getApplicationContext());
                    boolean nextLocationAlreadyVisited = updatedLocations.get(nextLocationId).getVisited();

                    if ((distance <= 20 && !nextLocationAlreadyVisited) && accuracy < 20) {

                        int idToUpdate = nextLocationId + 1;
                        updateVisitedStatus(idToUpdate);
                        // visited state updates the visibility of the items in evidence list
                        // it does not update the visibility of the markers on the map
                        // for this nextLocationId is responsible
                        ArrayList<PlaceModal> newLocations = SharedPreferencesHelper.getLocations(getApplicationContext());
                        newLocations.get(nextLocationId).setVisited(true);
                        SharedPreferencesHelper.saveLocations(getApplicationContext(), newLocations);

                        Toast toast = Toast.makeText(getApplicationContext(), "Location visited", 1);
                        toast.show();

                        // add shake event
                        ImageButton evidence_list_btn = findViewById(R.id.evidence_list);
                        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        evidence_list_btn.startAnimation(shake);

                        vibrate();

                    }
                }
            }
        };

        // connect to  the fragment
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        // get this map
        mapFragment.getMapAsync(this);

        locationPermissionRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                    if ((fineLocationGranted != null && fineLocationGranted) && (coarseLocationGranted != null && coarseLocationGranted)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(false); // disable location button
                        mMap.getUiSettings().setMapToolbarEnabled(true);
                    } else {
                        Toast.makeText(this,
                                "Location cannot be obtained due to missing permission.",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public static void setNextLocationId(Context context, int value) {
        nextLocationId = value;
        // Save the value to SharedPreferences
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NEXT_LOCATION_ID, Context.MODE_PRIVATE).edit();
        editor.putInt("nextLocationId", value);
        editor.apply();
    }

    // use this method to get the current value of nextLocationId
    public static int getNextLocationId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NEXT_LOCATION_ID, Context.MODE_PRIVATE);
        nextLocationId = prefs.getInt("nextLocationId", 0);
        return nextLocationId;
    }

    public void updateVisitedStatus(int idToUpdate) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String updateQuery = "UPDATE locations SET Visited = 1 WHERE ID = ?";
        Object[] bindArgs = {idToUpdate};

        try {
            database.execSQL(updateQuery, bindArgs);
        } catch (SQLException e) {
            // Handle the exception (e.g., log or show an error message)
            e.printStackTrace();
        } finally {
            // Close the database to free up resources
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        SharedPreferencesHelper.saveIsMapRecreated(getApplicationContext(),true);
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        mMap.getUiSettings().setZoomControlsEnabled(true); // add zoom in zoom out button

        // move zoom in/zoom out button to the upper right corner
        @SuppressLint("ResourceType") View zoomButton = findViewById(R.id.map).findViewById(0x1);

        if (zoomButton != null && zoomButton.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams zp = (RelativeLayout.LayoutParams) zoomButton.getLayoutParams();
            zp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            zp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            zp.setMargins(0, 30, 30, 0);
        }

        addMarkers();

        // move the camera
        CameraPosition cam_pos = new CameraPosition.Builder()
                .target(nextLocation)
                .zoom(14)
                .tilt(0)
                .bearing(0)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cam_pos));

        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        locationPermissionRequest.launch(PERMISSIONS);
    }

    public ArrayList<PlaceModal> getData() {
        // check if database is existing
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
        }

        // get database
        database = dbHelper.getDataBase();

        dbCursor = database.rawQuery("SELECT * FROM locations;", null);

        ArrayList<PlaceModal> placeModalArrayList = new ArrayList<>();


        int index = dbCursor.getColumnIndex("ID");
        int name_index = dbCursor.getColumnIndex("Name");
        int latitude_index = dbCursor.getColumnIndex("Latitude");
        int longitude_index = dbCursor.getColumnIndex("Longitude");
        int visited_index = dbCursor.getColumnIndex("Visited");

        // moving our cursor to first position.
        if (dbCursor.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                Boolean visited_value = (dbCursor.getInt(visited_index) == 0) ? false : true;
                nextLocationId = visited_value == true ? dbCursor.getInt(index) - 1 : nextLocationId;
                placeModalArrayList.add(new PlaceModal(
                        dbCursor.getString(name_index),
                        dbCursor.getDouble(latitude_index),
                        dbCursor.getDouble(longitude_index), visited_value));

            } while (dbCursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        dbCursor.close();

        return placeModalArrayList;
    }

    public void addMarkers() {
        nextLocationId = getNextLocationId(this);
        boolean isMapRecreated = SharedPreferencesHelper.getIsMapRecreated(getApplicationContext());
        if(mMap != null && !isMapRecreated){
            mMap.clear();
        }

        if (nextLocationId == 0) {
            // create all the markers for the visited locations (run at the beginning when the app is opened)

            MarkerOptions myMarker = createMarkers(nextLocationId);
            mMap.addMarker(myMarker);

        } else {
            // create all the markers for the visited locations (run at the beginning when the app is opened)
            for (int i = 0; i <= nextLocationId; i++) {
                MarkerOptions myMarker = createMarkers(i);
                mMap.addMarker(myMarker);

            }
        }
    }

    public MarkerOptions createMarkers(int index) {
        ArrayList<PlaceModal> locations = SharedPreferencesHelper.getLocations(getApplicationContext());
        nextLocation = new LatLng(locations.get(index).getLatitude(), locations.get(index).getLongitude());

        double lat = locations.get(index).getLatitude();
        double lon = locations.get(index).getLongitude();
        String name = locations.get(index).getName();


        // move camera to the marker's location
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));

        // upload a custom icon
        String icon_name = "location_" + (index + 1);
        int resourceId = getResources().getIdentifier(icon_name, "drawable", getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 60, 90, false);

        // create the marker
        MarkerOptions myMarker = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap))
                .position(new LatLng(lat, lon))
                .title(name);

        return myMarker;

    }

    public void onClickLocateMeListener(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            // Check for location permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettings();
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation == null) {
                    // Request location updates with a listener
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            // Remove location updates to save power, as we only need the current location once
                            // Use the current location
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                        }
                    });
                } else {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 17));

                }

            } else {
                // Handle the case where the user didn't grant the location permission
                // You may want to request permission here or show a message to the user
                Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationSettings() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Location services are disabled, show a dialog to the user to enable them
            showEnableLocationDialog();
        }
    }

    private void showEnableLocationDialog() {
        Toast.makeText(this, "Location services are disabled. Please enable them to use this feature.", Toast.LENGTH_LONG).show();

        // Open the location settings screen
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    public void onClickNextLocationListener(View view) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nextLocation, 17));
    }

    public void onClickEvidenceListListener(View view) {
        ArrayList<PlaceModal> locations = SharedPreferencesHelper.getLocations(getApplicationContext());

        //open the evidence list view
        locationStatusArray = new ArrayList<>();
        //add first element as 1 because it's "introduction" which will be always active
        locationStatusArray.add(1);
        nextLocationId = getNextLocationId(getApplicationContext());
        if (nextLocationId == 5) {
            // show the all the items, including last item
            setNewLocationUnlocked(getApplicationContext(), true);
            updateLocationStatus(locationStatusArray, locations.size());
        } else {
            // don't show the last item
            updateLocationStatus(locationStatusArray, locations.size() - 1);
        }

        Intent intent = new Intent(MapActivity.this, EvidenceList.class);
        intent.putExtra("locations", locationStatusArray);
        startActivity(intent);

    }

    public static void setNewLocationUnlocked(Context context, boolean value) {
        newLocationUnlocked = value;
        // Save the value to SharedPreferences
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NEW_LOCATION_UNLOCKED, Context.MODE_PRIVATE).edit();
        editor.putBoolean("newLocationUnlocked", value);
        editor.apply();
    }

    // use this method to get the current value of nextLocationId
    public static boolean getNewLocationUnlocked(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NEW_LOCATION_UNLOCKED, Context.MODE_PRIVATE);
        newLocationUnlocked = prefs.getBoolean("newLocationUnlocked", false);
        return newLocationUnlocked;
    }

    public void updateLocationStatus(ArrayList<Integer> locationStatusArray, int array_length) {
        ArrayList<PlaceModal> locations = SharedPreferencesHelper.getLocations(getApplicationContext());

        for (int i = 0; i < array_length; i++) {
            if (locations.get(i).getVisited()) {
                // assign 1
                locationStatusArray.add(1);
            } else {
                //assign 0
                locationStatusArray.add(0);
            }
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(2000, 250);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(2000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMap != null){
            SharedPreferencesHelper.saveIsMapRecreated(getApplicationContext(),false);
            addMarkers();
        }

    }
}
