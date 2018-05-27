package com.oaacelasu.placementez.share.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.adapter.PlaceAdapterRecyclerView;


import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private RecyclerView PlacesRecycler;
    private GridLayoutManager gridLayoutManagerPortrait;
    private GridLayoutManager gridLayoutManagerLandscape;
    private ArrayList<com.oaacelasu.placementez.model.Place> places;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView PlacesRecycler = (RecyclerView) view.findViewById(R.id.rvPlaceSearchRecycler);

        places = (ArrayList<com.oaacelasu.placementez.model.Place>) getArguments().getSerializable("PLACES_KEY");
        Log.d("Gato2", String.valueOf(places.size()));

        gridLayoutManagerPortrait = new GridLayoutManager(getContext(), 2);
        gridLayoutManagerPortrait.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManagerLandscape = new GridLayoutManager(getContext(), 3);
        gridLayoutManagerLandscape.setOrientation(LinearLayoutManager.VERTICAL);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            PlacesRecycler.setLayoutManager(gridLayoutManagerLandscape);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            PlacesRecycler.setLayoutManager(gridLayoutManagerPortrait);
        }

        PlaceAdapterRecyclerView PlaceAdapterRecyclerView =
                new PlaceAdapterRecyclerView(places, R.layout.cardview_place, getActivity());

        PlacesRecycler.setAdapter(PlaceAdapterRecyclerView);

        return view;
    }


}
