package com.example.android.inventoryapp;

import android.provider.BaseColumns;

final class ProductDatabase {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ProductDatabase() {
    }

    /* Inner class that defines the table contents */
    static abstract class ProdEntry implements BaseColumns {
        static final String TABLE_NAME = "products";
        static final String _ID = "_id";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_QUANTITY = "quantity";
        static final String COLUMN_PRICE = "price";
        static final String COLUMN_SUPPLIER ="supplier";
        static final String COLUMN_IMAGE ="image_location";
    }
}

