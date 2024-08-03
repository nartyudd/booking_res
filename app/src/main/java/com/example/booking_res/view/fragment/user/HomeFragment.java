package com.example.booking_res.view.fragment.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_res.R;
import com.example.booking_res.model.HomeHorModel;
import com.example.booking_res.model.HomeVerModel;
import com.example.booking_res.view.adapter.user.HomeHorAdapter;
import com.example.booking_res.view.adapter.user.HomeVerAdapter;
import com.example.booking_res.view.adapter.user.UpdateVerticalRec;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateVerticalRec {
    RecyclerView homeHorizontalRec, homeVerticalRec;
    /// Horizontalllllllll
    ArrayList<HomeHorModel> homeHorModelList;
    HomeHorAdapter homeHorAdapter;

    /// Verticallllllll

    ArrayList<HomeVerModel> homeVerModelList;
    HomeVerAdapter homeVerAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstancesState){
        View root = inflater.inflate(R.layout.fragment_home, container,false);

        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        ///verticalRec
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);

        ////////// Horizontal RecyclerView
        homeHorModelList = new ArrayList<>();

        homeHorModelList.add(new HomeHorModel(R.drawable.lau, "Lẩu"));
        homeHorModelList.add(new HomeHorModel(R.drawable.bbq, "Nướng"));
        homeHorModelList.add(new HomeHorModel(R.drawable.buffet, "Buffet"));
        homeHorModelList.add(new HomeHorModel(R.drawable.vegetable, "Chay"));


        homeHorAdapter = new HomeHorAdapter( this,getActivity(),homeHorModelList);

        homeHorizontalRec.setAdapter(homeHorAdapter);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);



        ////////// Vertical RecyclerView
        homeVerModelList = new ArrayList<>();

        homeVerModelList.add(new HomeVerModel(R.drawable.bbq, "BBQ","4.5","200K - 300K"));
        homeVerModelList.add(new HomeVerModel(R.drawable.bbq, "BBQ","4.5","200K - 300K"));
        homeVerModelList.add(new HomeVerModel(R.drawable.bbq, "BBQ","4.5","200K - 300K"));

        homeVerAdapter = new HomeVerAdapter(getActivity(),homeVerModelList);

        homeVerticalRec.setAdapter(homeVerAdapter);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        homeVerticalRec.setHasFixedSize(true);
//        homeVerticalRec.setNestedScrollingEnabled(false);
        return root;
    }

    @Override
    public void callBack(int position, ArrayList<HomeVerModel> list) {
        homeVerAdapter = new HomeVerAdapter(getContext(),list);
        homeVerAdapter.notifyDataSetChanged();
        homeVerticalRec.setAdapter(homeVerAdapter);
    }
}