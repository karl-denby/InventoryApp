package com.example.android.inventoryapp;

public class Product {
    private String mName;
    private int mQuantity;
    private int mPrice;
    private String mSupplier;
    private long mImageResource;

    public Product (String name, int quantity, int price, String supplier) {
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mSupplier = supplier;
    }

    public Product (String name, int quantity, int price, String supplier, long imageResource) {
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mSupplier = supplier;
        mImageResource = imageResource;
    }

    public void setImageResource(long imageResource) {
        mImageResource = imageResource;
    }

    public String getName() {return mName;}

    public int getQuantity() {return mQuantity;}

    public int getPrice() {return mPrice;}

    public String getSupplier() {return mSupplier;}

    public long getImageResource() {return mImageResource;}
}
