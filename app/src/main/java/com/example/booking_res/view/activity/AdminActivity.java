package com.example.booking_res.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.view.fragment.res_admin.CreateRestaurantFragment;
import com.example.booking_res.view.fragment.admin.CreateUserFragment;
import com.example.booking_res.view.fragment.admin.ListRestaurantFragment;
import com.example.booking_res.view.fragment.admin.ListUserFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private ShapeableImageView shapeableImageView;
    private NavigationView navigationView;

    private FirebaseAuth mAuth;
    private static final int NAV_USER_ADMIN = R.id.navUserAdmin;
    private static final int NAV_RESTAURANT_ADMIN = R.id.navRestaurantAdmin;
    private static final int NAV_LOGOUT_ADMIN = R.id.navLogoutAdmin;
    private static final String TAG = "AdminActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    private void init(){
        navigationView = (NavigationView) findViewById(R.id.navigationMenuAdmin);
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBarAdmin);

        mAuth = FirebaseAuth.getInstance();
        FragmentManagerHelper.getInstance().init(getSupportFragmentManager(), R.id.fragmentContainer);

        setSupportActionBar(topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                navigationView.setVisibility(View.VISIBLE);
            }
        });
        FragmentManagerHelper.getInstance().init(getSupportFragmentManager(), R.id.fragmentContainer);

        setFragmentInit();
        handleCloseNavigation();
        setListenerItemNavigation();
    }

//    private void handleListFragment(){
//        Fragment current = FragmentManagerHelper.getInstance().getCurrentFragment();
//
//        if(current instanceof CreateRestaurantFragment){
//            FragmentManagerHelper.getInstance().replaceFragment(ListRestaurantFragment.newInstance(), false);
//        } else if (current instanceof CreateUserFragment) {
//            FragmentManagerHelper.getInstance().replaceFragment(ListUserFragment.newInstance(), false);
//        }
//    }
//
//    private void hanndleCreateFragment(){
//        Fragment current = FragmentManagerHelper.getInstance().getCurrentFragment();
//
//        if(current instanceof ListRestaurantFragment){
//            FragmentManagerHelper.getInstance().replaceFragment(CreateRestaurantFragment.newInstance("234"), false);
//        } else if(current instanceof ListUserFragment){
//            FragmentManagerHelper.getInstance().replaceFragment(CreateUserFragment.newInstance(), false);
//        }
//    }


    private void setFragmentInit(){
        handleUserAdminFragment();
    }

    private void handleUserAdminFragment() {
        topAppBar.setTitle("Quản Lý Người Dùng");

        FragmentManagerHelper.getInstance().replaceFragment(ListUserFragment.newInstance(), false);

    }

    private void handleRestaurantAdminFragment() {
        topAppBar.setTitle("Quản Lý Nhà Hàng");

        FragmentManagerHelper.getInstance().replaceFragment(ListRestaurantFragment.newInstance(), false);
    }

    public void setListenerItemNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == NAV_USER_ADMIN) {
                    handleUserAdminFragment();
                } else if(itemId == NAV_RESTAURANT_ADMIN) {
                    handleRestaurantAdminFragment();
                } else if (itemId == NAV_LOGOUT_ADMIN) {
                    handleNavLogout();
                } else {
                    setCLoseNavigtion();
                }

                setCLoseNavigtion();

                return true;
            }
        });
    }

    public void handleCloseNavigation(){

        shapeableImageView = (ShapeableImageView) findViewById(R.id.navigationCloseAdmin);


        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCLoseNavigtion();
            }
        });
    }


    public void setCLoseNavigtion(){
        navigationView.setVisibility(View.GONE);
    }

    private void handleNavLogout() {
        mAuth.signOut();
        startActivity(new Intent(AdminActivity.this, MainActivity.class));
        finish();
    }
}