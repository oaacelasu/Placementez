package com.oaacelasu.placementez.share.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.adapter.PlaceAdapterRecyclerView;
import com.oaacelasu.placementez.adapter.ProductAdapterRecyclerView;
import com.oaacelasu.placementez.model.Place;
import com.oaacelasu.placementez.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.graphics.Color.TRANSPARENT;

public class PlaceDetailActivity extends AppCompatActivity {

    ArrayList<Product> products;
    private String name;
    private String adress;
    private byte[] picture;
    private Place place;
    private String strPicture;
    private ImageView ivPlaceImageCard;
    private TextView tvPlaceNameDetail;
    private TextView tvPlaceAdressDetail;
    private FloatingActionButton btnShare;
    RecyclerView rvProductDetailRecycler;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_place_detail);

        place = (Place) getIntent().getSerializableExtra("EXTRA_PLACE_ID");
        showToolbar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(TRANSPARENT);
            getWindow().setEnterTransition(new Fade());
        }


        ivPlaceImageCard = (ImageView) findViewById(R.id.ivPlaceImageCard);
        tvPlaceNameDetail = (TextView) findViewById(R.id.tvPlaceNameDetail);
        tvPlaceAdressDetail = (TextView) findViewById(R.id.tvPlaceAdressDetail);
        rvProductDetailRecycler = (RecyclerView) findViewById(R.id.rvProductDetailRecycler);
        btnShare = (FloatingActionButton) findViewById(R.id.btnShare);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://maps.google.com/"))
                        .setQuote("<"+place.getPlaceName()+"><"+"geo:0,0?q=" + place.getPlaceName() + ", " + place.getAdress()+">")
                        .build();

                if(ShareDialog.canShow((ShareLinkContent.class))){
                    shareDialog.show(linkContent);
                    Log.d("Facehola", "bien");
                }
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProductDetailRecycler.setLayoutManager(linearLayoutManager);


        ProductAdapterRecyclerView pictureAdapterRecyclerView =
                new ProductAdapterRecyclerView(place.getProducts(), R.layout.cardview_product, this);

        rvProductDetailRecycler.setAdapter(pictureAdapterRecyclerView);
        setInformationPlace();

    }

    private void setInformationPlace() {
        if (place.getPicture() != ""){
            Picasso.get().load(place.getPicture()).into(ivPlaceImageCard);
        }
        tvPlaceNameDetail.setText(place.getPlaceName());
        tvPlaceAdressDetail.setText(place.getAdress());
    }


    public void showToolbar(String tittle, boolean upButton){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }

    public void goToMaps(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + place.getPlaceName() + ", " + place.getAdress());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
