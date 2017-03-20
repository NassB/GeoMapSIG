package com.nassim.geomapsig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nassim on 14/02/2017 for GeoMapSIG.
 */

class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CityDatabase.db";
    private static final String TABLE_CITY = "City";

    private static final String CITY_KEY = "id";
    private static final String CITY_NAME = "name";
    private static final String CITY_LATITUDE = "latitude";
    private static final String CITY_LONGITUDE = "longitude";
    private static final String CITY_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_CITY + ";";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "("
                + CITY_KEY + " INTEGER PRIMARY KEY,"
                + CITY_NAME + " TEXT," + CITY_LATITUDE + " TEXT,"
                + CITY_LONGITUDE + " TEXT" + ")";
        db.execSQL(CREATE_CITY_TABLE);
        Log.d("createDb", "Create Database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CITY_TABLE_DROP);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    // Adding new city
    void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITY_NAME, city.getName());
        values.put(CITY_LATITUDE, city.getLatitude());
        values.put(CITY_LONGITUDE, city.getLongitude());

        // Inserting Row
        db.insert(TABLE_CITY, null, values);
        db.close(); // Closing database connection
    }

    // Getting single city
    public City getCity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CITY, new String[]{CITY_KEY, CITY_NAME,
                        CITY_LATITUDE, CITY_LONGITUDE}, CITY_KEY + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        // return contact
        assert cursor != null;
        return new City(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
    }

    // Getting All cities
    public List<City> getAllCities() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CITY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<City> cityList = new ArrayList<>();
        //List<City> cityList = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setID(Integer.parseInt(cursor.getString(0)));
                city.setName(cursor.getString(1));
                city.setLatitude(cursor.getString(2));
                city.setLongitude(cursor.getString(3));
                // Adding contact to list
                cityList.add(city);
            } while (cursor.moveToNext());
        }

        // return city list
        return cityList;
    }

    // Getting cities Count
    int getCitiesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single city
    public int updateCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITY_NAME, city.getName());
        values.put(CITY_LATITUDE, city.getLatitude());
        values.put(CITY_LONGITUDE, city.getLongitude());

        // updating row
        return db.update(TABLE_CITY, values, CITY_KEY + " = ?",
                new String[]{String.valueOf(city.getID())});
    }

    // Deleting single city
    public void deleteCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITY, CITY_KEY + " = ?",
                new String[]{String.valueOf(city.getID())});
        db.close();
    }
}

