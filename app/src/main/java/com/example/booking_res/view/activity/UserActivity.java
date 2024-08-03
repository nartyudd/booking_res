package com.example.booking_res.view.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.booking_res.R;
import com.example.booking_res.controller.FragmentManagerHelper;
import com.example.booking_res.view.fragment.user.HomeFragment;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FragmentManagerHelper.getInstance().init(getSupportFragmentManager(), R.id.frameLayout);
        FragmentManagerHelper.getInstance().addFragment(new HomeFragment(), false);

    }

//    private void hanldeReplaceFragment(Fragment frag){
//        FragmentManagerHelper.getInstance().replaceFragment(frag, false);
//    }
}