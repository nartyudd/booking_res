package com.example.booking_res.repo.admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

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

    public void GetAll(OnDataFetchedListener listener){
        coRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Restaurant> res = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        res.add(document.toObject(Restaurant.class));
                    }

                    listener.onDataFetched(res);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void Update(String res_id, Restaurant _res){
        Query query = coRef.whereEqualTo("uuid", res_id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                        Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);

                        restaurant.setName(_res.getName());
                        restaurant.setAddress(_res.getAddress());
                        restaurant.setActive(_res.isActive());
                        restaurant.setUuid(_res.getUuid());
                        restaurant.setRating(_res.getRating());
                        restaurant.setUriImage(_res.getUriImage());
                        restaurant.setUserId(_res.getUserId())
                        ;
                        coRef.document(documentSnapshot.getId()).set(restaurant, SetOptions.merge());

                    }
                }
            }
        });
    }
}
