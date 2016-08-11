package com.example.android.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    private int quantity = 1;

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
                Toast.makeText(AddProductActivity.this, "Validating input", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnMinusQuantity = (Button) findViewById(R.id.btnMinusQuantity);
        Button btnPlusQuantity = (Button) findViewById(R.id.btnPlusQuantity);

        final TextView tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvQuantity.setText(Integer.toString(quantity));

        btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    quantity -= 1;
                    tvQuantity.setText(Integer.toString(quantity));
                } else {
                    Toast.makeText(AddProductActivity.this, "Minimum Quantity is 0", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity += 1;
                tvQuantity.setText(Integer.toString(quantity));
            }
        });


    }
}
