package com.example.lab2_bhautikpethani_c0854487_android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "product_db";

    private static final String TABLE_NAME = "product";
    private static final String COLUMN_ID = "product_id";
    private static final String COLUMN_NAME = "product_name";
    private static final String COLUMN_DESC = "product_description";
    private static final String COLUMN_PRICE = "product_price";

    public DatabaseHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT product_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(20) NOT NULL, " +
                COLUMN_DESC + " VARCHAR(100) NOT NULL, " +
                COLUMN_PRICE + " DOUBLE NOT NULL);";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlQuery = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sqlQuery);
        onCreate(sqLiteDatabase);
    }

    public boolean addNewProduct(String name, String description, String price) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESC, description);
        contentValues.put(COLUMN_PRICE, price);

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }
}
