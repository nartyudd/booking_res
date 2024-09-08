package com.example.booking_res.view.adapter.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.Table;


import java.util.List;

public class TableChildAdapter extends RecyclerView.Adapter<TableChildAdapter.TableChildViewHolder>{

    private List<Table> _tables;
    private OnClickListener onClickListener;


    public TableChildAdapter(List<Table> tables){
        this._tables = tables;
    }

    public void SetOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TableChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_choose_table_item, parent, false);

        return new TableChildAdapter.TableChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableChildViewHolder holder, int position) {
        Table table = _tables.get(position);

        holder.tableName.setText(table.getName());

        if(table.isStatus()){
            holder.status.setText("Hết bàn");
            holder.status.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.bottomTabActive));
        } else {
            holder.status.setText("Còn bàn");
            holder.status.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(table.getUuid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return !_tables.isEmpty() ? _tables.size() : 0;
    }

    public static class TableChildViewHolder extends RecyclerView.ViewHolder{

        private TextView tableName, status;
        private CardView cardView;
        public TableChildViewHolder(View itemView){
            super(itemView);

            tableName = itemView.findViewById(R.id.tableName);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public interface OnClickListener{
        void onClick(String table_id);
    }
}

