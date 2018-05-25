package com.oaacelasu.placementez.model;

import java.util.ArrayList;

/**
 * name : Product
 * author : root
 * date : 24/05/18
 * description :
 */
public class Product {
    private String picture;
    private String productName;
    private String price;

    public Product(String picture, String productName, String price) {
        this.picture = picture;
        this.productName = productName;
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
