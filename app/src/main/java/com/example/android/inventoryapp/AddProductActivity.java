package com.example.android.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    private int quantity = 1;
    private final String TAG = "AddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        Button btnAddImage = (Button) findViewById(R.id.btnAddImage);
        Button btnAddProduct = (Button) findViewById(R.id.btnAddProduct);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddProductActivity.this, "Add an Image", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
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
            } else {
                Toast.makeText(AddProductActivity.this, "Good Input", Toast.LENGTH_SHORT).show();
            }

        }
}
