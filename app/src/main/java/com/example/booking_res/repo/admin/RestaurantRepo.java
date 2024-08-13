package com.example.booking_res.repo.admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RestaurantRepo extends BaseRepo {
    private String TAG = "RESTAURANT_ADMIN_REPOSITORY";

    public RestaurantRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("restaurant");
    }

    public void Add(Restaurant res){
        coRef.add(res);
    }

    public void Get(String userId, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("userId", userId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);

                    listener.onDataFetched(restaurant);
                    Log.i("ADMIN_USER_REPOSITORY", "Found restaurant: " + restaurant);
                } else {
                    listener.onDataFetched(null);
                    Log.i("ADMIN_USER_REPOSITORY", "No restaurant found for userId: " + userId);
                }
            } else {
                Log.e("ADMIN_USER_REPOSITORY", "Query failed with exception: ", task.getException());
            }
        });
    }
}
