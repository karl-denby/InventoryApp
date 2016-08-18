package com.example.android.inventoryapp;

public class Product {
    private int mId;
    private String mName;
    private int mQuantity;
    private int mPrice;
    private String mSupplier;
    private long mImageResource;

    public Product (int Id, String name, int quantity, int price, String supplier) {
        mId = Id;
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mSupplier = supplier;
    }

    public Product (int Id, String name, int quantity, int price, String supplier, long imageResource) {
        mId = Id;
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mSupplier = supplier;
        mImageResource = imageResource;
    }

    public void setImageResource(long imageResource) {
        mImageResource = imageResource;
    }

    public int getId() {return mId;}

    public String getName() {return mName;}

    public int getQuantity() {return mQuantity;}

    public int getPrice() {return mPrice;}

    public String getSupplier() {return mSupplier;}

    public long getImageResource() {return mImageResource;}
}
