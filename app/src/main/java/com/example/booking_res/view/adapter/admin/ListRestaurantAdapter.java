package com.example.booking_res.view.adapter.admin;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_res.R;
import com.example.booking_res.model.Restaurant;

import org.w3c.dom.Text;

import java.util.List;

public class ListRestaurantAdapter extends RecyclerView.Adapter<ListRestaurantAdapter.ListRestaurantViewHolder> {

    private List<Restaurant> restaurants;

    public ListRestaurantAdapter(List<Restaurant> _restaurants){
        this.restaurants = _restaurants;
    }
    @NonNull
    @Override
    public ListRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_admin, parent, false)
                ;
        return new ListRestaurantAdapter.ListRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRestaurantViewHolder holder, int position) {
        Restaurant res = restaurants.get(position);

        holder.name.setText(res.getName());
        holder.address.setText(res.getAddress());
        holder.rating.setText("Đánh giá : " + res.getRating());

        Glide.with(holder.itemView)
                .load(res.getUriImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return restaurants != null ? restaurants.size() : 0;
    }

    public static class ListRestaurantViewHolder extends RecyclerView.ViewHolder{

        private TextView name, address, rating;
        private Button btnUpdate, btnDelete;
        private ImageView image;
        public ListRestaurantViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            rating = itemView.findViewById(R.id.rating);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            image = itemView.findViewById(R.id.image);
        }
    }
}
