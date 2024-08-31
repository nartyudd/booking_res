package com.example.booking_res.view.fragment.res_admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_res.R;
import com.example.booking_res.model.Region;
import com.example.booking_res.repo.res_admin.RegionRepo;
import com.example.booking_res.utilities.GenID;
import com.example.booking_res.utilities.Regex;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateRegionFragment extends Fragment {

    private static final String ARG_PARAM1 = "res_id";
    private static final String ARG_PARAM2 = "region";

    private String res_id;
    private Region region;
    public CreateRegionFragment() {
        // Required empty public constructor
    }

    public static CreateRegionFragment newInstance(String res_id) {
        CreateRegionFragment fragment = new CreateRegionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, res_id);
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateRegionFragment newInstance(Region region) {
        CreateRegionFragment fragment = new CreateRegionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM2, region);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_PARAM1)) {
                res_id = getArguments().getString(ARG_PARAM1);
            }
            if (getArguments().containsKey(ARG_PARAM2)) {
                region = getArguments().getParcelable(ARG_PARAM2);
            }
        }
    }

    private TextView labelname, header;
    private Button btnAddRegion;
    private RegionRepo regionRepo;
    private EditText txtNameRegion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_region, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        btnAddRegion = view.findViewById(R.id.btnAddRegion);
        txtNameRegion =view.findViewById(R.id.txtNameRegion);
        labelname = view.findViewById(R.id.labelname);
        header = view.findViewById(R.id.header);

        if(region != null) {
            header.setText("Sửa khu vực");
            labelname.setText("Tên khu vực: " + region.getName());
            txtNameRegion.setHint("Nhập tên cần sửa....");
            btnAddRegion.setText("Sửa");
        }

        regionRepo = new RegionRepo();
        btnAddRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRegion(txtNameRegion.getText().toString().trim());
            }
        });
    }

    private void addRegion(String _nameRegion){
        if (_nameRegion.isEmpty()) {
            Toast.makeText(getActivity(), "Bạn phải điền đầy đủ các trường. !", Toast.LENGTH_SHORT).show();
        } else {
            if (region != null) {
                region.setName(_nameRegion.toUpperCase(Locale.ROOT));
                regionRepo.UpdateRegion(region.getUuid(), region.getName().toUpperCase(Locale.ROOT), Regex.getNumber(region.getName()));
                Toast.makeText(getActivity(), "Bạn đã cập nhật thành công. !", Toast.LENGTH_SHORT).show();
            } else {
                regionRepo.AddRegion(new Region(GenID.genUUID(), _nameRegion.toUpperCase(Locale.ROOT), res_id, Regex.getNumber(_nameRegion)));
                Toast.makeText(getActivity(), "Bạn đã thêm thành công. !", Toast.LENGTH_SHORT).show();
            }
            txtNameRegion.setText(null);
        }
    }

}