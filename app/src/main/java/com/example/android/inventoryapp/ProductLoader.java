package com.example.android.inventoryapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.ArrayList;
import java.util.List;

public class ProductLoader extends AsyncTaskLoader<List<Product>> {

    // Variables
    protected final String TAG = "StoryLoader";

    public ProductLoader(Context ctx) {
        super(ctx);
    }

    /****************************************************/
    /** (1) A task that performs the asynchronous load **/
    /****************************************************/

    @Override
    public List<Product> loadInBackground() {

        // Create a product list >> listArray >> ArrayAdapter
        List<Product> listOfProducts = new ArrayList<Product>() {};

        // Todo: fake data for now
        listOfProducts.add(new Product("Nexus 6", 1, 400, "Google", 0));
        listOfProducts.add(new Product("Nexus 6p", 5, 600, "Google", 0));
        listOfProducts.add(new Product("Nexus 5x", 5, 380, "Google", 0));
        listOfProducts.add(new Product("Moto 360", 2, 275, "Google", 0));

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