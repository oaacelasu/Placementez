package com.oaacelasu.placementez.model;

import java.util.ArrayList;

/**
 * name : Place
 * author : root
 * date : 24/05/18
 * description :
 */
public class Place {
    private String picture;
    private String placeName;
    private String adress;
//    private ArrayList<Product> products;

    public Place(String picture, String placeName, String adress) {
        this.picture = picture;
        this.placeName = placeName;
        this.adress = adress;
//        this.products = products;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

//    public ArrayList<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(ArrayList<Product> products) {
//        this.products = products;
//    }
}
