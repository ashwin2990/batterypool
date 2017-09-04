package com.example.ashwinshankar.batterypool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BatteryList extends AppCompatActivity {
    DatabaseReference firebase = FirebaseDatabase.getInstance().getReference().child("batterylocations");
    ListView mListView;
    ArrayList<String> batteryID = new ArrayList<>();
    ArrayList<String> cycleCount = new ArrayList<>();
    ArrayList<Integer> batteryImg = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_list);
        final String s = getIntent().getStringExtra("Petrol Pump Name");
        firebase.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String mbatteryid = postSnapshot.getKey().toString();
                            if(!"count".equals(mbatteryid) && !"latitude".equals(mbatteryid) && !"longitude".equals(mbatteryid) && !"address".equals(mbatteryid) && !"1".equals(postSnapshot.child("status").getValue().toString())) {
                                batteryID.add(mbatteryid);
                                cycleCount.add(postSnapshot.child("cycles").getValue().toString());
                                batteryImg.add(R.drawable.batterypool);
                            }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mListView = (ListView) findViewById(R.id.listview);
        MyAdapterBattery myAdapter = new MyAdapterBattery(BatteryList.this, batteryID, batteryImg, cycleCount);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(BatteryList.this, BatteryDetails.class);
                mIntent.putExtra("BatteryID", batteryID.get(i));
                mIntent.putExtra("cycleCount", cycleCount.get(i));
                mIntent.putExtra("batteryImg", batteryImg.get(i));
                mIntent.putExtra("PetrolStation", s);
                startActivity(mIntent);
            }
        });

    }
}


