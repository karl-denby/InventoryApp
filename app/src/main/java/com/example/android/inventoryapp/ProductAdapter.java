package com.example.android.inventoryapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> Products) {
        super(context, 0, Products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvProductName  = (TextView) convertView.findViewById(R.id.tvProductName);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
        TextView tvPrice= (TextView) convertView.findViewById(R.id.tvPrice);

        // Populate the data into the template view using the data object
        tvProductName.setText(product.getName());
        tvQuantity.setText(String.valueOf(product.getQuantity()));
        tvPrice.setText(String.valueOf(product.getPrice()));

        // Put _id into tag so its easy to find later
        convertView.setTag(product.getId());

        // Return the completed view to render on screen
        return convertView;
    }
}
