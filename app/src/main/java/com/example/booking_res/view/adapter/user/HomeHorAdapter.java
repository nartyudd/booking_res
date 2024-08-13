package com.example.booking_res.view.adapter.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.model.HomeHorModel;
import com.example.booking_res.model.HomeVerModel;
import com.example.booking_res.view.activity.UserActivity;
import com.example.booking_res.view.fragment.user.ChooseTableFragment;

import java.util.ArrayList;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {
    UpdateVerticalRec updateVerticalRec;
    Activity activity;
    ArrayList<HomeHorModel> list;

    boolean check = true;
    boolean select = true;
    int row_index = -1;

    public HomeHorAdapter(UpdateVerticalRec updateVerticalRec, Activity activity, ArrayList<HomeHorModel> list) {
        this.updateVerticalRec = updateVerticalRec;
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());

        if (check) {
            ArrayList<HomeVerModel> homeVerModels = new ArrayList<>();
            homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Thái",  "5.0", "100K - 300K / người"));
            homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Cù lao",  "3.0", "100K - 300K / người"));
            homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Cá",  "4.0", "100K - 300K / người"));
            homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Bò",  "4.0", "100K - 300K / người"));

            updateVerticalRec.callBack(position, homeVerModels);
            check = false;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
                if (position == 0) {

                    ArrayList<HomeVerModel> homeVerModels = new ArrayList<>();

                    homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Thái",  "5.0", "100K - 300K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Cù lao",  "3.0", "100K - 300K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Cá",  "4.0", "100K - 300K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.lau, "Lẫu Bò",  "4.0", "100K - 300K / người"));

                    updateVerticalRec.callBack(position, homeVerModels);
                } else if (position == 1) {
                    ArrayList<HomeVerModel> homeVerModels = new ArrayList<>();

                    homeVerModels.add(new HomeVerModel(R.drawable.bbq, "Combo BBQ_1", "4.0", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.bbq, "Combo BBQ_2",  "4.1", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.bbq, "Combo BBQ_3",  "4.2", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.bbq, "Combo BBQ_4",  "4.3", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.bbq, "Combo BBQ_5",  "4.3", "200K - 100K / người"));

                    updateVerticalRec.callBack(position, homeVerModels);
                } else if (position == 2) {
                    ArrayList<HomeVerModel> homeVerModels = new ArrayList<>();
                    homeVerModels.add(new HomeVerModel(R.drawable.buffet, "Buffet Gia Đình",  "4.0", "5000K / nhóm"));
                    homeVerModels.add(new HomeVerModel(R.drawable.buffet, "Buffet Sinh viên",  "4.0", "200K - 500K / người"));

                    homeVerModels.add(new HomeVerModel(R.drawable.buffet, "Buffet hải sản",  "4.0", "500K - 700K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.buffet, "Buffet vip", "4.0", "1000K / người"));

                    updateVerticalRec.callBack(position, homeVerModels);
                } else if (position == 3) {
                    ArrayList<HomeVerModel> homeVerModels = new ArrayList<>();
                    homeVerModels.add(new HomeVerModel(R.drawable.vegetable, "Chay_2",  "4.0", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.vegetable, "Chay_2",  "4.0", "200K - 100K / người"));

                    homeVerModels.add(new HomeVerModel(R.drawable.vegetable, "Chay_1",  "4.0", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.vegetable, "Chay_2",  "4.0", "200K - 100K / người"));
                    homeVerModels.add(new HomeVerModel(R.drawable.vegetable, "Chay_3",  "4.0", "200K - 100K / người"));

                    updateVerticalRec.callBack(position, homeVerModels);
                }
            }
        });
        if (select) {
            if (position == 0) {
                holder.cardView.setBackgroundResource(R.drawable.change_bg);
                select = false;
            }
        } else {
            if (row_index == position) {
                holder.cardView.setBackgroundResource(R.drawable.change_bg);
            } else {
                holder.cardView.setBackgroundResource(R.drawable.default_bg);
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.hor_image);
            name = itemView.findViewById(R.id.hor_text);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
