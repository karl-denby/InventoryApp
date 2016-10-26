package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

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

        Button btnSell = (Button) findViewById(R.id.btnDetailSell);
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _id = getProductDetails(db, _id).getId();
                int new_qty = getProductDetails(db, _id).getQuantity() - 1;

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, new_qty);

                // Which row to update, based on the title
                String selection = ProductDatabase.ProdEntry._ID + " = ?";
                String[] selectionArgs = { String.valueOf(_id) };

                if (new_qty >= 0) {
                    int count = db.update(
                            ProductDatabase.ProdEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);
                    db.close();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "None in stock", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnReceived = (Button) findViewById(R.id.btnDetailReceived);
        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _id = getProductDetails(db, _id).getId();
                int new_qty = getProductDetails(db, _id).getQuantity() + 1;

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, new_qty);

                // Which row to update, based on the title
                String selection = ProductDatabase.ProdEntry._ID + " = ?";
                String[] selectionArgs = { String.valueOf(_id) };

                int count = db.update(
                        ProductDatabase.ProdEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                db.close();
                finish();
            }
        });

        Button btnOrder = (Button) findViewById(R.id.btnDetailOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] toEmails = {""};
                toEmails[0] = getProductDetails(db, _id).getSupplier();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, toEmails);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ordering: " + getProductDetails(db, _id).getName());
                intent.putExtra(Intent.EXTRA_TEXT, "Hi " + getProductDetails(db, _id).getSupplier() + ". I would like to order ...");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

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
            ImageView detail_product_image = (ImageView) findViewById(R.id.detail_product_image);
            TextView detail_product_name = (TextView) findViewById(R.id.detail_product_name);
            TextView detail_product_qty = (TextView) findViewById(R.id.detail_product_qty);
            TextView detail_product_price = (TextView) findViewById(R.id.detail_product_price);

            Product prod = getProductDetails(db, _id);

            String encodedBmp = prod.getImageLocation();
            Log.v("Length of Stored BMP", "" + encodedBmp.length());
            try {
                Log.v("String >> BMP", "");
                Bitmap bmp = decodeBmpFromBase64(encodedBmp);
                Log.v("BMP height is: ", "" + bmp.getHeight());
                if (bmp != null) {
                    detail_product_image.setImageBitmap(bmp);
                }
            } catch (Exception e) {
                Log.v("Image Exception", "" + e.toString());
            }

            detail_product_name.setText(getString(R.string.detail_product_name_is, prod.getName()));
            detail_product_qty.setText(getString(R.string.detail_quantity_is, prod.getQuantity()));
            detail_product_price.setText(getString(R.string.detail_price_is, prod.getPrice()));
        }
        db.close();
    }

    private static Bitmap decodeBmpFromBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
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
                    c.getString(c.getColumnIndexOrThrow("image_location"))
            ));
        c.close();
        return prod;
    }
}
