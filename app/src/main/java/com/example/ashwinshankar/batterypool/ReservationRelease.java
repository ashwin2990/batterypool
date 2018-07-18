package com.example.ashwinshankar.batterypool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReservationRelease extends AppCompatActivity {
    TextView mCancelMessage;
    Button mReserveAnother;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_release);
        final String cancelMessage = getIntent().getStringExtra("Message");
        mCancelMessage = (TextView) findViewById(R.id.textView11);
        mReserveAnother = (Button) findViewById(R.id.button5);
        mCancelMessage.setText(cancelMessage);

        mReserveAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mIntent1 = new Intent(ReservationRelease.this, MapsActivity.class);
                startActivity(mIntent1);
            }
        });

    }
}
