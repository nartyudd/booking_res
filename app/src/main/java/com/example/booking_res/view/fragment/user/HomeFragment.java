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
import java.util.List;

public class HomeFragment extends Fragment implements UpdateVerticalRec, HomeVerAdapter.OnItemClickListener {

    private RecyclerView homeHorizontalRec, homeVerticalRec;
    private ArrayList<HomeHorModel> homeHorModelList;
    private HomeHorAdapter homeHorAdapter;
    private ArrayList<HomeVerModel> homeVerModelList;
    private HomeVerAdapter homeVerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);

        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel(R.drawable.lau, "Lẩu"));
        homeHorModelList.add(new HomeHorModel(R.drawable.bbq, "Nướng"));
        homeHorModelList.add(new HomeHorModel(R.drawable.buffet, "Buffet"));
        homeHorModelList.add(new HomeHorModel(R.drawable.vegetable, "Chay"));

        homeHorAdapter = new HomeHorAdapter(this, requireActivity(), homeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);

        homeVerModelList = new ArrayList<>();
        homeVerModelList.add(new HomeVerModel(R.drawable.bbq, "BBQ", "4.5", "200K - 300K"));
        homeVerModelList.add(new HomeVerModel(R.drawable.bbq, "BBQ", "4.5", "200K - 300K"));
        homeVerModelList.add(new HomeVerModel(R.drawable.bbq, "BBQ", "4.5", "200K - 300K"));

        homeVerAdapter = new HomeVerAdapter(requireContext(), homeVerModelList, this);
        homeVerticalRec.setAdapter(homeVerAdapter);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(false);

        return root;
    }

    @Override
    public void callBack(int position, ArrayList<HomeVerModel> list) {
        homeVerAdapter.updateData(list);
    }

    @Override
    public void onItemClick(HomeVerModel item) {
        // Navigate to ChooseTableFragment
        ChooseTableFragment chooseTableFragment = new ChooseTableFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, chooseTableFragment)
                .addToBackStack(null)
                .commit();
    }
}
