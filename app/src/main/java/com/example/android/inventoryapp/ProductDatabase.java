package com.example.android.inventoryapp;

import android.provider.BaseColumns;

public final class ProductDatabase {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ProductDatabase() {
    }

    /* Inner class that defines the table contents */
    public static abstract class ProdEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_SUPPLIER ="supplier";
        public static final String COLUMN_IMAGE ="image_resource";
    }
}

