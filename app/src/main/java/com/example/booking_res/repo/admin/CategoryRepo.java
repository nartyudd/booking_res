package com.example.booking_res.repo.admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.model.Category;
import com.example.booking_res.model.Region;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.viewmodels.Item;
import com.example.booking_res.viewmodels.ItemCate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepo extends BaseRepo {
    private String TAG = "CATEGORY_ADMIN_REPOSITORY";

    public CategoryRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("category");
    }

    public void Add(Category cate){
        coRef.add(cate);
    }

    public void GetAll(OnDataFetchedListener listener){
        coRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Category> cates = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cates.add(document.toObject(Category.class));
                    }

                    listener.onDataFetched(cates);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void GetAllForRes(OnDataFetchedListener listener){
        coRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<ItemCate> itemCates = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Category cate = document.toObject(Category.class);
                    itemCates.add(new ItemCate(cate.getUuid(), cate.getName()));
                }

                listener.onDataFetched(itemCates);
            } else {
                Log.e(TAG, "Failed to get list of regions by res_id");
            }
        });
    }
}
