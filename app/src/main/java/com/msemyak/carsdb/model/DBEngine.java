package com.msemyak.carsdb.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBEngine extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CarsAndOwners.db";
    public static final int DATABASE_VERSION = 1;

    public static final String OWNERS_TABLE_NAME = "owners";
    public static final String OWNERS_COLUMN_ID = "id";
    public static final String OWNERS_COLUMN_NAME = "name";
    public static final String OWNERS_COLUMN_MIDNAME = "midname";
    public static final String OWNERS_COLUMN_SURNAME = "surname";
    public static final String OWNERS_COLUMN_PASSPORT = "passport";
    public static final String OWNERS_COLUMN_ADDRESS = "address";

    public static final String CARS_TABLE_NAME = "cars";
    public static final String CARS_COLUMN_ID = "id";
    public static final String CARS_COLUMN_OWNERID = "owner_id";
    public static final String CARS_COLUMN_BRAND = "brand";
    public static final String CARS_COLUMN_MODEL = "model";
    public static final String CARS_COLUMN_YEAR = "year";
    public static final String CARS_COLUMN_REGNUMBER = "regnumber";


    public DBEngine(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_OWNERS_TABLE =
                "CREATE TABLE " + OWNERS_TABLE_NAME + "("
                        + OWNERS_COLUMN_ID + " INTEGER PRIMARY KEY, "
                        + OWNERS_COLUMN_NAME + " TEXT, "
                        + OWNERS_COLUMN_MIDNAME + " TEXT, "
                        + OWNERS_COLUMN_SURNAME + " TEXT, "
                        + OWNERS_COLUMN_PASSPORT + " TEXT, "
                        + OWNERS_COLUMN_ADDRESS + " TEXT"
                        + ")";

        Log.d("DBA", "" + CREATE_OWNERS_TABLE);

        String CREATE_CARS_TABLE =
                "CREATE TABLE " + CARS_TABLE_NAME + "("
                        + CARS_COLUMN_ID + " INTEGER PRIMARY KEY, "
                        + CARS_COLUMN_OWNERID + " INTEGER, "
                        + CARS_COLUMN_BRAND + " TEXT, "
                        + CARS_COLUMN_MODEL + " TEXT, "
                        + CARS_COLUMN_YEAR + " TEXT, "
                        + CARS_COLUMN_REGNUMBER + " TEXT"
                        + ")";

        Log.d("DBA", "" + CREATE_CARS_TABLE);

        db.execSQL(CREATE_OWNERS_TABLE);
        db.execSQL(CREATE_CARS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + OWNERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CARS_TABLE_NAME);

        onCreate(db);
    }


    /************************
     *
     *
     * CAR DATABASE METHODS
     *
     *
     ************************/

    public int addCar(Car car) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARS_COLUMN_OWNERID, car.getOwnerId());
        values.put(CARS_COLUMN_BRAND, car.getBrand());
        values.put(CARS_COLUMN_MODEL, car.getModel());
        values.put(CARS_COLUMN_YEAR, car.getYear());
        values.put(CARS_COLUMN_REGNUMBER, car.getRegnumber());

        int newCar = (int) db.insert(CARS_TABLE_NAME, null, values);

        db.close();

        return newCar;
    }

    public List<Car> getAllCars() {
        String selectQuery = "SELECT * FROM " + CARS_TABLE_NAME;
        return execQueryReturnCarLIst(selectQuery);
    }

    public List<Car> getAllCarsWithoutOwner() {
        String selectQuery = "SELECT * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + " = 0";
        return execQueryReturnCarLIst(selectQuery);
    }

    public List<Car> getOwnerCars(int ownerId) {

        String selectQuery = "SELECT  * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + "=" + ownerId;
        return execQueryReturnCarLIst(selectQuery);
    }

    private List<Car> execQueryReturnCarLIst(String selectQuery) {
        List<Car> carsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(cursor.getInt(0));
                car.setOwnerId(cursor.getInt(1));
                car.setBrand(cursor.getString(2));
                car.setModel(cursor.getString(3));
                car.setYear(cursor.getString(4));
                car.setRegnumber(cursor.getString(5));

                carsList.add(car);

            } while (cursor.moveToNext());
        }
        return carsList;
    }

    public int getCarsWithoutOwnerCount() {

        String selectQuery = "SELECT * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public Car getCar(int id) {

        String selectQuery = "SELECT  * FROM " + CARS_TABLE_NAME + " WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) cursor.moveToFirst();

        Car car = new Car(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return car;
    }

    public void deleteCar(int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARS_TABLE_NAME, CARS_COLUMN_ID + " = ?", new String[]{String.valueOf(carId)});
        db.close();
    }

    public int updateCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARS_COLUMN_OWNERID, car.getOwnerId());
        values.put(CARS_COLUMN_BRAND, car.getBrand());
        values.put(CARS_COLUMN_MODEL, car.getModel());
        values.put(CARS_COLUMN_YEAR, car.getYear());
        values.put(CARS_COLUMN_REGNUMBER, car.getRegnumber());

        return db.update(CARS_TABLE_NAME, values, CARS_COLUMN_ID + " = ?", new String[]{String.valueOf(car.getId())});
    }

    public int linkCarToOwner(int ownerId, int carId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARS_COLUMN_OWNERID, ownerId);

        return db.update(CARS_TABLE_NAME, values, CARS_COLUMN_ID + " = ?", new String[]{String.valueOf(carId)});
    }


    /************************
     *
     *
     * OWNER DATABASE METHODS
     *
     *
     ************************/

    public int addOwner(Owner owner) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OWNERS_COLUMN_NAME, owner.getName());
        values.put(OWNERS_COLUMN_MIDNAME, owner.getMidname());
        values.put(OWNERS_COLUMN_SURNAME, owner.getSurname());
        values.put(OWNERS_COLUMN_PASSPORT, owner.getPassport());
        values.put(OWNERS_COLUMN_ADDRESS, owner.getAddress());

        int newOwner = (int) db.insert(OWNERS_TABLE_NAME, null, values);

        db.close();

        return newOwner;
    }


    public void deleteOwner(int ownerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OWNERS_TABLE_NAME, OWNERS_COLUMN_ID + " = ?", new String[]{String.valueOf(ownerId)});
        db.close();
    }

    public Owner getOwner(int id) {

        String selectQuery = "SELECT  * FROM " + OWNERS_TABLE_NAME + " WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) cursor.moveToFirst();

        Owner owner = new Owner(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return owner;
    }

    public int updateOwner(Owner owner) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OWNERS_COLUMN_NAME, owner.getName());
        values.put(OWNERS_COLUMN_MIDNAME, owner.getMidname());
        values.put(OWNERS_COLUMN_SURNAME, owner.getSurname());
        values.put(OWNERS_COLUMN_PASSPORT, owner.getPassport());
        values.put(OWNERS_COLUMN_ADDRESS, owner.getAddress());

        return db.update(OWNERS_TABLE_NAME, values, OWNERS_COLUMN_ID + " = ?", new String[]{String.valueOf(owner.getId())});
    }

    public int getOwnerCarsCount(int ownerId) {

        String countQuery = "SELECT * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + "=" + ownerId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<Owner> getAllOwners() {
        List<Owner> ownersList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + OWNERS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Owner owner = new Owner();
                owner.setId(Integer.parseInt(cursor.getString(0)));
                owner.setName(cursor.getString(1));
                owner.setMidname(cursor.getString(2));
                owner.setSurname(cursor.getString(3));
                owner.setPassport(cursor.getString(4));
                owner.setAddress(cursor.getString(5));

                ownersList.add(owner);
            } while (cursor.moveToNext());
        }
        return ownersList;
    }


}
