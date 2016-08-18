package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;  // increment the version if scheme changes
    // 1 = first creation
    // 2 = insert 1 record

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
        /*
        listOfProducts.add(new Product("Nexus 6p", 5, 600, "Google", 0));
        listOfProducts.add(new Product("Nexus 5x", 5, 380, "Google", 0));
        listOfProducts.add(new Product("Motorola 360", 2, 275, "Motorola", 0));
        listOfProducts.add(new Product("Nexus 5", 1, 275, "Google", 0));
        listOfProducts.add(new Product("iPhone 6s", 5, 600, "Apple", 0));
        listOfProducts.add(new Product("iPhone 5s", 5, 380, "Apple", 0));
        listOfProducts.add(new Product("Apple Watch", 2, 500, "Apple", 0));
        */
        ContentValues values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, 1);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, "Nexus 6p");
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, 2);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, 600);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, "bob@buy.google.nexus.local");
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
