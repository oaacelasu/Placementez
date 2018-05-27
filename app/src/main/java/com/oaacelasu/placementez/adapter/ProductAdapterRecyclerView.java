package com.oaacelasu.placementez.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.oaacelasu.placementez.R;
import com.oaacelasu.placementez.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * name : ProductAdapterRecyclerView
 * author : root
 * date : 24/05/18
 * description :
 */
public class ProductAdapterRecyclerView extends RecyclerView.Adapter<ProductAdapterRecyclerView.ProductViewHolder>{

    private ArrayList<Product> products;
    private int resource;
    private Activity activity;

    public ProductAdapterRecyclerView(ArrayList<Product> products, int resource, Activity activity) {
        this.products = products;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.ProductNameCard.setText(product.getProductName());
        holder.ProductPriceCard.setText(product.getPrice());
        Picasso.get().load(product.getPicture()).into(holder.ProductImageCard);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView ProductImageCard;
        private TextView ProductNameCard;
        private TextView ProductPriceCard;


        public ProductViewHolder(View itemView) {
            super(itemView);

            ProductImageCard = itemView.findViewById(R.id.ivProductImageCard);
            ProductNameCard = itemView.findViewById(R.id.edtProductNameCard);
            ProductPriceCard = itemView.findViewById(R.id.edtProductPriceCard);



        }
    }
}
