package com.example.booking_res.repo;

import android.util.Log;

import com.example.booking_res.viewmodels.UserViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpRepo extends BaseRepo {

    private static final String TAG = "SignUpRepo";

    public SignUpRepo() {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("user");
    }


    public void AddUser(UserViewModel user){
        coRef.add(user).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Log.i(TAG, task.getResult().getId().toString());
            } else {
                Log.e(TAG, task.getResult().toString());

            }
        });
    }
}
