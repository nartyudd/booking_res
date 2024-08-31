package com.example.booking_res.view.fragment.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_res.R;
import com.example.booking_res.model.ChooseTableModel;
import com.example.booking_res.view.adapter.user.ChooseTableAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChooseTableFragment extends Fragment {

    RecyclerView recyclerView;
    List<ChooseTableModel> chooseTableModels;
    ChooseTableAdapter chooseTableAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_table, container, false);

        if (getArguments() != null) {
            String itemName = getArguments().getString("item_name");
            // Use itemName as needed
        }

        recyclerView = root.findViewById(R.id.choose_table_rec);

        GridLayoutManager grid = new GridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(grid);
//
        chooseTableModels = new ArrayList<>();
        chooseTableModels.add(new ChooseTableModel(R.drawable.couple,"Bàn đôi","Còn bàn","Phù hợp cho các cặp đôi" ));
        chooseTableModels.add(new ChooseTableModel(R.drawable.family,"Bàn gia đình","Còn bàn","Phù hợp cho gia đình 4 người" ));
        chooseTableModels.add(new ChooseTableModel(R.drawable.singeltable,"Bàn nhỏ","Còn bàn","Phù hợp cho nhóm từ 5 - 6 người" ));
        chooseTableModels.add(new ChooseTableModel(R.drawable.table,"Bàn nhóm lớn","Còn bàn","Phù hợp cho nhóm từ 10 - 20 người"));

        chooseTableAdapter = new ChooseTableAdapter(chooseTableModels);
        recyclerView.setAdapter(chooseTableAdapter);
        chooseTableAdapter.notifyDataSetChanged();

        return root;
    }
}