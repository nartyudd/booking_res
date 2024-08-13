package com.example.booking_res.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.SignInRepo;
import com.example.booking_res.view.activity.AdminActivity;
import com.example.booking_res.view.activity.ResAdminActivity;
import com.example.booking_res.view.activity.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btn_sign_in;
    private TextView btn_sign_up;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SignInRepo signInRepo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        btn_sign_in = view.findViewById(R.id.btn_sign_in);
        btn_sign_up = view.findViewById(R.id.btn_sign_up);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManagerHelper.getInstance().replaceFragment(SignInFragment.newInstance(), true);
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManagerHelper.getInstance().replaceFragment(SignUpFragment.newInstance(), true);
            }
        });
        init();
        return view;
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        signInRepo = new SignInRepo();
        CheckUserLogin();
    }

    private void CheckUserLogin(){
        mUser = mAuth.getCurrentUser();
        if(mUser != null){
            CheckUserRole(mUser.getUid());
        }
    }

    private void CheckUserRole(String uid){
        signInRepo.getRole(uid, new BaseRepo.OnDataFetchedListener<String>() {
            @Override
            public void onDataFetched(String role) {
                switch (role) {
                    case "user":
                        startActivity(new Intent(getActivity(), UserActivity.class));
                        getActivity().finish();
                        break;
                    case "admin":
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                        getActivity().finish();
                        break;
                    case "res":
                        startActivity(new Intent(getActivity(), ResAdminActivity.class));
                        getActivity().finish();
                        break;
                }
            }
        });
    }
}