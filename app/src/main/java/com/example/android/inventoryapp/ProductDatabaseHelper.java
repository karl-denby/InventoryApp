package com.example.android.inventoryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;  // increment the version if scheme changes
    public static final String DATABASE_NAME = "Products.db";

    // helper definitions
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INT";
    private static final String DATA_TYPE = " BLOB";
    public static final String COLUMN_NULLABLE = "NULL";
    public static final String COLUMN_NOT_NULLABLE = "NOT_NULL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductDatabase.ProdEntry.TABLE_NAME + " (" +
                    ProductDatabase.ProdEntry._ID + " INTEGER PRIMARY KEY," +
                    ProductDatabase.ProdEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_PRICE + INTEGER_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_SUPPLIER + TEXT_TYPE + COMMA_SEP +
                    ProductDatabase.ProdEntry.COLUMN_IMAGE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductDatabase.ProdEntry.TABLE_NAME;

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDatabase(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}

