package com.example.booking_res.view.fragment.res_admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.booking_res.R;
import com.example.booking_res.view.adapter.res_admin.RegionExpandableListAdapter;

public class ListTableFragment extends Fragment {

    private static final String ARG_PARAM1 = "res_id";
    private static final String ARG_PARAM2 = "region_id";

    private String res_id;
    private String region_id;

    public ListTableFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListTableFragment newInstance(String res_id, String region_id) {
        ListTableFragment fragment = new ListTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, res_id);
        args.putString(ARG_PARAM2, region_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            res_id = getArguments().getString(ARG_PARAM1);
            region_id = getArguments().getString(ARG_PARAM2);
        }
    }


    private ExpandableListView expandableList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_table, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        expandableList = view.findViewById(R.id.expandableList);

    }
}