package com.example.booking_res.view.fragment.res_admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Table;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.res_admin.TableRepo;
import com.example.booking_res.view.adapter.res_admin.ListTableParentAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListTableFragment extends Fragment {

    private static final String ARG_PARAM1 = "res_id";

    private String res_id;

    public ListTableFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListTableFragment newInstance(String res_id) {
        ListTableFragment fragment = new ListTableFragment();
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

    private OnTableUpdateListener listener;

    private TableRepo tableRepo;
    private RecyclerView recyclerView;
    private ListTableParentAdapter listTableParentAdapter;
    private List<Region> _regions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_table, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        _regions = new ArrayList<>();

        tableRepo = new TableRepo();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        listTableParentAdapter = new ListTableParentAdapter(_regions);

        recyclerView.setAdapter(listTableParentAdapter);

        loadData();
        handleTable();
    }

    private void handleTable(){

        listTableParentAdapter.setDeleteClickListener(new ListTableParentAdapter.DeleteClickListener() {
            @Override
            public void onDeleteClick(String regionId, Table table) {
                tableRepo.DeleteTable(regionId, table);
            }
        });

        listTableParentAdapter.setUpdateClickListener(new ListTableParentAdapter.UpdateClickListener() {
            @Override
            public void onUpdateCLick(String region_id, Table table) {
                listener.onTableUpdate(region_id,table );
            }
        });
    }


    private void loadData(){
        tableRepo.GetAllTable(res_id, new BaseRepo.OnDataFetchedListener<List<Region>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataFetched(List<Region> regions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    regions.sort(Comparator.comparing(Region::getPriority));
                }

                _regions.addAll(regions);

                listTableParentAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListTableFragment.OnTableUpdateListener) {
            listener = (ListTableFragment.OnTableUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListTableFragment.OnTableUpdateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnTableUpdateListener {
        void onTableUpdate(String region_id, Table table);
    }
}