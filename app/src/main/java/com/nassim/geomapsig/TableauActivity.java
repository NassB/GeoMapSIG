package com.nassim.geomapsig;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tableau);

        Intent intentConversionActivity = getIntent();
        String newCity = intentConversionActivity.getStringExtra("city");

        TextView newCityInTableLayout = new TextView(getApplicationContext());

        TableLayout tableauCoordonnees = (TableLayout)findViewById(R.id.tableauCoordonneesLayout);
        TableRow newTableRowForTable = new TableRow(getApplicationContext());
        newTableRowForTable.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        newCityInTableLayout.setText(newCity);
        newCityInTableLayout.setTextColor(Color.BLACK);
        newCityInTableLayout.setGravity(Gravity.CENTER);
        newTableRowForTable.addView(newCityInTableLayout);
        tableauCoordonnees.addView(newTableRowForTable);



    }

}
