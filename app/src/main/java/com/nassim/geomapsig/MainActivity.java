package com.nassim.geomapsig;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawer;

    private static final String degresFormat = "^([0-8]?[0-9]|90)";
    private static final String minutesFormat = "(\\s[0-5]?[0-9]')";
    private static final String secondesFormat = "(\\s[0-5]?[0-9](,[0-9])?\")";

    Button buttonConversion, buttonReset;

    TextView txtLatitude, txtLongitude;
    TextView affichageConversionLatitude, affichageConversionLongitude;

    EditText latitudeDecimal, longitudeDecimal;

    public RadioGroup uniteCoordonnees;
    public RadioButton sexagecimale, decimal;

    public double textDegresLatitude, textMinutesLatitude, textSecondesLatitude;

    public double textDegresLongitude, textMinutesLongitude, textSecondesLongitude;

    public double textLatitude, textLongitude;

    private double resultatConversionLatitude, resultatConversionLongitude;

    EditText degresLatitude, minutesLatitude, secondesLatitude;
    EditText degresLongitude, minutesLongitude, secondesLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*****************************************************************************************************/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupDrawerContent(navigationView);

        /*****************************************************************************************************/

        final NumberFormat formatter = new DecimalFormat("#0.000000");

        buttonConversion = (Button) findViewById(R.id.Conversion);
        buttonReset = (Button) findViewById(R.id.Reset);

        uniteCoordonnees = (RadioGroup) findViewById(R.id.uniteCoordonnée);

        sexagecimale = (RadioButton) findViewById(R.id.sexagecimale);
        decimal = (RadioButton) findViewById(R.id.decimal);

        txtLatitude = (TextView) findViewById(R.id.textLatitude);
        txtLongitude = (TextView) findViewById(R.id.textLongitude);

        /*****************************************************************************************************/

        latitudeDecimal = (EditText) findViewById(R.id.latitude_decimal);
        longitudeDecimal = (EditText) findViewById(R.id.longitude_decimal);

        latitudeDecimal.setFilters(new InputFilter[]{new FilterActivity("0", "180")});
        longitudeDecimal.setFilters(new InputFilter[]{new FilterActivity("0", "180")});

        /*****************************************************************************************************/

        degresLatitude = (EditText) findViewById(R.id.degres_latitude);
        minutesLatitude = (EditText) findViewById(R.id.minutes_latitude);
        secondesLatitude = (EditText) findViewById(R.id.secondes_latitude);

        degresLatitude.setFilters(new InputFilter[]{new FilterActivity("0", "90")});
        minutesLatitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});
        secondesLatitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});

        /*****************************************************************************************************/

        degresLongitude = (EditText) findViewById(R.id.degres_longitude);
        minutesLongitude = (EditText) findViewById(R.id.minutes_longitude);
        secondesLongitude = (EditText) findViewById(R.id.secondes_longitude);

        degresLongitude.setFilters(new InputFilter[]{new FilterActivity("0", "90")});
        minutesLongitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});
        secondesLongitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});

        /*****************************************************************************************************/

        affichageConversionLatitude = (TextView) findViewById(R.id.affichageResultatLatitude);
        affichageConversionLongitude = (TextView) findViewById(R.id.affichageResultatLongitude);

        final LinearLayout latitudeSexagecimal = (LinearLayout) findViewById(R.id.latitude_sexagecimal);
        final LinearLayout longitudeSexagecimal = (LinearLayout) findViewById(R.id.longitude_sexagecimal);

        /*****************************************************************************************************/

        uniteCoordonnees.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.sexagecimale) {
                    latitudeSexagecimal.setVisibility(View.VISIBLE);
                    longitudeSexagecimal.setVisibility(View.VISIBLE);
                    latitudeDecimal.setVisibility(View.GONE);
                    longitudeDecimal.setVisibility(View.GONE);

                } else if (checkedId == R.id.decimal) {
                    latitudeSexagecimal.setVisibility(View.GONE);
                    longitudeSexagecimal.setVisibility(View.GONE);
                    latitudeDecimal.setVisibility(View.VISIBLE);
                    longitudeDecimal.setVisibility(View.VISIBLE);

                }
            }
        });

        View.OnClickListener ClickButton = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Conversion:
                        if (sexagecimale.isChecked()) {
                            textDegresLatitude = Double.parseDouble(degresLatitude.getText().toString());
                            textMinutesLatitude = Double.parseDouble(minutesLatitude.getText().toString());
                            textSecondesLatitude = Double.parseDouble(secondesLatitude.getText().toString());

                            textDegresLongitude = Double.parseDouble(degresLongitude.getText().toString());
                            textMinutesLongitude = Double.parseDouble(minutesLongitude.getText().toString());
                            textSecondesLongitude = Double.parseDouble(secondesLongitude.getText().toString());

                            resultatConversionLatitude = textDegresLatitude + textMinutesLatitude / 60 + textSecondesLatitude / 3600;
                            resultatConversionLongitude = textDegresLongitude + textMinutesLongitude / 60 + textSecondesLongitude / 3600;

                            affichageConversionLatitude.setText(String.valueOf("Latitude : " + formatter.format(resultatConversionLatitude) + "°"));
                            affichageConversionLongitude.setText(String.valueOf("Longitude : " + formatter.format(resultatConversionLongitude) + "°"));

                        } else if (decimal.isChecked()) {
                            textLatitude = Double.parseDouble(latitudeDecimal.getText().toString());
                            textLongitude = Double.parseDouble(longitudeDecimal.getText().toString());



                            affichageConversionLatitude.setText(String.valueOf(resultatConversionLatitude));
                            affichageConversionLatitude.setText(String.valueOf(resultatConversionLongitude));
                        }

                        break;

                    case R.id.Reset:
                        latitudeDecimal.getText().clear();
                        longitudeDecimal.getText().clear();

                        degresLongitude.getText().clear();
                        minutesLongitude.getText().clear();
                        secondesLongitude.getText().clear();

                        degresLatitude.getText().clear();
                        minutesLatitude.getText().clear();
                        secondesLatitude.getText().clear();


                        affichageConversionLatitude.setText("");
                        affichageConversionLongitude.setText("");
                        break;

                }
            }
        };

        buttonConversion.setOnClickListener(ClickButton);
        buttonReset.setOnClickListener(ClickButton);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_conversion:
                fragmentClass = ConversionFragment.class;
                break;
            case R.id.nav_tableau:
                fragmentClass = TableauFragment.class;
                break;
            case R.id.nav_maps:
                fragmentClass = MapsFragment.class;
                break;

            default:
                fragmentClass = ConversionFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        // Highlight the selected item has been done by NavigationView
        // menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
