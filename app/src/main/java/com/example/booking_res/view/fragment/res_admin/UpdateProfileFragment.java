package com.example.booking_res.view.fragment.res_admin;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.RestaurantRepo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "res_id";
    private static final String ARG_PARAM2 = "user_id";

    // TODO: Rename and change types of parameters
    private String res_id;
    private String user_id;
    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    public static UpdateProfileFragment newInstance(String res_id, String user_id) {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, res_id);
        args.putString(ARG_PARAM2, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            res_id = getArguments().getString(ARG_PARAM1);
            user_id = getArguments().getString(ARG_PARAM2);
        }
    }

    private String oldImageUrl;
    private Float rating;
    private ImageView imageView;
    private RestaurantRepo restaurantRepo;
    private EditText editName, editAddress;
    private Button uploadButtonImage, btnAddRestaurant;
    private Uri imageUri;
    private StorageReference storageReference;
    private String _name, _address;
    private LinearProgressIndicator progressIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        editName = view.findViewById(R.id.editName);
        editAddress = view.findViewById(R.id.editAddress);
        uploadButtonImage = view.findViewById(R.id.uploadButtonImage);
        btnAddRestaurant = view.findViewById(R.id.btnAddRestaurant);
        imageView = view.findViewById(R.id.imageView);
        progressIndicator = view.findViewById(R.id.progress);

        restaurantRepo = new RestaurantRepo();
        storageReference = FirebaseStorage.getInstance().getReference();

        loadData(view);

        uploadButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
//
        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRestaurant();
//                Toast.makeText(getActivity(), "res_id : " + res_id, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadData(View view){
        restaurantRepo.Get(user_id, new BaseRepo.OnDataFetchedListener<Restaurant>() {
            @Override
            public void onDataFetched(Restaurant res) {
                editName.setText(res.getName());
                editAddress.setText(res.getAddress());
                rating = res.getRating();
                _name = res.getName();
                _address = res.getAddress();
                oldImageUrl = res.getUriImage();

                Glide.with(view.getContext())
                        .load(res.getUriImage())
                        .into(imageView);
            }
        });
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            imageUri = result.getData().getData();
                            Glide.with(requireContext()).load(imageUri).into(imageView);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//
    private void updateRestaurant() {
        String name = editName.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty()) {
           name = _name;
           address = _address;
        }

        if (imageUri != null) {
            uploadImageAndSaveRestaurant(imageUri, name, address, rating);
        } else {
            saveRestaurant(null, name, address, rating);
        }
    }
//
//
    private void uploadImageAndSaveRestaurant(Uri file, String name, String address, float rating) {
        StorageReference oldImageRef = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
        oldImageRef.delete();

        StorageReference ref = storageReference.child("image_restaurants/" + res_id);

        UploadTask uploadTask =  ref.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        saveRestaurant(uri.toString(), name, address, rating);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to get image URL: " + e.getMessage());
                    }
                })
                ;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to upload image: " + e.getMessage());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressIndicator.setMax(Math.toIntExact(snapshot.getTotalByteCount()));
                progressIndicator.setProgress(Math.toIntExact(snapshot.getBytesTransferred()));
            }
        })
        ;
    }

    private void saveRestaurant(String imageUrl, String name, String address, float rating) {
        Restaurant updatedRestaurant = new Restaurant(res_id, name, address, rating, imageUrl, user_id);
        restaurantRepo.Update(res_id, updatedRestaurant);
        Toast.makeText(getActivity(), "Restaurant updated successfully", Toast.LENGTH_SHORT).show();
    }
}