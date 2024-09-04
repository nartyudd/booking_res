package com.example.booking_res.view.adapter.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_res.R;
import com.example.booking_res.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private List<Restaurant> _res;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public RestaurantAdapter(List<Restaurant> res){
        this._res = res;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false);
        return new RestaurantAdapter.RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant re = _res.get(position);

        holder.name.setText(re.getName());
        holder.rating.setText(re.getRating() + "");
        holder.address.setText(re.getAddress());

        Glide.with(holder.itemView)
                .load(re.getUriImage())
                .into(holder.ver_img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(re.getUuid());
            }
        });
    }

    public void updateData(List<Restaurant> newRes) {
        _res = newRes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return !_res.isEmpty() ? _res.size() : 0;
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{

        private ImageView ver_img;
        private TextView name, rating, address;
        private CardView cardView;

        public RestaurantViewHolder(View itemView){
            super(itemView);

            ver_img = itemView.findViewById(R.id.ver_img);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            address = itemView.findViewById(R.id.address);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public interface OnClickListener{
        void onClick(String res_id);
    }
}
