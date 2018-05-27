package com.oaacelasu.placementez.model;

/**
 * name : Geometry
 * author : root
 * date : 26/05/18
 * description : Tomado de: https://www.codeproject.com/Articles/1121069/Google-Maps-Nearby-Places-API-using-Retrofit-Andro
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;

    /**
     *
     * @return
     * The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

}

