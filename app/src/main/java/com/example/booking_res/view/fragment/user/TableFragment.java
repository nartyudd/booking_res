package com.example.booking_res.view.fragment.user;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.res_admin.TableRepo;
import com.example.booking_res.view.adapter.res_admin.ListTableParentAdapter;
import com.example.booking_res.view.adapter.user.TableParentAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableFragment extends Fragment {


    private static final String ARG_PARAM1 = "res_id";

    // TODO: Rename and change types of parameters
    private String res_id;
    private String user_id;

    public TableFragment() {
        // Required empty public constructor
    }

    public static TableFragment newInstance(String res_id) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, res_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            res_id = getArguments().getString(ARG_PARAM1);
        }
    }
    private TableRepo tableRepo;
    private RecyclerView recyclerView;
    private TableParentAdapter tableParentAdapter;
    private List<Region> _regions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_table, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);

        _regions = new ArrayList<>();
        tableRepo = new TableRepo();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        tableParentAdapter = new TableParentAdapter(_regions);
        recyclerView.setAdapter(tableParentAdapter);

        loadData();

        tableParentAdapter.SetOnClicckListener(new TableParentAdapter.OnClickListener() {
            @Override
            public void onClick(String table_id) {
                Toast.makeText(getActivity(), "table_id : " + table_id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(){
        tableRepo.GetAllTable(res_id, new BaseRepo.OnDataFetchedListener<List<Region>>() {
            @Override
            public void onDataFetched(List<Region> regions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    regions.sort(Comparator.comparing(Region::getPriority));
                }

                _regions.addAll(regions);

                tableParentAdapter.notifyDataSetChanged();
            }
        });
    }

}