package com.example.booking_res.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.booking_res.viewmodels.UserViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SignInRepo extends BaseRepo{
    private static final String TAG = "SignInRepo";

    public SignInRepo() {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("user");
    }

    public void getAll(OnDataFetchedListener listener){
        coRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UserViewModel> users = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserViewModel user = document.toObject(UserViewModel.class);
                                Log.d(TAG, "User: " + user.toString());
                                users.add(user);
                            }

                            Log.d(TAG, "Total users fetched: " + users.size());
                            listener.onDataFetched(users);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    public void getRole(String userId, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("userId", userId);
        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        QuerySnapshot querySnapshot = task.getResult();
                        if(querySnapshot != null && !querySnapshot.isEmpty()) {

                            String userRole;

                            userRole = querySnapshot.getDocuments().get(0).getString("role");

                            if(userRole != null) {
                                listener.onDataFetched(userRole);
                            } else {
                                listener.onDataFetched(null);
                            }

                        }

                    }
                });
    }


    public Task<Boolean> checkExistsAsync(String email) {
        Query query = coRef.whereEqualTo("email", email);

        return query.get().continueWith(new Continuation<QuerySnapshot, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<QuerySnapshot> task) throws Exception {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        return documentSnapshot.toObject(UserViewModel.class).isExists();
                    } else {
                        return false;
                    }
                } else {
                    throw task.getException();
                }
            }
        });
    }

}
