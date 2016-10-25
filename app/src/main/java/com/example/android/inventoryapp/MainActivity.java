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
    SQLiteDatabase db;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get our Database object and loader
        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(this);
        db = db_helper.getWritableDatabase();
        getSupportLoaderManager().initLoader(SQL_LOADER, null, MainActivity.this).forceLoad();

        // Show "No Content Available" when listView is empty
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        final ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
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

                int id = ((int) view.getTag());
                product_details.putExtra("_id", id);

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
