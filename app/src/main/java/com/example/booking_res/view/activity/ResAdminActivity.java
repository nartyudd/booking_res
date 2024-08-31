package com.example.booking_res.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.booking_res.R;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.model.Table;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.RestaurantRepo;
import com.example.booking_res.view.fragment.res_admin.CreateRegionFragment;
import com.example.booking_res.view.fragment.res_admin.CreateRestaurantFragment;
import com.example.booking_res.view.fragment.res_admin.CreateTableFragment;
import com.example.booking_res.view.fragment.res_admin.ListRegionFragment;
import com.example.booking_res.view.fragment.res_admin.ListTableFragment;
import com.example.booking_res.view.fragment.res_admin.ProfileRestaurantFragment;
import com.example.booking_res.view.fragment.res_admin.UpdateProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResAdminActivity extends AppCompatActivity implements ListRegionFragment.OnRegionUpdateListener, ListTableFragment.OnTableUpdateListener {
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
    private static final int NAV_PROFILE_ADMIN = R.id.navProfileAdmin;

    private int tabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_admin);

        init();
    }

    @Override
    public void onRegionUpdate(Region region) {
        Log.i("RES_ADMIN_ACTIVITY", "onRegionUpdate called with region: " + region.getName() + "tabindex : " + tabIndex);
        tabIndex = 1;
        tab_layout.getTabAt(1).select();
        FragmentManagerHelper.getInstance().replaceFragment(CreateRegionFragment.newInstance(region), true);
        handleTab_layout();
    }


    @Override
    public void onTableUpdate(String region_id, Table table) {
        Log.i("RES_ADMIN_ACTIVITY", "onTableUpdate called with table: " + table.getName() + "tabindex : " + tabIndex);
        tabIndex = 2;
        tab_layout.getTabAt(1).select();
        FragmentManagerHelper.getInstance().replaceFragment(CreateTableFragment.newInstance(region_id, table), true);
        handleTab_layout();
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
                    tabIndex = 1;
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
                    } else if(tabIndex == 3) {
                        handleProfileAdminFragment();
                    }
                    else if (tabIndex == 4) {
                        //                return new ListBillFragment();
                    }
                } else if (tab.getPosition() == 1) { // Add tab
                    if (tabIndex == 1) {
                        handleCreateRegion();
                    } else if (tabIndex == 2) {
                        handleCreateTable();
                    } else if(tabIndex == 3) {
                        handleUpdateProfile();
                    } else if (tabIndex == 4)  {
//                        tabIndex = 3;
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

    private void handleCreateRegion(){
        Log.i("RES_ADMIN_ACTIVITY",  " handleCreateRegion : " + tabIndex);
        tabIndex = 1;
        FragmentManagerHelper.getInstance().replaceFragment(CreateRegionFragment.newInstance(res_id), true);
        tab_layout.getTabAt(1).select();
    }

    private void handleCreateTable(){
        Log.i("RES_ADMIN_ACTIVITY",  " handleCreateTable : " + tabIndex);
        tabIndex = 2;
        FragmentManagerHelper.getInstance().replaceFragment(CreateTableFragment.newInstance(res_id), true);
        tab_layout.getTabAt(1).select();
    }

    private void handleUpdateProfile(){
        tabIndex = 3;
        FragmentManagerHelper.getInstance().replaceFragment(UpdateProfileFragment.newInstance(res_id, mUser.getUid()), false);
        tab_layout.getTabAt(1).select();
    }


    private void handleRegionAdminFragment(){
        Log.i("RES_ADMIN_ACTIVITY",  " handleRegionAdminFragment : " + tabIndex);
        tabIndex = 1;
        topAppBar.setTitle( "Quản Lý Khu Vực");
        FragmentManagerHelper.getInstance().replaceFragment(ListRegionFragment.newInstance(res_id), false);
        tab_layout.getTabAt(0).select();
        handleTab_layout();
    }

    private void handleTableAdminFragment() {
        topAppBar.setTitle( "Quản Lý Bàn Ăn");
        tabIndex = 2;
        Log.i("RES_ADMIN_ACTIVITY",  " handleTableAdminFragment : " + tabIndex);
        FragmentManagerHelper.getInstance().replaceFragment(ListTableFragment.newInstance(res_id), false);
        tab_layout.getTabAt(0).select();
        handleTab_layout();
    }


    private void handleProfileAdminFragment(){
        tabIndex = 3;
        topAppBar.setTitle("Quản lý Thông tin cá nhân");
        FragmentManagerHelper.getInstance().replaceFragment(ProfileRestaurantFragment.newInstance(mUser.getUid()), false);
        tab_layout.getTabAt(0).select();
        handleTab_layout();
    }


    private void handleBillAdminFragment() {
        Log.i("RES_ADMIN_ACTIVITY",  " handleBillAdminFragment :  " + tabIndex);
        tabIndex = 4;
        Toast.makeText(this, "Bill Admin", Toast.LENGTH_SHORT).show();
        handleTab_layout();
//        tab_layout.getTabAt(0).select();
    }

    private void handleTab_layout(){
        if(tabIndex == 3){
            tab_layout.getTabAt(0).setText("Thông tin nhà hàng");
            tab_layout.getTabAt(1).setText("Sửa thông tin");
        } else {
            tab_layout.getTabAt(0).setText("Danh sách");
            tab_layout.getTabAt(1).setText("Thêm / Sửa");
        }
    }


    public void setListenerItemNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

               if(itemId == NAV_REIOGN_ADMIN) {
                    handleRegionAdminFragment();
                } else if(itemId == NAV_TABLE_ADMIN) {
                   handleTableAdminFragment();
               } else if (itemId == NAV_BILL_ADMIN){
                    handleBillAdminFragment();
                } else if(itemId == NAV_PROFILE_ADMIN) {
                   handleProfileAdminFragment();
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