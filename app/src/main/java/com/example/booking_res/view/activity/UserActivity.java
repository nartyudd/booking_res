package com.example.booking_res.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.view.fragment.user.HomeFragment;
import com.example.booking_res.view.fragment.user.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity {

    private static final int NAV_RESTAURANT = R.id.navRes;
    private static final int NAV_NOTIFICATE = R.id.navNotification;
    private static final int NAV_PROFILE = R.id.navProfile;
    private BottomNavigationView bottomNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        init();
    }

    private void init(){
        bottomNavView = findViewById(R.id.bottomNavView);

        initFragment();

        setListenerItemNavigation(bottomNavView);
    }

    private void setListenerItemNavigation(BottomNavigationView bottomNavView){
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == NAV_RESTAURANT) {
                    initFragment();
                } else if (itemId == NAV_NOTIFICATE) {
//                    loadFragment(new ChooseTableFragment(), false);

                } else if (itemId == NAV_PROFILE) {
                    FragmentManagerHelper.getInstance().replaceFragment(ProfileFragment.newInstance("ss", "1ss"), false);
                }
                return true;
            }
        });
    }

    private void initFragment(){
        FragmentManagerHelper.getInstance().init(getSupportFragmentManager(), R.id.frameLayout);
        FragmentManagerHelper.getInstance().addFragment(new HomeFragment(), false);
    }


//    private void hanldeReplaceFragment(Fragment frag){
//        FragmentManagerHelper.getInstance().replaceFragment(frag, false);
//    }
}