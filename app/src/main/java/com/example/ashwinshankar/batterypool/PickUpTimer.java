package com.example.ashwinshankar.batterypool;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.countDown;
import static com.example.ashwinshankar.batterypool.R.id.textView;
import static com.example.ashwinshankar.batterypool.R.id.textView4;


public class PickUpTimer extends AppCompatActivity {
    TextView textView4;
    TextView mbatteryID;
    TextView mAddress;
    Button mCancelBattery;
    Button mPayNow;
    CountDownTimer timer;
    Dialog dialog,dialog_payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_timer);

        textView4 = (TextView)findViewById(R.id.textView4);
        mbatteryID = (TextView)findViewById(R.id.textView6);
        mAddress = (TextView)findViewById(R.id.textView13);
        mCancelBattery = (Button)findViewById(R.id.button3);
        mPayNow = (Button)findViewById(R.id.button4);

        final String batteryID = getIntent().getStringExtra("BatteryID");
        final String batteryType = getIntent().getStringExtra("BatteryType");
        final String swapStation = getIntent().getStringExtra("PetrolStation");
        final Intent mIntent1 = new Intent(PickUpTimer.this, ReservationRelease.class);
        final Intent mIntent2 = new Intent(PickUpTimer.this,PaidPage.class);

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

        timer = new CountDownTimer(600000, 1000){

            public void onTick(long millisUntilFinished) {
                textView4.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                transacref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer count=mutableData.child("count").getValue(Integer.class);
                        mutableData.child(batteryID + "/status").setValue(0);
                        count++;
                        mutableData.child("count").setValue(count);
                        mIntent1.putExtra("Message","Reservation Time's Up!");
                        startActivity(mIntent1);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                    }
                });
            }

        }.start();

        mCancelBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                transacref.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer count=mutableData.child("count").getValue(Integer.class);
                        mutableData.child(batteryID + "/status").setValue(0);
                        count++;
                        mutableData.child("count").setValue(count);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                    }
                });

                dialog = new Dialog(PickUpTimer.this);
                dialog.setContentView(R.layout.dialogbox);
                dialog.setTitle("BatteryPool");
                Button reserve = (Button) dialog.findViewById(R.id.reserve_button);
                reserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent mIntent1 = new Intent(PickUpTimer.this, MapsActivity.class);
                        startActivity(mIntent1);
                    }
                });
                dialog.show();

            }
        });

        mPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                dialog_payment = new Dialog(PickUpTimer.this);
                dialog_payment.setContentView(R.layout.dialogbox_payment);
                dialog_payment.setTitle("BatteryPool");

                Button reserve = (Button) dialog_payment.findViewById(R.id.reserve_button);
                reserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent mIntent1 = new Intent(PickUpTimer.this, MapsActivity.class);
                        startActivity(mIntent1);
                    }
                });

                dialog_payment.show();
            }
        });





    }

}
