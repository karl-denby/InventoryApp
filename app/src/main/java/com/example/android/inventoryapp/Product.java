package com.example.android.inventoryapp;

public class Product {
    private int mId;
    private String mName;
    private int mQuantity;
    private int mPrice;
    private String mSupplier;
    private String mImageLocation;

    public Product (int Id, String name, int quantity, int price, String supplier) {
        mId = Id;
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mSupplier = supplier;
    }

    public Product (int Id, String name, int quantity, int price, String supplier, String imageLocation) {
        mId = Id;
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mSupplier = supplier;
        mImageLocation = imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        mImageLocation = imageLocation;
    }

    public int getId() {return mId;}

    public String getName() {return mName;}

    public int getQuantity() {return mQuantity;}

    public int getPrice() {return mPrice;}

    public String getSupplier() {return mSupplier;}

    public String getImageResource() {return mImageLocation;}
}
