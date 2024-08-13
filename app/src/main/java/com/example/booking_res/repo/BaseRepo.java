package com.example.booking_res.repo;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class BaseRepo {
    protected FirebaseFirestore db;
    protected CollectionReference coRef;
    public interface OnDataFetchedListener<T> {
        void onDataFetched(T data);
    }
}
