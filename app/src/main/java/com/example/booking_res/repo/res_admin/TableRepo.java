package com.example.booking_res.repo.res_admin;

import android.os.Build;
import android.util.Log;

import com.example.booking_res.model.Region;
import com.example.booking_res.model.Table;
import com.example.booking_res.repo.BaseRepo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableRepo extends BaseRepo {
    private static final String TAG = "TABLE_ADMIN_REPOSITORY";

    public TableRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("region");
    }

    public void AddTableToRegion(String document_id, Table Table){
        coRef.document(document_id)
                .update("tables", FieldValue.arrayUnion(Table));
    }

    public void GetAllTable(String res_id, OnDataFetchedListener listener) {
        Query query = coRef.whereEqualTo("res_id", res_id);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Region> regions = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Region region = document.toObject(Region.class);
                    regions.add(region);
                }
                listener.onDataFetched(regions);
            } else {
                Log.e(TAG, "Lấy dữ liệu thất bại.", task.getException());
            }
        });
    }

    public void DeleteTable(String regionId, Table table){
        Query query = coRef.whereEqualTo("uuid", regionId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                if(documentSnapshot != null){
                    String id = documentSnapshot.getId();

                    coRef.document(id).update("tables", FieldValue.arrayRemove(table));
                }
            }
        });
    }

    public void UpdateTable(String regionId, String tableId, String newName) {
        Query query = coRef.whereEqualTo("uuid", regionId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                if (documentSnapshot != null) {
                    // Get the ID of the document to update
                    String id = documentSnapshot.getId();

                    List<Table> tables = documentSnapshot.toObject(Region.class).getTables();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tables.forEach(t -> {
                            if(t.getUuid().equals(tableId)){
                                t.setName(newName);
                            }
                        });
                    }

                    coRef.document(id).update("tables", tables);

                }
            }
        });
    }


}
