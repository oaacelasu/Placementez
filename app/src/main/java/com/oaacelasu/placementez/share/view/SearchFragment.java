package com.oaacelasu.placementez.share.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.adapter.PlaceAdapterRecyclerView;
import com.oaacelasu.placementez.model.Place;
import com.oaacelasu.placementez.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        RecyclerView PlacesRecycler = (RecyclerView) view.findViewById(R.id.rvPlaceSearchRecycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        PlacesRecycler.setLayoutManager(gridLayoutManager);

        PlaceAdapterRecyclerView PlaceAdapterRecyclerView =
                new PlaceAdapterRecyclerView(buildPlaces(), R.layout.cardview_place, getActivity());

        PlacesRecycler.setAdapter(PlaceAdapterRecyclerView);
        return view;
    }

    public ArrayList<Place> buildPlaces(){
        ArrayList<Place> Places = new ArrayList<>();
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-1.jpg", "Oscar Acelas", "4"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-2.jpg", "Miguel Dominguez", "15"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-3.jpg", "Camila Díaz", "9"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-4.jpg", "Juan Acevedo", "8"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-5.jpg", "Daniel Upegui", "6"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-6.jpg", "Pedro Sabogal", "2"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-7.jpg", "July Nieto", "dfhgfghgf"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-8.jpg", "Alfonso Cardenas", "5"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-9.jpg", "Cris Cetina", "3"));
        Places.add(new Place("https://www.10puntos.net//wp-content/uploads/2015/04/perros-populares-google-10.jpg", "Karen Macias", "1"));
        return Places;
    }

}
