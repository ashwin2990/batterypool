package com.example.ashwinshankar.batterypool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    Button mReserve;
    private String status;
    //private static String count ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_details);
        mbatteryID = (TextView)findViewById(R.id.textView7);
        mcycleCount = (TextView)findViewById(R.id.textView8);
        mAddress = (TextView) findViewById(R.id.textView9);
        mReserve = (Button) findViewById(R.id.button2);
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

        mReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mIntent1 = new Intent(BatteryDetails.this, CountDownTimer.class);
                final Intent mIntent2 = new Intent(BatteryDetails.this, BatteryList.class);

                firebase.child(swapStation).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        status = dataSnapshot.child(batteryID).child("status").getValue().toString();
                        //count=dataSnapshot.child("count").getValue().toString();
                        //System.out.println("count hereeee"+count);


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(!"1".equals(status)){

                    //System.out.println("swapStation"+swapStation);
                    //System.out.println("swapStation"+batteryID);
                   // Integer count_int=Integer.parseInt(count);
                   // count_int--;
                    firebase.child(swapStation).child(batteryID).child("status").setValue(1);
                    //firebase.child(swapStation).child("count").setValue(count_int);
                    startActivity(mIntent1);
                }
                else{
                    startActivity(mIntent2);
                }



            }
        });

    }
}
