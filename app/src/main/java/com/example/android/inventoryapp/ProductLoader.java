package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import java.util.ArrayList;
import java.util.List;

public class ProductLoader extends AsyncTaskLoader<ArrayList<Product>> {

    // Variables
    protected final String TAG = "ProductLoader";
    SQLiteDatabase db;

    public ProductLoader(Context ctx) {super(ctx);}

    @Override
    public ArrayList<Product> loadInBackground() {

        // Create a product list >> listArray >> ArrayAdapter
        ArrayList<Product> listOfProducts = new ArrayList<Product>() {};

        // Todo: fake data for now
        //listOfProducts.add(new Product("Nexus 6", 1, 400, "Google", 0));
        listOfProducts.add(new Product("Nexus 6p", 5, 600, "Google", 0));
        listOfProducts.add(new Product("Nexus 5x", 5, 380, "Google", 0));
        listOfProducts.add(new Product("Motorola 360", 2, 275, "Motorola", 0));
        listOfProducts.add(new Product("Nexus 5", 1, 275, "Google", 0));
        listOfProducts.add(new Product("iPhone 6s", 5, 600, "Apple", 0));
        listOfProducts.add(new Product("iPhone 5s", 5, 380, "Apple", 0));
        listOfProducts.add(new Product("Apple Watch", 2, 500, "Apple", 0));

        // Get additional row from database
        String[] projection = {
                ProductDatabase.ProdEntry.COLUMN_NAME,
                ProductDatabase.ProdEntry.COLUMN_QUANTITY,
                ProductDatabase.ProdEntry.COLUMN_PRICE,
                ProductDatabase.ProdEntry.COLUMN_SUPPLIER,
                ProductDatabase.ProdEntry.COLUMN_IMAGE
        };

        // Get our Database object and loader
        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(getContext());
        db = db_helper.getWritableDatabase();
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
        listOfProducts.add(new Product(
                c.getString(c.getColumnIndexOrThrow("name")),
                c.getInt(c.getColumnIndexOrThrow("quantity")),
                c.getInt(c.getColumnIndexOrThrow("price")),
                c.getString(c.getColumnIndexOrThrow("supplier")),
                c.getLong(c.getColumnIndexOrThrow("image_resource"))
        ));

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