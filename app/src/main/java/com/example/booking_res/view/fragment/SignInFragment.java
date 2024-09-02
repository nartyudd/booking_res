package com.example.booking_res.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.SignInRepo;
import com.example.booking_res.repo.SignUpRepo;
import com.example.booking_res.view.activity.AdminActivity;
import com.example.booking_res.view.activity.ResAdminActivity;
import com.example.booking_res.view.activity.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {
    private String TAG = "SignInFragment_TAG";
    public SignInFragment() {
    }


    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button btn_sign_in;
    private EditText edt_email, edt_password;
    private FirebaseAuth mAuth;
    private SignInRepo signInRepo;
    private TextView btn_sign_up;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        init(view);

        return view;
    }

    private void init(View view){

        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        btn_sign_in = view.findViewById(R.id.btn_sign_in);
        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        // constructor
        signInRepo = new SignInRepo();
        mAuth = FirebaseAuth.getInstance();

        btnSignIn(btn_sign_in);
        btnSignUp(btn_sign_up);
    }

    private void btnSignUp(TextView btn_sign_up){
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManagerHelper.getInstance().replaceFragment(SignUpFragment.newInstance(), true);
            }
        });
    }
    private void btnSignIn(Button btn_sign_in){
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });
    }

    private void CheckUserRole(String uid){
        signInRepo.getRole(uid, new BaseRepo.OnDataFetchedListener<String>() {
            @Override
            public void onDataFetched(String role) {

                Log.i(TAG, role);
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



    private void SignIn(){
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if(email == "" || password == "" || email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(getActivity(), "Bạn phải điền đủ các trường.!", Toast.LENGTH_SHORT).show();
        } else {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Đăng nhập thành công.!",
                                    Toast.LENGTH_SHORT).show();
                            String uid = task.getResult().getUser().getUid().toString().trim();
                            CheckUserRole(uid);
                        } else {
                            Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu không đúng.!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}