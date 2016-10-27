package com.example.android.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> Products) {
        super(context, 0, Products);
    }

    @Override
    public @NonNull View getView(int position, View convertView,@NonNull ViewGroup parent) {
        // Get the data item for this position
        final Product product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvProductName  = (TextView) convertView.findViewById(R.id.tvProductName);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
        TextView tvPrice= (TextView) convertView.findViewById(R.id.tvPrice);
        Button btnSellOne = (Button) convertView.findViewById(R.id.btnSellOne);

        // Populate the data into the template view using the data object
        tvProductName.setText(product.getName());
        tvQuantity.setText(String.valueOf(product.getQuantity()));
        tvPrice.setText(String.valueOf(product.getPrice()));

        btnSellOne.setTag(product.getId());
        btnSellOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Click:", "id " + product.getId());
                // Todo: replace this with code to delete the given id
            }
        });

        // Put _id into tag so its easy to find later
        convertView.setTag(product.getId());

        // Return the completed view to render on screen
        return convertView;
    }
}
