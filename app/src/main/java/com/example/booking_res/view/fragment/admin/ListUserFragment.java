package com.example.booking_res.view.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_res.R;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.UserRepo;
import com.example.booking_res.view.adapter.admin.ListUserAdapter;
import com.example.booking_res.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListUserFragment extends Fragment {

    public ListUserFragment() {
        // Required empty public constructor
    }

    public static ListUserFragment newInstance() {

        return new ListUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<UserViewModel> users;
    private RecyclerView recyclerView;
    private ListUserAdapter listUserAdapter;
    private UserRepo userRepo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_user, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        users = new ArrayList<>();
        userRepo = new UserRepo();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        listUserAdapter = new ListUserAdapter(users);

        recyclerView.setAdapter(listUserAdapter);

        loadData();
    }

    private void loadData(){
        userRepo.GetALlUser(new BaseRepo.OnDataFetchedListener<List<UserViewModel>>() {
            @Override
            public void onDataFetched(List<UserViewModel> _users) {
                users.addAll(_users);

                listUserAdapter.notifyDataSetChanged();
            }
        });
    }
}