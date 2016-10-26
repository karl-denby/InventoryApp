package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import java.util.ArrayList;

class ProductLoader extends AsyncTaskLoader<ArrayList<Product>> {

    ProductLoader(Context ctx) {super(ctx);}

    @Override
    public ArrayList<Product> loadInBackground() {

        // Create a product list >> listArray >> ArrayAdapter
        ArrayList<Product> listOfProducts = new ArrayList<Product>() {};

        // Get rows from database
        String[] projection = {
                ProductDatabase.ProdEntry._ID,
                ProductDatabase.ProdEntry.COLUMN_NAME,
                ProductDatabase.ProdEntry.COLUMN_QUANTITY,
                ProductDatabase.ProdEntry.COLUMN_PRICE,
                ProductDatabase.ProdEntry.COLUMN_SUPPLIER,
                ProductDatabase.ProdEntry.COLUMN_IMAGE
        };

        // Get our Database object and loader
        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(getContext());
        SQLiteDatabase db = db_helper.getWritableDatabase();

        // Read data from the DB and add it to listOfProducts
        Cursor c = db.query(
                ProductDatabase.ProdEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                ProductDatabase.ProdEntry._ID + " ASC"
        );

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            listOfProducts.add(new Product(
                    c.getInt(c.getColumnIndexOrThrow("_id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getInt(c.getColumnIndexOrThrow("quantity")),
                    c.getInt(c.getColumnIndexOrThrow("price")),
                    c.getString(c.getColumnIndexOrThrow("supplier")),
                    c.getString(c.getColumnIndexOrThrow("image_location"))
            ));
            c.moveToNext();
        }

        c.close();
        db.close();
        return listOfProducts;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}