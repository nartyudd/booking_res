package com.example.booking_res.view.fragment.res_admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.booking_res.R;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.RestaurantRepo;

import java.util.Locale;

public class ProfileRestaurantFragment extends Fragment {


    private static final String ARG_PARAM1 = "user_id";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String user_id;
    private String mParam2;

    public ProfileRestaurantFragment() {
        // Required empty public constructor
    }


    public static ProfileRestaurantFragment newInstance(String user_id) {
        ProfileRestaurantFragment fragment = new ProfileRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, user_id);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_id = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView name, address, rating, active;
    private ImageView image;
    private RestaurantRepo restaurantRepo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_restaurant, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        rating = view.findViewById(R.id.rating);
        active = view.findViewById(R.id.active);
        image = view.findViewById(R.id.image);

        restaurantRepo = new RestaurantRepo();
        loadData(view);
    }

    private void loadData(View view){
        restaurantRepo.Get(user_id, new BaseRepo.OnDataFetchedListener<Restaurant>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataFetched(Restaurant res) {
                name.setText(res.getName().toUpperCase(Locale.ROOT));
                address.setText(res.getAddress());
                rating.setText("" + res.getRating());
                if(res.isActive()){
                    active.setTextColor(ContextCompat.getColor(view.getContext(), R.color.green));
                    active.setText("Đang hoạt động");
                } else {
                    active.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red));
                    active.setText("Ngừng hoạt động");
                }

                Glide.with(view.getContext())
                        .load(res.getUriImage())
                        .into(image);
            }
        });
    }
}