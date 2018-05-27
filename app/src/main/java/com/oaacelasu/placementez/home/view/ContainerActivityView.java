package com.oaacelasu.placementez.home.view;

import android.content.Intent;

import com.google.android.gms.location.places.Place;

import java.io.File;
import java.io.IOException;

/**
 * name : ContainerActivityView
 * author : root
 * date : 27/05/18
 * description :
 */
public interface ContainerActivityView {
    void buildGoogleApiClient();
    boolean isGooglePlayServicesAvailable();
    void displayPlacePicker();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void displayPlace(Place place);
    void getPhoto(final String name, final String adress);
    void displayPlace(final String placeId, final String name, final String adress);
    void getPhotoForBuild(final String name, final String adress);
    File createImageFile() throws IOException;
    void goToPlaceDetailActivity(com.oaacelasu.placementez.model.Place place);
    void build_retrofit_and_get_response(String type);
    void goToSearchFragment();

}
