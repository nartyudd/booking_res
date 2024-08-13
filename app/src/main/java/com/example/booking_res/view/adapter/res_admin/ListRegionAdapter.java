package com.example.booking_res.view.adapter.res_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.Region;

import java.util.List;
import java.util.Locale;

public class ListRegionAdapter extends RecyclerView.Adapter<ListRegionAdapter.ListRegionViewHolder> {

    private List<Region> _regions;

    private UpdateClickListener updateClickListener;
    private DeleteClickListener deleteClickListener;

    public void setDeleteClickListener(DeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    public void setUpdateClickListener(UpdateClickListener updateClickListener) {
        this.updateClickListener = updateClickListener;
    }

    public ListRegionAdapter(List<Region> regions) {
        this._regions = regions;
    }

    @NonNull
    @Override
    public ListRegionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region_admin, parent, false);

        return new ListRegionAdapter.ListRegionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListRegionViewHolder holder, int position) {
        Region _region = _regions.get(position);

        holder.txtRegion.setText(_region.getName().toUpperCase(Locale.ROOT));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClickListener.onDeleteClick(_region.getUuid());
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClickListener.onUpdateCLick(_region);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _regions.size();
    }

    public class ListRegionViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout cardRegion;
        private Button btnDelete, btnUpdate;
        private TextView txtRegion;

        public ListRegionViewHolder(View itemView) {
            super(itemView);
            cardRegion = itemView.findViewById(R.id.cardRegion);
            txtRegion = itemView.findViewById(R.id.txtRegion);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }

    public interface DeleteClickListener {
        void onDeleteClick(String uuid);
    }

    public interface UpdateClickListener {
        void onUpdateCLick(Region region);
    }

}
