package com.example.booking_res.view.adapter.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.ChooseTableModel;
import com.example.booking_res.model.Region;

import java.util.List;

public class TableParentAdapter extends RecyclerView.Adapter<TableParentAdapter.TableParentViewHolder> {

    private List<Region> _regions;
    private OnClickListener onClickListener;

    public void SetOnClicckListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public TableParentAdapter(List<Region> regions) {
        this._regions = regions;
    }

    @NonNull
    @Override
    public TableParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_table, parent, false);
        return new TableParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableParentViewHolder holder, int position) {
        Region region = _regions.get(position);

        holder.titleParent.setText(region.getName());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.childRecyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false);

        if (holder.childRecyclerView.getLayoutManager() == null) {
            holder.childRecyclerView.setLayoutManager(gridLayoutManager);
        }

        TableChildAdapter tableChildAdapter = new TableChildAdapter(region.getTables());
        holder.childRecyclerView.setAdapter(tableChildAdapter);

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

        tableChildAdapter.SetOnClickListener(new TableChildAdapter.OnClickListener() {
            @Override
            public void onClick(String table_id) {
                onClickListener.onClick(table_id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return _regions.size() != 0 ? _regions.size() : 0;
    }

    static class TableParentViewHolder extends RecyclerView.ViewHolder {
        private TextView titleParent;
        private RecyclerView childRecyclerView;

        public TableParentViewHolder(@NonNull View itemView) {
            super(itemView);
            titleParent = itemView.findViewById(R.id.titleParent);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }

    public interface OnClickListener{
        void onClick(String table_id);
    }
}
