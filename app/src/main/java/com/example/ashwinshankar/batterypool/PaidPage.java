package com.example.ashwinshankar.batterypool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaidPage extends AppCompatActivity {

    Button mReserveAnother;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_page);
        mReserveAnother = (Button) findViewById(R.id.button6);

        mReserveAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent mIntent1 = new Intent(PaidPage.this, MapsActivity.class);
                startActivity(mIntent1);
            }
        });
    }
}
