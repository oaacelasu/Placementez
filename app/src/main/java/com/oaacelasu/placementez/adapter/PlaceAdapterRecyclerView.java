package com.oaacelasu.placementez.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.home.view.ContainerActivity;
import com.oaacelasu.placementez.model.Place;
import com.oaacelasu.placementez.share.view.PlaceDetailActivity;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * name : PlaceAdapterRecyclerView
 * author : root
 * date : 24/05/18
 * description :
 */
public class PlaceAdapterRecyclerView extends RecyclerView.Adapter<PlaceAdapterRecyclerView.PlaceViewHolder>{

    private ArrayList<Place> places;
    private int resource;
    private Activity activity;


    public PlaceAdapterRecyclerView(ArrayList<Place> places, int resource, Activity activity) {
        this.places = places;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.placeNameCard.setText(place.getPlaceName());
        if (place.getPicture() != null){
            Picasso.get().load(place.getPicture()).into(holder.placeImageCard);
        }


        holder.placeImageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(place.getPicture() != null){
                    Intent intent = new Intent(activity, PlaceDetailActivity.class);
                    intent.putExtra("EXTRA_PLACE_ID", place);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        Explode explode = new Explode();
                        activity.getWindow().setExitTransition(explode);
                        activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, activity.getString(R.string.transitionname_place)).toBundle());
                    }else{
                        activity.startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(activity, PlaceDetailActivity.class);
                    intent.putExtra("EXTRA_PLACE_ID", place);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        Explode explode = new Explode();
                        activity.getWindow().setExitTransition(explode);
                        activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, activity.getString(R.string.transitionname_place)).toBundle());
                    }else{
                        activity.startActivity(intent);
                    }
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return places.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{

        private ImageView placeImageCard;
        private TextView placeNameCard;


        public PlaceViewHolder(View itemView) {
            super(itemView);

            placeImageCard = (ImageView) itemView.findViewById(R.id.ivPlaceImageCard);
            placeNameCard = (TextView) itemView.findViewById(R.id.edtPlaceNameCard);

        }
    }
}
