package com.example.booking_res.view.adapter.res_admin;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Table;

import java.util.List;

public class ListTableChildAdapter extends RecyclerView.Adapter<ListTableChildAdapter.ListTableChildViewHolder>{
    private List<Table> tables;

    private UpdateClickListener updateClickListener;
    private DeleteClickListener deleteClickListener;

    public void setDeleteClickListener(DeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    public void setUpdateClickListener(UpdateClickListener updateClickListener) {
        this.updateClickListener = updateClickListener;
    }
    public ListTableChildAdapter(List<Table> _table){
        this.tables = _table;
    }
    @NonNull
    @Override
    public ListTableChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_table, parent, false);

        return new ListTableChildAdapter.ListTableChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTableChildViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Table table = tables.get(position);

        holder.titleChild.setText(table.getName());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tables.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, tables.size());

                deleteClickListener.onDeleteClick(table);
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClickListener.onUpdateCLick(table);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tables != null ? tables.size() : 0;
    }

    public static class ListTableChildViewHolder extends RecyclerView.ViewHolder{

        private TextView titleChild;
        private TextView btnUpdate, btnDelete;
        public ListTableChildViewHolder(View itemView){
            super(itemView);

            titleChild = itemView.findViewById(R.id.titleChild);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete= itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface DeleteClickListener {
        void onDeleteClick(Table table);
    }

    public interface UpdateClickListener {
        void onUpdateCLick(Table table);
    }
}
