package com.example.android.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity {

    int _id;
    SQLiteDatabase db;

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
                areYouSure();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getInt("_id");
            Toast.makeText(getApplicationContext(), "Product _id is " + _id, Toast.LENGTH_SHORT).show();
        }
    }

    private void areYouSure() {
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

}
