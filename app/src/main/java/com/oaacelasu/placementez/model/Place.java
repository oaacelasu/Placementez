package com.oaacelasu.placementez.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * name : Place
 * author : root
 * date : 24/05/18
 * description :
 */
public class Place implements Serializable{
    private String picture = "";
    private String placeName = "";
    private String adress = "";
    private ArrayList<Product> products;

    public Place(String picture, String placeName, String adress) {
        this.picture = picture;
        this.placeName = placeName;
        this.adress = adress;
        this.products = makeProducts();
    }

    public Place(String placeName, String adress) {
        this.placeName = placeName;
        this.adress = adress;
        this.products = makeProducts();
    }

    private ArrayList<Product> makeProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("https://secure.footprint.net/cupages/pepsi/3_cans-min.png", "Pepsi", "30 pesos"));
        products.add(new Product("https://e0605b5e794e16b15e51-b25f5db3158ecf712705ad7a35f2aa8a.ssl.cf3.rackcdn.com/ensalada-de-tofu-y-calabacin-a-la-plancha-65-840-584-nw.jpg", "Ensalada", "100 pesos"));
        products.add(new Product("https://www.cocinavital.mx/wp-content/uploads/2017/08/pastel-de-chocolate.jpg", "Pastel", "50 pesos"));
        products.add(new Product("https://t2.uc.ltmcdn.com/images/4/6/9/img_como_hacer_helado_de_menta_28964_600.jpg", "Helado", "50 pesos"));
        products.add(new Product("http://www.coffeeiq.co/wp-content/uploads/2015/05/blog-dummi-1-e1430909798822.jpeg", "Cafe", "60 pesos"));
        products.add(new Product("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyICKk1g6wKvq68xzsKDxGAIUAPJDHOtjccGkHQr0fy_IVeZhkGg", "Carne", "150 pesos"));
        products.add(new Product("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSW1flk9yHNQVsEVYnvTcAVkRYCF8-ls9YpkGc8hAHEXsBfGsYA", "Pasta", "100 pesos"));
        return products;
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}
