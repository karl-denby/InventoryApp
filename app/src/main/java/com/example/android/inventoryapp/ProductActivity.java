package com.example.android.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity {

    int _id;
    SQLiteDatabase db;

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(getApplicationContext());
        db = db_helper.getWritableDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(getApplicationContext());
        db = db_helper.getWritableDatabase();

        Button btnDelete = (Button) findViewById(R.id.btnDetailDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areYouSureDelete();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getInt("_id");
            Toast.makeText(getApplicationContext(), "Product _id is " + _id, Toast.LENGTH_SHORT).show();

            // Load Data for the provided id
            TextView detail_product_name = (TextView) findViewById(R.id.detail_product_name);
            TextView detail_product_qty = (TextView) findViewById(R.id.detail_product_qty);
            TextView detail_product_price = (TextView) findViewById(R.id.detail_product_price);

            Product prod = getProductDetails(db, _id);
            detail_product_name.setText(getString(R.string.detail_product_name_is, prod.getName()));
            detail_product_qty.setText(getString(R.string.detail_quantity_is, prod.getQuantity()));
            detail_product_price.setText(getString(R.string.detail_price_is, prod.getPrice()));
        }
        db.close();
    }

    private void areYouSureDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Do you really want to remove this product?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(ProductActivity.this, "Deleting", Toast.LENGTH_SHORT).show();

                        String selection = ProductDatabase.ProdEntry._ID + "= ?";  // WHERE
                        String[] selectionArgs = new String[]{ String.valueOf(_id) };

                        db.delete(ProductDatabase.ProdEntry.TABLE_NAME, selection, selectionArgs);

                        Intent main_activity = new Intent(ProductActivity.this, MainActivity.class);
                        startActivity(main_activity);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private Product getProductDetails(SQLiteDatabase db, int _id) {
        // Get rows from database
        String[] projection = {
                ProductDatabase.ProdEntry._ID,
                ProductDatabase.ProdEntry.COLUMN_NAME,
                ProductDatabase.ProdEntry.COLUMN_QUANTITY,
                ProductDatabase.ProdEntry.COLUMN_PRICE,
                ProductDatabase.ProdEntry.COLUMN_SUPPLIER,
                ProductDatabase.ProdEntry.COLUMN_IMAGE
        };

        // WHERE _ID = ?
        String selection = ProductDatabase.ProdEntry._ID + " = " + _id;

        Cursor c = db.query(
                ProductDatabase.ProdEntry.TABLE_NAME,
                projection, //columns
                selection, //selection
                null, //selectionArgs
                null, //groupBy
                null, //having
                ProductDatabase.ProdEntry._ID + " ASC"
        );

        // We only want one result
        c.moveToFirst();
        Product prod = (new Product(
                    c.getInt(c.getColumnIndexOrThrow("_id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getInt(c.getColumnIndexOrThrow("quantity")),
                    c.getInt(c.getColumnIndexOrThrow("price")),
                    c.getString(c.getColumnIndexOrThrow("supplier")),
                    c.getLong(c.getColumnIndexOrThrow("image_resource"))
            ));
        c.close();
        return prod;
    }
}
