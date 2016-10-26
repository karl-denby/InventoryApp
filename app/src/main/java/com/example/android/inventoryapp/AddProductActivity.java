package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_OPEN = 1;
    private int quantity = 1;
    private final String TAG = "AddProductActivity";
    SQLiteDatabase db;
    String productImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(getApplicationContext());
        db = db_helper.getWritableDatabase();

        Button btnAddImage = (Button) findViewById(R.id.btnAddImage);
        Button btnAddProduct = (Button) findViewById(R.id.btnAddProduct);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddProductActivity.this, "Add an Image", Toast.LENGTH_SHORT).show();
                selectImage();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
                dataInsert();
            }
        });

        Button btnMinusQuantity = (Button) findViewById(R.id.btnMinusQuantity);
        Button btnPlusQuantity = (Button) findViewById(R.id.btnPlusQuantity);

        final TextView tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvQuantity.setText(String.format(Locale.ENGLISH, "%d", quantity));

        btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    quantity -= 1;
                    tvQuantity.setText(String.format(Locale.ENGLISH, "%d", quantity));
                } else {
                    Toast.makeText(AddProductActivity.this, "Minimum Quantity is 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity += 1;
                tvQuantity.setText(String.format(Locale.ENGLISH, "%d", quantity));
            }
        });
    }

    private void validateInput() {
        EditText edtProductName = (EditText) findViewById(R.id.edtProductName);
        EditText edtPrice = (EditText) findViewById(R.id.edtPrice);
        EditText edtSupplier = (EditText) findViewById(R.id.edtSupplier);

        final String MESSAGE = "Please add ";
        String missing_fields = "";

        Log.v(TAG, edtProductName.getText().toString());
        if (edtProductName.getText().length() == 0) { missing_fields += ", Product";}
        Log.v(TAG, edtPrice.getText().toString());
        if (edtPrice.getText().toString().length() == 0) { missing_fields += ", Price";}
        Log.v(TAG, edtSupplier.getText().toString());
        if (edtSupplier.getText().toString().length() == 0) { missing_fields += ", Supplier";}

        if (missing_fields.length() != 0) {
            // remove leading , and add a . to list of missing input fields
            missing_fields = missing_fields.substring(1, missing_fields.length());
            missing_fields += ".";
            Toast.makeText(AddProductActivity.this, MESSAGE + missing_fields, Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();

            ImageView ivSelectedFile = (ImageView) findViewById(R.id.ivProductImage);

            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
                productImage = encodeBmpToBase64(bmp, Bitmap.CompressFormat.JPEG, 100);
                Log.v("Length of Original BMP", "" + productImage.length());
            } catch (Exception e) {
                Log.v("Problem with BMP", "" + e.toString());
            }
            Bitmap bmp = decodeBmpFromBase64(productImage);
            ivSelectedFile.setImageBitmap(bmp);
        }
    }

    private static Bitmap decodeBmpFromBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void dataInsert() {
        EditText edtProductName = (EditText) findViewById(R.id.edtProductName);
        TextView tvProductQuantity = (TextView) findViewById(R.id.tvQuantity);
        EditText edtPrice = (EditText) findViewById(R.id.edtPrice);
        EditText edtSupplier = (EditText) findViewById(R.id.edtSupplier);

        int _id = nextFreeNumber();
        String productName = edtProductName.getText().toString();
        String productQuantity = tvProductQuantity.getText().toString();
        String productPrice = edtPrice.getText().toString();
        String productSupplier = edtSupplier.getText().toString();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ProductDatabase.ProdEntry._ID, _id);
        values.put(ProductDatabase.ProdEntry.COLUMN_NAME, productName);
        values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, productQuantity);
        values.put(ProductDatabase.ProdEntry.COLUMN_PRICE, productPrice);
        values.put(ProductDatabase.ProdEntry.COLUMN_SUPPLIER, productSupplier);
        values.put(ProductDatabase.ProdEntry.COLUMN_IMAGE, productImage);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ProductDatabase.ProdEntry.TABLE_NAME, null, values);

        Toast.makeText(AddProductActivity.this, "Added row " + newRowId, Toast.LENGTH_SHORT).show();
    }

    private static String encodeBmpToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private int nextFreeNumber() {
        // Get rows from database
        String[] projection = {
                ProductDatabase.ProdEntry._ID,
        };

        Cursor c = db.query(
                ProductDatabase.ProdEntry.TABLE_NAME,
                projection, //columns
                null, //selection
                null, //selectionArgs
                null, //groupBy
                null, //having
                ProductDatabase.ProdEntry._ID + " DESC"
        );

        // We only want one result
        c.moveToFirst();
        int _id = c.getInt(c.getColumnIndexOrThrow("_id"));
        c.close();

        // highest value we found incremented by 1
        return _id + 1;
    }
}