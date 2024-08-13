package com.example.booking_res.view.fragment.res_admin;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Table;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.res_admin.RegionRepo;
import com.example.booking_res.repo.res_admin.TableRepo;
import com.example.booking_res.utilities.GenID;
import com.example.booking_res.utilities.Regex;
import com.example.booking_res.viewmodels.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CreateTableFragment extends Fragment {

    private static final String ARG_PARAM1 = "res_id";

    private String res_id;

    public CreateTableFragment() {

    }

    public static CreateTableFragment newInstance(String res_id) {
        CreateTableFragment fragment = new CreateTableFragment();
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
    private Spinner spinnerReion;
    private List<Item> items;
    private RegionRepo regionRepo;
    private String document_id;
    private ArrayAdapter<Item> adapter;
    private EditText nameTable;
    private Button btnAddTable;
    private TableRepo tableRepo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_table, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        nameTable = view.findViewById(R.id.nameTable);
        btnAddTable = view.findViewById(R.id.btnAddTable);
        spinnerReion = view.findViewById(R.id.spinnerReion);
        items = new ArrayList<>();
        regionRepo = new RegionRepo();
        tableRepo = new TableRepo();

        // Set up the adapter
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerReion.setAdapter(adapter);

        // Set the onItemSelectedListener for the Spinner
        spinnerReion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Item selectedItem = (Item) parentView.getItemAtPosition(position);
                document_id = selectedItem.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        loadData();

        btnAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddTable(nameTable.getText().toString().trim());
            }
        });

    }

    private void loadData() {
        regionRepo.GetRegionForTable(res_id, new BaseRepo.OnDataFetchedListener<List<Item>>() {
            @Override
            public void onDataFetched(List<Item> _items) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    items.sort(Comparator.comparing(Item::getPriority));
                }
                items.addAll(_items);
                adapter.notifyDataSetChanged();

            }
        });


    }

    private void handleAddTable(String _nameTable){
        if(_nameTable.isEmpty()){
            Toast.makeText(getActivity(), "Bạn phải điền đầy đủ các trường. !", Toast.LENGTH_SHORT).show();
        } else {
            tableRepo.AddTable(document_id, new Table(GenID.genUUID(), _nameTable.toUpperCase(Locale.ROOT)));
            Toast.makeText(getActivity(), "Bạn đã thêm thành công. !", Toast.LENGTH_SHORT).show();
            nameTable.setText(null);
        }
    }

}