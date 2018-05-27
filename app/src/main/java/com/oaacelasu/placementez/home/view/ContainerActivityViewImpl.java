package com.oaacelasu.placementez.home.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;

import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.model.Example;
import com.oaacelasu.placementez.model.RetrofitMaps;
import com.oaacelasu.placementez.share.view.PlaceDetailViewImpl;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class ContainerActivityViewImpl extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener, ContainerActivityView{

    final int PROXIMITY_RADIUS = 2000;
    final private int PLACE_PICKER_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    double latitude = 0;
    double longitude = 0;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient geoDataClient;
    android.location.Location mLastLocation;
    LocationRequest mLocationRequest;
    ArrayList<com.oaacelasu.placementez.model.Place> places = new ArrayList<>();
    BottomBar bottomBar;
    private int currentPhotoIndex = 0;
    private boolean info_flag = false;
    private String photoPathTemp = "";
    private ArrayList<PlacePhotoMetadata> photosDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }



        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, homeFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_search:
                        goToSearchFragment();
                        break;
                    case R.id.tab_map:
                        displayPlacePicker();
                        break;
                }
            }
        });

        geoDataClient = Places.getGeoDataClient(this, null);
    }

    @Override
    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }
    @Override
    public void displayPlacePicker() {
        mGoogleApiClient.connect();
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected())
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            displayPlace(PlacePicker.getPlace(data, this));
        }
    }
    @Override
    public void displayPlace(Place place) {
        if (place == null)
            return;
        final String placeId = place.getId();
        final String name = (String) place.getName();
        final String adress = (String) place.getAddress();

        final Task<PlacePhotoMetadataResponse> photoResponse =
                geoDataClient.getPlacePhotos(placeId);

        photoResponse.addOnCompleteListener
                (new OnCompleteListener<PlacePhotoMetadataResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                        currentPhotoIndex = 0;
                        photosDataList = new ArrayList<>();
                        PlacePhotoMetadataResponse photos = task.getResult();
                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                        for(PlacePhotoMetadata photoMetadata : photoMetadataBuffer){
                            photosDataList.add(photoMetadataBuffer.get(0).freeze());
                        }

                        photoMetadataBuffer.release();

                        getPhoto(name, adress);
                    }
                });
    }
    @Override
    public void getPhoto(final String name, final String adress) {
        if(photosDataList.isEmpty() || currentPhotoIndex > photosDataList.size() - 1){
            com.oaacelasu.placementez.model.Place selectedPlace = new com.oaacelasu.placementez.model.Place(name, adress);
            goToPlaceDetailActivity(selectedPlace);
        }else {
            PlacePhotoMetadata photoMetadata = photosDataList.get(currentPhotoIndex);

            Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
            photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                    PlacePhotoResponse photo = task.getResult();
                    Bitmap photoBitmap = photo.getBitmap();
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if (photoFile != null){
                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(photoFile.getAbsolutePath());
                            photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                            com.oaacelasu.placementez.model.Place selectedPlace = new com.oaacelasu.placementez.model.Place(photoPathTemp, name, adress);
                            goToPlaceDetailActivity(selectedPlace);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
        }

    }
    @Override
    public void displayPlace(final String placeId, final String name, final String adress) {
        final Task<PlacePhotoMetadataResponse> photoResponse =
                geoDataClient.getPlacePhotos(placeId);

        photoResponse.addOnCompleteListener
                (new OnCompleteListener<PlacePhotoMetadataResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                        currentPhotoIndex = 0;
                        photosDataList = new ArrayList<>();
                        PlacePhotoMetadataResponse photos = task.getResult();
                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                        if (photoMetadataBuffer.getCount() > 0){
                            for(PlacePhotoMetadata photoMetadata : photoMetadataBuffer){
                                photosDataList.add(photoMetadataBuffer.get(0).freeze());
                            }

                            photoMetadataBuffer.release();

                            getPhotoForBuild(name, adress);
                        }

                    }
                });
    }
    @Override
    public void getPhotoForBuild(final String name, final String adress) {
        if(photosDataList.isEmpty() || currentPhotoIndex > photosDataList.size() - 1){
            com.oaacelasu.placementez.model.Place selectedPlace = new com.oaacelasu.placementez.model.Place(name, adress);
            if (places.size()<5){
                places.add(selectedPlace);
            }
        }else {
            PlacePhotoMetadata photoMetadata = photosDataList.get(currentPhotoIndex);

            Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
            photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                    PlacePhotoResponse photo = task.getResult();
                    Bitmap photoBitmap = photo.getBitmap();

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if (photoFile != null){
                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(photoFile.getAbsolutePath());
                            photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                            com.oaacelasu.placementez.model.Place selectedPlace = new com.oaacelasu.placementez.model.Place(photoPathTemp, name, adress);
                            places.add(selectedPlace);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
        }

    }
    @Override
    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyymmdd_HH-mm--ss", Locale.getDefault()).format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imageFileName, ".png", storageDir);

        photoPathTemp = "file:" + photo.getAbsolutePath();
        return photo;

    }
    @Override
    public void goToPlaceDetailActivity(com.oaacelasu.placementez.model.Place place) {
        Intent intent = new Intent(this, PlaceDetailViewImpl.class);

        intent.putExtra("EXTRA_PLACE_ID", place);
        startActivity(intent);
    }

    @Override
    public void build_retrofit_and_get_response(String type) {

        final String url = "https://maps.googleapis.com/maps/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<Example> call = service.getNearbyPlaces(type, latitude + "," + longitude, PROXIMITY_RADIUS);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                try {
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        String placeName = response.body().getResults().get(i).getName();
                        String vicinity = response.body().getResults().get(i).getVicinity();
                        String placeId = response.body().getResults().get(i).getPlaceId();

                        displayPlace(placeId,placeName, vicinity);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( mGoogleApiClient != null && !mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.connect();}
        bottomBar.setDefaultTab(R.id.tab_search);
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (!info_flag){
            info_flag = true;
            mLastLocation = location;
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            build_retrofit_and_get_response("restaurant");
            bottomBar.setDefaultTab(R.id.tab_search);
            bottomBar.setDefaultTab(R.id.tab_map);
        }

    }
    @Override
    public void goToSearchFragment() {
        if (places.size()>0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("PLACES_KEY", places);
            searchFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, searchFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }

    }
}
