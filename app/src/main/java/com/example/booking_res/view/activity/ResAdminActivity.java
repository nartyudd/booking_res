package com.example.booking_res.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.booking_res.Helper.FragmentType;
import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.RestaurantRepo;
import com.example.booking_res.view.fragment.res_admin.CreateRegionFragment;
import com.example.booking_res.view.fragment.res_admin.CreateRestaurantFragment;
import com.example.booking_res.view.fragment.res_admin.CreateTableFragment;
import com.example.booking_res.view.fragment.res_admin.ListRegionFragment;
import com.example.booking_res.view.fragment.res_admin.ListTableFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResAdminActivity extends AppCompatActivity implements ListRegionFragment.OnRegionUpdateListener {
    private String res_id;

    private MaterialToolbar topAppBar;
    private ShapeableImageView shapeableImageView;
    private NavigationView navigationView;
    private TabLayout tab_layout;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RestaurantRepo resRepo;

    private static final int NAV_TABLE_ADMIN = R.id.navTableAdmin;
    private static final int NAV_BILL_ADMIN = R.id.navBillAdmin;
    private static final int NAV_LOGOUT_ADMIN = R.id.navLogoutAdmin;
    private static final int NAV_REIOGN_ADMIN = R.id.navRegionAdmin;


    private static int tabIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_admin);

        init();
    }

    @Override
    public void onRegionUpdate(Region region) {
        tab_layout.getTabAt(1).select();
        FragmentManagerHelper.getInstance().replaceFragment(CreateRegionFragment.newInstance(region), true);
    }

    private void init(){
        navigationView = (NavigationView) findViewById(R.id.navigationMenuAdmin);
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBarResAdmin);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);

        resRepo = new RestaurantRepo();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


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
        handleTabLayout(tab_layout);
    }


    private void setFragmentInit(){
        resRepo.Get(mUser.getUid(), new BaseRepo.OnDataFetchedListener<Restaurant>() {
            @Override
            public void onDataFetched(Restaurant res) {
                if(res != null) {
                    res_id = res.getUuid();
                    handleRegionAdminFragment();
                }
                else{
                    navigationView.setVisibility(View.INVISIBLE);
                    topAppBar.setVisibility(View.INVISIBLE);
                    tab_layout.setVisibility(View.INVISIBLE);
                    FragmentManagerHelper.getInstance().replaceFragment(CreateRestaurantFragment.newInstance(mUser.getUid()), false);
                }
            }
        });
    }

    private void handleTabLayout(TabLayout tab_layout){
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) { // List tab
                    if (tabIndex == 1) {
                        handleRegionAdminFragment();
                    } else if (tabIndex == 2) {
                        handleTableAdminFragment();
                    } else if (tabIndex == 3) {
                        //                return new ListBillFragment();
                    }
                } else if (tab.getPosition() == 1) { // Add tab
                    if (tabIndex == 1) {
                        FragmentManagerHelper.getInstance().replaceFragment(CreateRegionFragment.newInstance(res_id), true);
                    } else if (tabIndex == 2) {
                        FragmentManagerHelper.getInstance().replaceFragment(CreateTableFragment.newInstance(res_id), true);
                    } else if (tabIndex == 3)  {
//                        navigateToFragment(FragmentType.CREATE_BILL);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void handleTableAdminFragment() {
        topAppBar.setTitle( "Quản Lý Bàn Ăn");
        FragmentManagerHelper.getInstance().replaceFragment(ListTableFragment.newInstance("11", "11"), false);
    }

    private void handleBillAdminFragment() {
        Toast.makeText(this, "Bill Admin", Toast.LENGTH_SHORT).show();

    }

    private void handleRegionAdminFragment(){
        topAppBar.setTitle( "Quản Lý Khu Vực");
        FragmentManagerHelper.getInstance().replaceFragment(ListRegionFragment.newInstance(res_id), false);
    }

    public void setListenerItemNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

               if(itemId == NAV_REIOGN_ADMIN) {
                    handleRegionAdminFragment();
                    tabIndex = 1;
                } else if(itemId == NAV_TABLE_ADMIN) {
                   handleTableAdminFragment();
                   tabIndex = 2;
               } else if (itemId == NAV_BILL_ADMIN){
                    handleBillAdminFragment();
                    tabIndex = 3;
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
        startActivity(new Intent(ResAdminActivity.this, MainActivity.class));
        finish();
    }
}