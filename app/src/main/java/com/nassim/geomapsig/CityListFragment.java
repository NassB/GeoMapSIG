package com.nassim.geomapsig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nassim.geomapsig.MainActivity.TAG_CITY;
import static com.nassim.geomapsig.MainActivity.TAG_LATITUDE;
import static com.nassim.geomapsig.MainActivity.TAG_LONGITUDE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //CityListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CityListFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityListFragment extends Fragment {

    String cityName;
    String cityLatitude;
    String cityLongitude;

    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View listView = inflater.inflate(R.layout.fragment_listview, container, false);

        final ArrayList<HashMap<String, String>> citiesList = new ArrayList<>();

        ListView lvCity = (ListView) listView.findViewById(R.id.CityLv);
        HashMap<String, String> cities = null;
        String[] cityFrom = new String[]{TAG_CITY, TAG_LATITUDE, TAG_LONGITUDE};
        int[] cityTo = new int[]{R.id.cityNameLv, R.id.cityLatLv, R.id.cityLongLv};

        DatabaseHandler db = new DatabaseHandler(getContext());

        Log.d("Reading", "Reading all cities for listview..");

        List<City> citiesFromDB = db.getAllCities();
        int rows = db.getCitiesCount();

        for (final City ct : citiesFromDB) {

            int cityID = ct.getID();
            cityName = ct.getName();
            cityLatitude = ct.getLatitude();
            cityLongitude = ct.getLongitude();

            for (int i = 0; i < rows; i++) {
                cities = new HashMap<>();
                cities.put(TAG_CITY, cityName);
                cities.put(TAG_LATITUDE, cityLatitude);
                cities.put(TAG_LONGITUDE, cityLongitude);
            }

            citiesList.add(cities);

            String log = "Id: " + cityID + ", Name: " + cityName + ", Latitude: " + cityLatitude + ", Longitude: " + cityLongitude;
            // Writing cities to log
            Log.d("Name", log);
        }

        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> city = citiesList.get(position);

                Intent inMapsActivity = new Intent(getContext(), MapsActivity.class);
                inMapsActivity.putExtra(TAG_CITY, city.get(TAG_CITY));
                inMapsActivity.putExtra(TAG_LATITUDE, city.get(TAG_LATITUDE));
                inMapsActivity.putExtra(TAG_LONGITUDE, city.get(TAG_LONGITUDE));

                Log.d(TAG_CITY, city.get(TAG_CITY));
                Log.d(TAG_LATITUDE, city.get(TAG_LATITUDE));
                Log.d(TAG_LONGITUDE, city.get(TAG_LONGITUDE));

                startActivity(inMapsActivity);
            }

        });

        SimpleAdapter custom_adapter_city = new SimpleAdapter(getContext(), citiesList,
                R.layout.custom_listview_cities, cityFrom, cityTo);
        lvCity.setAdapter(custom_adapter_city);

        return listView;
    }
}
