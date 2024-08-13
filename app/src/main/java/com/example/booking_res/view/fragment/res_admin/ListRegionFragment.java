package com.example.booking_res.view.fragment.res_admin;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.res_admin.RegionRepo;
import com.example.booking_res.view.adapter.res_admin.ListRegionAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListRegionFragment extends Fragment {

    private static final String ARG_PARAM1 = "res_id";
    private String res_id;

    public ListRegionFragment() {

    }

    public static ListRegionFragment newInstance(String res_id) {
        ListRegionFragment fragment = new ListRegionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, res_id);
        fragment.setArguments(args);  // This line was missing
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            res_id = getArguments().getString(ARG_PARAM1);
        }

    }
    private OnRegionUpdateListener listener;

    private RecyclerView recyclerListRegion;

    private ListRegionAdapter listRegionAdapter;
    private RegionRepo regionRepo;
    private List<Region> _regions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_list_region, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        regionRepo = new RegionRepo();
        _regions = new ArrayList<>();
        recyclerListRegion = view.findViewById(R.id.recyclerListRegion);

        RecyclerViewListRegion(view);
        LoadDataRegion(res_id);
    }

    private void LoadDataRegion(String res_id){
        regionRepo.GetRegions(res_id, new BaseRepo.OnDataFetchedListener<List<Region>>() {

            @Override
            public void onDataFetched(List<Region> regions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    regions.sort(Comparator.comparing(Region::getPriority));
                }

                _regions.addAll(regions);

                listRegionAdapter.notifyDataSetChanged();
            }
        });
    }

    private void RecyclerViewListRegion(View view){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerListRegion.setLayoutManager(gridLayoutManager);

        listRegionAdapter = new ListRegionAdapter(_regions);

        recyclerListRegion.setAdapter(listRegionAdapter);

        listRegionAdapter.setDeleteClickListener(new ListRegionAdapter.DeleteClickListener() {
            @Override
            public void onDeleteClick(String uuid) {
                regionRepo.DeleteRegion(uuid);
                DeleteRegion(uuid);
                Toast.makeText(getActivity(), "Bạn đã xóa thành công.!", Toast.LENGTH_SHORT).show();
            }
        });

        listRegionAdapter.setUpdateClickListener(new ListRegionAdapter.UpdateClickListener() {
            @Override
            public void onUpdateCLick(Region region) {
                if (listener != null) {
                    listener.onRegionUpdate(region);
                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegionUpdateListener) {
            listener = (OnRegionUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegionUpdateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void DeleteRegion(String uuid){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _regions.removeIf(region -> region.getUuid().equals(uuid));
        }

        listRegionAdapter.notifyDataSetChanged();
    }

    public interface OnRegionUpdateListener {
        void onRegionUpdate(Region region);
    }
}