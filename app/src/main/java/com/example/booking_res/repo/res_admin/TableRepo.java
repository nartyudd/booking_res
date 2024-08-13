package com.example.booking_res.repo.res_admin;

import com.example.booking_res.model.Table;
import com.example.booking_res.repo.BaseRepo;
import com.google.firebase.firestore.FirebaseFirestore;

public class TableRepo extends BaseRepo {
    private static final String TAG = "TABLE_ADMIN_REPOSITORY";

    public TableRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("region");
    }

    public void AddTable(String document_id, Table Table){
        coRef.document(document_id).collection("tables").add(Table);
    }
}
