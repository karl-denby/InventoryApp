package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;  // increment the version if scheme changes
    // 1 = first creation
    // 2 = insert 1 record
    // 3 = rest of initial data set
    // 4 = pull id for later use

    public static final String DATABASE_NAME = "Products.db";

    // helper definitions
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INT";
    //private static final String DATA_TYPE = " BLOB";
    //public static final String COLUMN_NULLABLE = "NULL";
    //public static final String COLUMN_NOT_NULLABLE = "NOT_NULL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + ProductDatabase.ProdEntry.TABLE_NAME + " (" +
                    ProductDatabase.ProdEntry._ID + " INTEGER PRIMARY KEY," +
                    ProductDatabase.ProdEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_PRICE + INTEGER_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_SUPPLIER + TEXT_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_IMAGE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_TABLE_PRODUCTS =
            "DROP TABLE IF EXISTS " + ProductDatabase.ProdEntry.TABLE_NAME;

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PRODUCTS);
        defaultData(db);
    }

    public void defaultData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 1);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "Nexus 6p");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 5);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 600);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "eric@buy.google.nexus.local");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 2);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "Nexus 5x");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 5);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 380);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "eric@buy.google.nexus.local");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 3);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "Motorola 360");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 2);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 275);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "mike@get.moto.local");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 4);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "Nexus 5");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 1);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 275);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "eric@buy.google.nexus.local");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 5);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "iPhone 6s");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 5);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 600);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "tim@mo.money.than.sense.com");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 6);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "iPhone 5s");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 5);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 380);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "nick@high.price.refurb.com");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 7);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "Apple Watch");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 2);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 500);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "tim@mo.money.than.sense.com");
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, 0);
        db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_PRODUCTS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
/*
    public void deleteDatabase(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_TABLE_PRODUCTS);
    }
*/
}
