package com.example.ashwinshankar.batterypool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.ashwinshankar.batterypool.R.id.textView;


public class BatteryDetails extends AppCompatActivity {
    DatabaseReference firebase = FirebaseDatabase.getInstance().getReference().child("batterylocations");
    TextView mbatteryID;
    TextView mcycleCount;
    TextView mAddress;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_details);
        mbatteryID = (TextView)findViewById(R.id.textView7);
        mcycleCount = (TextView)findViewById(R.id.textView8);
        mAddress = (TextView) findViewById(R.id.textView9);
        final String batteryID = getIntent().getStringExtra("BatteryID");
        final String cycleCount = getIntent().getStringExtra("cycleCount");
        final String swapStation = getIntent().getStringExtra("PetrolStation");

        firebase.child(swapStation).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address = dataSnapshot.child("address").getValue().toString();
                mAddress.setText(address);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mbatteryID.setText("Battery ID:" + batteryID);
        mcycleCount.setText("Battery Cycles: " + cycleCount);

    }
}
