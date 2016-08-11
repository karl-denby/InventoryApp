package com.example.android.inventoryapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.ArrayList;
import java.util.List;

public class ProductLoader extends AsyncTaskLoader<ArrayList<Product>> {

    // Variables
    protected final String TAG = "StoryLoader";

    public ProductLoader(Context ctx) {super(ctx);}

    @Override
    public ArrayList<Product> loadInBackground() {

        // Create a product list >> listArray >> ArrayAdapter
        ArrayList<Product> listOfProducts = new ArrayList<Product>() {};

        // Todo: fake data for now
        listOfProducts.add(new Product("Nexus 6", 1, 400, "Google", 0));
        listOfProducts.add(new Product("Nexus 6p", 5, 600, "Google", 0));
        listOfProducts.add(new Product("Nexus 5x", 5, 380, "Google", 0));
        listOfProducts.add(new Product("Moto360", 2, 275, "Google", 0));
        listOfProducts.add(new Product("Nexus 5", 1, 275, "Google", 0));
        listOfProducts.add(new Product("iPhone 6s", 5, 600, "Apple", 0));
        listOfProducts.add(new Product("iPhone 5s", 5, 380, "Apple", 0));
        listOfProducts.add(new Product("Apple Watch", 2, 500, "Apple", 0));
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