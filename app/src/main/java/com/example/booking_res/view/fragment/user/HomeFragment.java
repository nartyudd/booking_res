package com.example.booking_res.view.fragment.user;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.model.Category;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.user.CategoryRepo;
import com.example.booking_res.repo.user.RestaurantRepo;
import com.example.booking_res.view.adapter.user.CategoryAdapter;
import com.example.booking_res.view.adapter.user.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment{

    private RecyclerView home_hor_rec, home_ver_rec;

    private List<Category> _cate;
    private CategoryRepo categoryRepo;
    private CategoryAdapter categoryAdapter;

    private List<Restaurant> _res;
    private RestaurantRepo restaurantRepo;
    private RestaurantAdapter restaurantAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        home_hor_rec = view.findViewById(R.id.home_hor_rec);
        home_ver_rec = view.findViewById(R.id.home_ver_rec);

        categoryRepo = new CategoryRepo();
        _cate = new ArrayList<>();

        restaurantRepo = new RestaurantRepo();
        _res = new ArrayList<>();

        setHor(view);
        setVer(view);
    }

    private void setHor(View view){
        GridLayoutManager gridLayoutManagerHor = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        home_hor_rec.setLayoutManager(gridLayoutManagerHor);
        categoryAdapter = new CategoryAdapter(_cate);

        home_hor_rec.setAdapter(categoryAdapter);

        loadDataHor();

        categoryAdapter.setOnClickListener(new CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(String cate_id) {
                List<Restaurant> _newRes;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    _newRes = _res.stream()
                            .filter(re -> re.getCate_id().equals(cate_id) && re.isActive())
                            .collect(Collectors.toList());
                } else {
                    _newRes = new ArrayList<>();
                    for (Restaurant re : _res) {
                        if (re.getCate_id().equals(cate_id) && re.isActive()) {
                            _newRes.add(re);
                        }
                    }
                }

                restaurantAdapter.updateData(_newRes);
            }
        });
    }

    private void loadDataHor(){
        categoryRepo.GetAll(new BaseRepo.OnDataFetchedListener<List<Category>>() {
            @Override
            public void onDataFetched(List<Category> cates) {
                _cate.addAll(cates);

                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setVer(View view){
        GridLayoutManager gridLayoutManagerVer = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);
        home_ver_rec.setLayoutManager(gridLayoutManagerVer);
        restaurantAdapter = new RestaurantAdapter(_res);

        home_ver_rec.setAdapter(restaurantAdapter);

        loadDataVer();

        restaurantAdapter.setOnClickListener(new RestaurantAdapter.OnClickListener() {
            @Override
            public void onClick(String res_id) {
                FragmentManagerHelper.getInstance().replaceFragment(TableFragment.newInstance(res_id), true);
            }
        });
    }

    private void loadDataVer(){
        restaurantRepo.GetAll(new BaseRepo.OnDataFetchedListener<List<Restaurant>>() {
            @Override
            public void onDataFetched(List<Restaurant> res) {
                _res.addAll(res);

                restaurantAdapter.notifyDataSetChanged();
            }
        });
    }


}
