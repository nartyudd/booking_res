package com.example.booking_res.view.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_res.R;
import com.example.booking_res.model.Category;

import java.util.List;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.ListCategoryViewHolder>{

    private List<Category> cates;

    public ListCategoryAdapter(List<Category> _cates){
        this.cates = _cates;
    }

    @NonNull
    @Override
    public ListCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_admin, parent, false);

        return new ListCategoryAdapter.ListCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryViewHolder holder, int position) {
        Category cate = cates.get(position);

        holder.txtCate.setText(cate.getName());

        Glide.with(holder.itemView)
                .load(cate.getUriImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return cates.size() != 0 ? cates.size() : 0;
    }

    public static class ListCategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView txtCate;
        private ImageView image;
        public ListCategoryViewHolder(View itemView){
            super(itemView);

            txtCate = itemView.findViewById(R.id.txtCate);
            image = itemView.findViewById(R.id.image);
        }
    }
}
