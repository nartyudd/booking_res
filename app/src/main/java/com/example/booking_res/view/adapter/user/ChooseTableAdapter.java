package com.example.booking_res.view.adapter.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.ChooseTableModel;

import java.util.List;

public class ChooseTableAdapter extends RecyclerView.Adapter<ChooseTableAdapter.ChooseTableViewHolder> {

    private List<ChooseTableModel> chooseTableList;

    public ChooseTableAdapter(List<ChooseTableModel> chooseTableList) {
        this.chooseTableList = chooseTableList;
    }

    @NonNull
    @Override
    public ChooseTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_choose_table_item, parent, false);
        return new ChooseTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseTableViewHolder holder, int position) {
        ChooseTableModel chooseTable = chooseTableList.get(position);
        holder.tableImage.setImageResource(chooseTable.getImg());
        holder.tableName.setText(chooseTable.getNameTable());
        holder.tableStatus.setText(chooseTable.getStatus());
        holder.tableDescription.setText(chooseTable.getDescription());
    }

    @Override
    public int getItemCount() {
        return chooseTableList.size();
    }

    static class ChooseTableViewHolder extends RecyclerView.ViewHolder {
        ImageView tableImage;
        TextView tableName;
        TextView tableStatus;
        TextView tableDescription;

        public ChooseTableViewHolder(@NonNull View itemView) {
            super(itemView);
            tableImage = itemView.findViewById(R.id.imageViewTable);
            tableName = itemView.findViewById(R.id.tableName);
            tableStatus = itemView.findViewById(R.id.status);
            tableDescription = itemView.findViewById(R.id.description);
        }
    }
}
