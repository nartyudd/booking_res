package com.example.booking_res.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.repo.SignUpRepo;
import com.example.booking_res.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button btn_sign_up;
    private EditText edt_email, edt_password;
    private SignUpRepo signUpRepo;
    private FirebaseAuth mAuth;
    private RadioGroup rd_role;
    private TextView btn_sign_in;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        mAuth = FirebaseAuth.getInstance();
        signUpRepo = new SignUpRepo();
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        btn_sign_in = view.findViewById(R.id.btn_sign_in);
        rd_role = (RadioGroup) view.findViewById(R.id.radio_group_role);

        btnSignUp(btn_sign_up, view);
        btnSignIn(btn_sign_in);
    }

    private void btnSignIn(TextView btn_sign_in){
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManagerHelper.getInstance().replaceFragment(SignInFragment.newInstance(), true);
            }
        });
    }

    private void btnSignUp(Button btn_sign_up, View _view ){

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = rd_role.getCheckedRadioButtonId();
                RadioButton radDegree = (RadioButton) _view.findViewById(id);

                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if(email == "" || password == "" || email.isEmpty() || password.isEmpty() || radDegree == null)
                {
                    Toast.makeText(getActivity(), "Bạn phải điền đầy đủ tất cả các trường.!", Toast.LENGTH_SHORT).show();
                } else {
                    SignUp(email, password, radDegree);
                }
            }
        });
    }

    private void SignUp(String email, String password,  RadioButton radDegree) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AddUser(task, radDegree);
                            Toast.makeText(getActivity(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            FragmentManagerHelper.getInstance().replaceFragment(SignInFragment.newInstance(), true);
                        } else {
                            Toast.makeText(getActivity(), "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void AddUser(Task<AuthResult> task,  RadioButton radDegree){
        String uid = task.getResult().getUser().getUid().toString();
        String email = task.getResult().getUser().getEmail();

        int index = email.indexOf("@gmail.com");

        String name = email.substring(0, index);

        UserViewModel user;

        if(radDegree.getId() == R.id.radio_res_admin) {
            user = new UserViewModel(uid, name, email, "res", true);
        } else {
           user = new UserViewModel(uid, name, email, "user", true);
        }

        signUpRepo.AddUser(user);

        edt_email.setText(null);
        edt_password.setText(null);

        mAuth.signOut();
    }

}