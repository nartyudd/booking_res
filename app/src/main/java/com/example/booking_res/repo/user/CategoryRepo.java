package com.example.booking_res.repo.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.model.Category;
import com.example.booking_res.repo.BaseRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepo extends BaseRepo {
    private String TAG = "CATEGORY_USER_REPOSITORY";

    public CategoryRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("category");
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

}
