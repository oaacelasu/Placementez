package com.oaacelasu.placementez.home.view;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.adapter.PlaceAdapterRecyclerView;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    GridLayoutManager gridLayoutManagerPortrait;
    GridLayoutManager gridLayoutManagerLandscape;
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
