package com.example.booking_res.view.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_res.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRestaurantFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListRestaurantFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListRestaurantFragment newInstance() {
        ListRestaurantFragment fragment = new ListRestaurantFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_restaurant, container, false);



        return view;
    }
}