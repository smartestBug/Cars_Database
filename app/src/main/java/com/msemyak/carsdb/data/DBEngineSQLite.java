package com.msemyak.carsdb.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.msemyak.carsdb.data.model.Car;
import com.msemyak.carsdb.data.model.Owner;

import java.util.ArrayList;
import java.util.List;

public class DBEngineSQLite extends SQLiteOpenHelper implements DBEngine {

    // имя и версия базы данных
    private static final String DATABASE_NAME = "CarsAndOwners.db";
    private static final int DATABASE_VERSION = 1;

    // определим поля таблицы с владельцами
    private static final String OWNERS_TABLE_NAME = "owners";
    private static final String OWNERS_COLUMN_ID = "id";
    private static final String OWNERS_COLUMN_NAME = "name";
    private static final String OWNERS_COLUMN_MIDNAME = "midname";
    private static final String OWNERS_COLUMN_SURNAME = "surname";
    private static final String OWNERS_COLUMN_PASSPORT = "passport";
    private static final String OWNERS_COLUMN_ADDRESS = "address";

    // определим поля таблицы с автомобилями
    private static final String CARS_TABLE_NAME = "cars";
    private static final String CARS_COLUMN_ID = "id";
    private static final String CARS_COLUMN_OWNERID = "owner_id";
    private static final String CARS_COLUMN_BRAND = "brand";
    private static final String CARS_COLUMN_MODEL = "model";
    private static final String CARS_COLUMN_YEAR = "year";
    private static final String CARS_COLUMN_REGNUMBER = "regnumber";

    public DBEngineSQLite(Context context) {

        //вызов предшественника создаст базу данных
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // создадим таблицы
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

    // при обновлении версии базы делаем что нам нужно для работы с новой версией
    // в нашем случае просто удаляем таблицы и создаем новые (наш случай, в принципе пока не наступает)
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

    // добавить новый автомобиль
    @Override
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

    // возвращает список всех автомобилей
    @Override
    public List<Car> getAllCars() {
        String selectQuery = "SELECT * FROM " + CARS_TABLE_NAME;
        return execQueryReturnCarLIst(selectQuery);
    }

    // возвращает список всех автомобилей без владельца
    @Override
    public List<Car> getAllCarsWithoutOwner() {
        String selectQuery = "SELECT * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + " = 0";
        return execQueryReturnCarLIst(selectQuery);
    }

    // возвращает владельца конкретного автомобиля
    @Override
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

        cursor.close();
        return carsList;
    }

    // возвращает количество автомобилей, которые не принадлежат никомуу
    @Override
    public int getCarsWithoutOwnerCount() {

        String selectQuery = "SELECT * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    // возвращает конкретный автомобиль
    @Override
    public Car getCar(int id) {

        String selectQuery = "SELECT  * FROM " + CARS_TABLE_NAME + " WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) cursor.moveToFirst();

        Car car = new Car(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        cursor.close();
        return car;
    }

    // удаляем автомобиль
    @Override
    public void deleteCar(int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARS_TABLE_NAME, CARS_COLUMN_ID + " = ?", new String[]{String.valueOf(carId)});
        db.close();
    }

    // обновляем данные про автомобиль
    @Override
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

    // связываем владельца и автомобиль
    @Override
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

    // создаем нового владельца
    @Override
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

    // удаляем владельца
    @Override
    public void deleteOwner(int ownerId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // удаляем и владельца...
        db.delete(OWNERS_TABLE_NAME, OWNERS_COLUMN_ID + " = ?", new String[]{String.valueOf(ownerId)});

        // и связи этого человека с автомобилями, которые с ним связаны
        ContentValues values = new ContentValues();
        values.put(CARS_COLUMN_OWNERID, 0);

        db.update(CARS_TABLE_NAME, values, CARS_COLUMN_OWNERID + " = ?", new String[]{String.valueOf(ownerId)});

        db.close();
    }

    // возвращает конкретного пользователя
    @Override
    public Owner getOwner(int id) {

        String selectQuery = "SELECT  * FROM " + OWNERS_TABLE_NAME + " WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) cursor.moveToFirst();

        Owner owner = new Owner(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        cursor.close();
        return owner;
    }

    // обновление записи конкретного владельца
    @Override
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

    // возвращает количество автомобилей, принадлежащих конкретному владельцу
    @Override
    public int getOwnerCarsCount(int ownerId) {

        String countQuery = "SELECT * FROM " + CARS_TABLE_NAME + " WHERE " + CARS_COLUMN_OWNERID + "=" + ownerId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // возвращает список всех владельцев
    @Override
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

        cursor.close();
        return ownersList;
    }


}
