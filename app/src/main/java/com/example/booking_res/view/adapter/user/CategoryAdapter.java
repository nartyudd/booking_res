package com.example.booking_res.view.adapter.user;

import android.media.Image;
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
import com.example.booking_res.model.Category;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private List<Category> _cates;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public CategoryAdapter(List<Category> cates){
        this._cates = cates;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false);
        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cate = _cates.get(position);

        holder.hor_text.setText(cate.getName());

        Glide.with(holder.itemView)
                .load(cate.getUriImage())
                .into(holder.hor_image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(cate.getUuid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return !_cates.isEmpty() ? _cates.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView hor_image;
        private TextView hor_text;
        private CardView cardView;
        public CategoryViewHolder(View itemView){
            super(itemView);

            hor_image = itemView.findViewById(R.id.hor_image);
            hor_text = itemView.findViewById(R.id.hor_text);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public interface OnClickListener{
        void onClick(String cate_id);
    }
}
