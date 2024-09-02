package com.example.booking_res.repo.user;

import com.example.booking_res.repo.BaseRepo;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantRepo extends BaseRepo {
    private String TAG = "RESTAURANT_ADMIN_REPOSITORY";

    public RestaurantRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("restaurant");
    }

    public void GetAll(OnDataFetchedListener listener){

    }
}
