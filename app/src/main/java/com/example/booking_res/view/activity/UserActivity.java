package com.example.booking_res.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_res.R;
import com.example.booking_res.controller.FragmentManagerHelper;
import com.example.booking_res.view.fragment.user.ChooseTableFragment;
import com.example.booking_res.view.fragment.user.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    private Button btnSignOut;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navRes) {
                    loadFragment(new HomeFragment(), false);
                }
//                else if (itemId == R.id.navNotification) {
//                    loadFragment(new NotificationFragment(), false);
//
//                } else {
//                    loadFragment(new ProfileFragment(), false);
//
//                }
                return true;
            }
        });

        loadFragment(new HomeFragment(), true);
//        FragmentManagerHelper.getInstance().init(getSupportFragmentManager(), R.id.frameLayout);
//        FragmentManagerHelper.getInstance().addFragment(new HomeFragment(), false);

    }

    private void loadFragment(Fragment fragment, boolean isAppInitalized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isAppInitalized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }

//    private void hanldeReplaceFragment(Fragment frag){
//        FragmentManagerHelper.getInstance().replaceFragment(frag, false);
//    }
}