package com.example.booking_res.view.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_res.R;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.RestaurantRepo;
import com.example.booking_res.view.adapter.admin.ListRestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurantFragment extends Fragment {


    public ListRestaurantFragment() {

    }

    public static ListRestaurantFragment newInstance() {
        return new ListRestaurantFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RecyclerView recyclerView;
    private RestaurantRepo restaurantRepo;
    private ListRestaurantAdapter listRestaurantAdapter;
    private List<Restaurant> _restaurants;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_restaurant, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        restaurantRepo = new RestaurantRepo();
        _restaurants = new ArrayList<>();

        listRestaurantAdapter = new ListRestaurantAdapter(_restaurants);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(listRestaurantAdapter);

        loadData();
    }

    private void loadData(){
        restaurantRepo.GetAll(new BaseRepo.OnDataFetchedListener<List<Restaurant>>() {
            @Override
            public void onDataFetched(List<Restaurant> restaurants) {
                _restaurants.addAll(restaurants);

                listRestaurantAdapter.notifyDataSetChanged();
            }
        });
    }
}