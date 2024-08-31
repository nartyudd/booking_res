package com.example.booking_res.repo.admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepo extends BaseRepo {
    private String TAG = "USER_ADMIN_REPOSITORY";

    public UserRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("user");
    }

    public void GetALlUser(OnDataFetchedListener listener){
        coRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UserViewModel> users = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(document.toObject(UserViewModel.class));
                            }

                            listener.onDataFetched(users);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });

    }
}
