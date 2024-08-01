package com.example.booking_res.admin.repo;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class BaseAdminRepository {
    protected FirebaseFirestore db;
    protected CollectionReference coRef;
    public interface OnDataFetchedListener<T> {
        void onDataFetched(T data);
    }
}
