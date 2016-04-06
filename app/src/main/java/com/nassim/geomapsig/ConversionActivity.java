package com.nassim.geomapsig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_conversion);

        Intent intentFromConversionFragment = getIntent();
        String newCity = intentFromConversionFragment.getStringExtra("city");

        Intent intentToTableauActivity = new Intent(ConversionActivity.this,
                TableauActivity.class);
        intentToTableauActivity.putExtra("city", newCity);

    }

}
