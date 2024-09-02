package com.example.booking_res.view.adapter.res_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Table;

import java.util.List;

public class ListTableParentAdapter extends RecyclerView.Adapter<ListTableParentAdapter.ListTableParentViewHolder>{
    private List<Region> regions;
    private UpdateClickListener updateClickListener;
    private DeleteClickListener deleteClickListener;

    public void setDeleteClickListener(DeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    public void setUpdateClickListener(UpdateClickListener updateClickListener) {
        this.updateClickListener = updateClickListener;
    }
    public ListTableParentAdapter(List<Region> _regions){
        this.regions = _regions;
    }
    @NonNull
    @Override
    public ListTableParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_table, parent, false);

        return new ListTableParentAdapter.ListTableParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTableParentViewHolder holder, int position) {
        Region region = regions.get(position);

        holder.titleParent.setText(region.getName());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.childRecyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false);
        if (holder.childRecyclerView.getLayoutManager() == null) {
            holder.childRecyclerView.setLayoutManager(gridLayoutManager);
        }

        ListTableChildAdapter childAdapter = new ListTableChildAdapter(region.getTables());
        holder.childRecyclerView.setAdapter(childAdapter);

        childAdapter.setDeleteClickListener(new ListTableChildAdapter.DeleteClickListener() {
            @Override
            public void onDeleteClick(Table table) {
                deleteClickListener.onDeleteClick(region.getUuid(), table);
            }
        });

        childAdapter.setUpdateClickListener(new ListTableChildAdapter.UpdateClickListener() {
            @Override
            public void onUpdateCLick(Table table) {
                updateClickListener.onUpdateCLick(region.getUuid(), table);
            }
        });

        holder.titleParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.childRecyclerView.getVisibility() == View.VISIBLE) {
                    holder.childRecyclerView.setVisibility(View.GONE);
                } else {
                    holder.childRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public static class ListTableParentViewHolder extends RecyclerView.ViewHolder{
        private TextView titleParent;
        private RecyclerView childRecyclerView;

        public ListTableParentViewHolder(View itemView){
            super(itemView);

            titleParent = itemView.findViewById(R.id.titleParent);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }

    public interface DeleteClickListener {
        void onDeleteClick( String regionId, Table table);
    }

    public interface UpdateClickListener {
        void onUpdateCLick(String region_id, Table table);
    }
}
