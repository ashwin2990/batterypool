package com.example.ashwinshankar.batterypool;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.attr.defaultValue;
import static android.R.attr.key;
import static android.R.attr.radius;

public class LocationList extends AppCompatActivity {


    ArrayList<String> locationName = new ArrayList<>();
    ArrayList<Integer> locationDistance = new ArrayList<>();
    ArrayList<Integer> locationImg = new ArrayList<>();
    ListView mListView;
    DatabaseReference stationLocations = FirebaseDatabase.getInstance().getReference().child("geofirelocations");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeoLocation userLocation;
        double longitude = getIntent().getDoubleExtra("User Longitude",defaultValue);
        double latitude = getIntent().getDoubleExtra("User Latitude",defaultValue);
        userLocation = new GeoLocation(latitude,longitude);
        setContentView(R.layout.activity_location_list);
        getNearbyLocations(userLocation);
    }



    private int search_radius = 50;
    private boolean finished_search = false;
    private int marker_radius = 1;
    private int radius = marker_radius;

    GeoQuery geoQuery;

    private void getNearbyLocations(final GeoLocation userLocation){

        final GeoFire geofire = new GeoFire(stationLocations);
        geoQuery = geofire.queryAtLocation(userLocation,radius);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if(!finished_search && !locationName.contains(key))
                {
                    locationName.add(key);
                    locationDistance.add(radius);
                    locationImg.add(R.drawable.stationicon);
                }


            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if(radius<search_radius){
                    radius++;
                    getNearbyLocations(userLocation);
                }
                else{
                    finished_search = true;
                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

        mListView = (ListView) findViewById(R.id.listview);
        final MyAdapterLocation myAdapter = new MyAdapterLocation(LocationList.this, locationName, locationDistance, locationImg);
        myAdapter.notifyDataSetChanged();
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(LocationList.this, BatteryList.class);
                mIntent.putExtra("Petrol Pump Name", locationName.get(i));
                startActivity(mIntent);
            }
        });


    }
}
