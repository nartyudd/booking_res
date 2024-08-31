package com.example.booking_res.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.view.fragment.user.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        FragmentManagerHelper.getInstance().init(getSupportFragmentManager(), R.id.frameLayout);
        FragmentManagerHelper.getInstance().replaceFragment(new HomeFragment(), false);

    }

//    private void hanldeReplaceFragment(Fragment frag){
//        FragmentManagerHelper.getInstance().replaceFragment(frag, false);
//    }
}