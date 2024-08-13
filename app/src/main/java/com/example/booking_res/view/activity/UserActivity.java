package com.example.booking_res.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
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