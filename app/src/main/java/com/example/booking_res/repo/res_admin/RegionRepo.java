package com.example.booking_res.repo.res_admin;

import android.util.Log;

import com.example.booking_res.model.Region;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.viewmodels.Item;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegionRepo extends BaseRepo {
    private static final String TAG = "REGION_ADMIN_REPOSITORY";

    public RegionRepo(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("region");
    }

    public void AddRegion(Region region){
        coRef.add(region);
    }

    public void GetRegions(String res_id, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("res_id", res_id);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Region> _region = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    _region.add(document.toObject(Region.class));
                    Log.i(TAG, document.toObject(Region.class).toString());
                }

                listener.onDataFetched(_region);
            } else {
                Log.e(TAG, "Failed to get list of regions by res_id");
            }
        });
    }

    public void GetRegionForTable(String res_id, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("res_id", res_id);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Item> items = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Region region = document.toObject(Region.class);
                    items.add(new Item(document.getId(), region.getName(), region.getPriority()));
                }

                listener.onDataFetched(items);
            } else {
                Log.e(TAG, "Failed to get list of regions by res_id");
            }
        });
    }

    public void UpdateRegion(String uuid, String name, int priority){
          Query query = coRef.whereEqualTo("uuid", uuid);

          query.get().addOnCompleteListener(task -> {
              if(task.isSuccessful()){
                  DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                  if(documentSnapshot != null){
                      String id = documentSnapshot.getId();

                      coRef.document(id).update("name", name, "priority", priority);
                  }
              }
          });
    }

    public void DeleteRegion(String uuid){
        Query query = coRef.whereEqualTo("uuid", uuid);

        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                if(documentSnapshot != null){
                    String id = documentSnapshot.getId();

                    coRef.document(id).delete();
                }
            }
        });
    }
}
