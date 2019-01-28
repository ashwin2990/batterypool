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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import static com.example.ashwinshankar.batterypool.R.id.textView;


public class BatteryDetails extends AppCompatActivity {


    TextView mbatteryID;
    TextView mcycleCount;
    TextView mAddress;
    Button mReserve;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_details);
        mbatteryID = (TextView)findViewById(R.id.textView7);
        mcycleCount = (TextView)findViewById(R.id.textView8);
        mAddress = (TextView) findViewById(R.id.textView9);
        mReserve = (Button) findViewById(R.id.button2);
        final String batteryID = getIntent().getStringExtra("BatteryID");
        final String batteryType = getIntent().getStringExtra("BatteryType");
        final String cycleCount = getIntent().getStringExtra("cycleCount");
        final String swapStation = getIntent().getStringExtra("PetrolStation");
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference().child("batterylocations");
        final DatabaseReference transacref = FirebaseDatabase.getInstance().getReference().child("batterylocations/"+swapStation);

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
        mbatteryID.setText(batteryType);
        mcycleCount.setText(cycleCount);

        mReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mIntent1 = new Intent(BatteryDetails.this, PickUpTimer.class);
                final Intent mIntent2 = new Intent(BatteryDetails.this, BatteryList.class);

                transacref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer status=mutableData.child(batteryID + "/status").getValue(Integer.class);
                        Integer count=mutableData.child("count").getValue(Integer.class);
                        if(status==0)
                        {
                            mutableData.child(batteryID + "/status").setValue(1);
                            count--;
                            mutableData.child("count").setValue(count);
                            mIntent1.putExtra("BatteryID", batteryID);
                            mIntent1.putExtra("BatteryType", batteryType);
                            mIntent1.putExtra("PetrolStation",swapStation);
                            startActivity(mIntent1);

                        }
                        else
                        {
                            Toast.makeText(BatteryDetails.this, "Battery Reserved, choose another", Toast.LENGTH_SHORT).show();
                            startActivity(mIntent2);
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    }
                });

            }
        });

    }
}
