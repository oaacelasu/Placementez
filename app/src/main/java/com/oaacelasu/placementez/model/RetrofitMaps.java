package com.oaacelasu.placementez.model;

/**
 * name : RetrofitMaps
 * author : root
 * date : 26/05/18
 * description :  Tomado de: https://www.codeproject.com/Articles/1121069/Google-Maps-Nearby-Places-API-using-Retrofit-Andro
 */

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by navneet on 17/7/16.
 */
public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDN7RJFmImYAca96elyZlE5s_fhX-MMuhk")
    Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}