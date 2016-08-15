package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Product>> {

    private static final int SQL_LOADER = 0;

    // Loader lifecycle Events
    @Override
    public ProductLoader onCreateLoader(int id, Bundle args) {
        return new ProductLoader(this);
    }

    @Override public void onLoaderReset(Loader<ArrayList<Product>> param1) {
        // todo: something
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Product>> loader, ArrayList<Product> data) {
        ProductAdapter productAdapter = new ProductAdapter(this, data);

        // attach data to listView and tell it to refresh
        ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
        lvProducts.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

/* Functionality
ListView Population:
The listView populates with the current products stored in the table.

Add product button:
The Add product button prompts the user for information about the product and a picture, each of
which are then properly stored in the table.

Sale button:
The sale button on each list item properly reduces the quantity available by one, unless that would
result in a negative quantity.

Detail View intent:
Clicking on the rest of each list item sends the user to the detail screen for the correct product.

Order Button:
The ‘order more’ button sends an intent to either a phone app or an email app to contact the supplier
using the information stored in the database.

Delete button:
The delete button prompts the user for confirmation and, if confirmed, deletes the product record
entirely and sends the user back to the main activity.
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get our Database object and loader
        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(this);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        getSupportLoaderManager().initLoader(SQL_LOADER, null, MainActivity.this).forceLoad();

        // Show "No Content Available" when listView is empty
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
        TextView tvNoContent = (TextView) findViewById(R.id.tvNoContent);
        lvProducts.addHeaderView(header);
        lvProducts.setEmptyView(tvNoContent);

        // Setup listener for "Add Product"
        Button btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_product = new Intent(MainActivity.this, AddProductActivity.class);

                startActivity(add_product);
            }
        });

        // Clicking an list item brings you to ProductActivity/detail_layout
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent product_details = new Intent(MainActivity.this, ProductActivity.class);

                startActivity(product_details);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(SQL_LOADER, null, MainActivity.this).forceLoad();
    }

}
