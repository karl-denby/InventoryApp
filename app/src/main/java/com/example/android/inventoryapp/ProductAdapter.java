package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

    Context ctx;

    public ProductAdapter(Context context, ArrayList<Product> Products) {
        super(context, 0, Products);
        ctx = context;
    }

    @Override
    public @NonNull View getView(int position, View convertView,@NonNull final ViewGroup parent) {
        // Get the data item for this position
        final Product product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvProductName  = (TextView) convertView.findViewById(R.id.tvProductName);
        final TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
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
                ProductDatabaseHelper db_helper = new ProductDatabaseHelper(ctx);
                int _id = product.getId();
                int new_qty = product.getQuantity() - 1;

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(ProductDatabase.ProdEntry.COLUMN_QUANTITY, new_qty);
                SQLiteDatabase db = db_helper.getWritableDatabase();

                // Which row to update, based on the title
                String selection = ProductDatabase.ProdEntry._ID + " = ?";
                String[] selectionArgs = { String.valueOf(_id) };

                if (new_qty >= 0) {
                    db.update(
                            ProductDatabase.ProdEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);
                    db.close();
                    tvQuantity.setText(new_qty + "");
                    product.setQuantity(new_qty);
                } else {
                    Toast.makeText(ctx, "None in stock", Toast.LENGTH_SHORT).show();
                }
                db_helper.close();
            }
        });

        // Put _id into tag so its easy to find later
        convertView.setTag(product.getId());

        // Return the completed view to render on screen
        return convertView;
    }
}
