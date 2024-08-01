package com.example.booking_res.admin.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdminRepository extends BaseAdminRepository {
    private static final String TAG = "RESTAURANT_ADMIN_REPOSITORY";


    public RestaurantAdminRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("restaurant");
    }

    public void AddRestaurant(Restaurant res){
        coRef.add(res);
    }

    public void GetRestaurants(OnDataFetchedListener listener){
        coRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Restaurant> _restaurants = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        _restaurants.add(document.toObject(Restaurant.class));
                    }

                    listener.onDataFetched(_restaurants);
                } else {
                    Log.e(TAG, "get list restaurant failed");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.toString());
            }
        });
    }
}
