package com.example.booking_res.view.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_res.R;
import com.example.booking_res.viewmodels.UserViewModel;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder> {

    private List<UserViewModel> userViewModels;

    public ListUserAdapter(List<UserViewModel> _userViewModels){
        this.userViewModels = _userViewModels;
    }

    @NonNull
    @Override
    public ListUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_admin, parent, false);

        return new ListUserAdapter.ListUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUserViewHolder holder, int position) {
        UserViewModel user = userViewModels.get(position);

        holder.userName.setText(user.getUserName());
        holder.email.setText(user.getEmail());
        if(user.getRole().equals("user")){
            holder.role.setText("Nguời dùng");
        } else {
            holder.role.setText("Nhà hàng");
        }
        if(user.isExists()){
            holder.exist.setText("Hoạt động");
        } else {
            holder.exist.setText("Ngừng hoạt động");
        }
    }

    @Override
    public int getItemCount() {
        return userViewModels != null ? userViewModels.size() : 0;
    }

    public static class ListUserViewHolder extends RecyclerView.ViewHolder{

        private TextView userName, email, role, exist;
        public ListUserViewHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.email);
            role = itemView.findViewById(R.id.role);
            exist = itemView.findViewById(R.id.exist);
        }
    }
}
