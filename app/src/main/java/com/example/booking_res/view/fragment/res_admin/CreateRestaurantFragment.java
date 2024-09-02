package com.example.booking_res.view.fragment.res_admin;

import static android.app.Activity.RESULT_OK;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.CategoryRepo;
import com.example.booking_res.repo.admin.RestaurantRepo;
import com.example.booking_res.utilities.GenID;
import com.example.booking_res.viewmodels.Item;
import com.example.booking_res.viewmodels.ItemCate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRestaurantFragment extends Fragment {

    private static final String TAG = "CU_RESTAURANT_FRAGMENT";

    // variable fragment
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "address";
    private static final String ARG_PARAM3 = "rating";
    private static final String ARG_PARAM4 = "uriImage";
    private static final String ARG_PARAM5 = "userId";

    private String userId;

    // variable handle
    private StorageReference storageReference;
    private Button uploadImage;
    private Uri imageUri;
    private ImageView imageView;
    private LinearProgressIndicator progressIndicator;
    private EditText editName, editAddress;

    private RestaurantRepo resRepo;
    private Button btnAddRestaurant;
    private RatingBar ratingBarRestaurant;

    private List<ItemCate> _itemCates;
    private ArrayAdapter<ItemCate> adapter;
    private Spinner spinner;
    private String cate_id;
    private CategoryRepo categoryRepo;

    public CreateRestaurantFragment() {
        // Required empty public constructor
    }

    public static CreateRestaurantFragment newInstance(String userId) {
        CreateRestaurantFragment fragment = new CreateRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM5, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            userId = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_restaurant, container, false);

        init(view);

        return view;
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    uploadImage.setEnabled(true);
                    imageUri = result.getData().getData();
                    Glide.with(getActivity().getApplicationContext()).load(imageUri).into(imageView);
                }
            } else {
                Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });


    private void init(View view) {
        uploadImage = view.findViewById(R.id.uploadButtonImage);
        imageView = view.findViewById(R.id.imageViewRestaurant);
        progressIndicator = view.findViewById(R.id.progress);
        btnAddRestaurant = view.findViewById(R.id.btnAddRestaurant);
        editName = view.findViewById(R.id.editName);
        editAddress = view.findViewById(R.id.editAddress);
        ratingBarRestaurant = view.findViewById(R.id.ratingBarRestaurant);
        storageReference = FirebaseStorage.getInstance().getReference();
        spinner = view.findViewById(R.id.spinner);
        _itemCates = new ArrayList<>();
        categoryRepo = new CategoryRepo();
        resRepo = new RestaurantRepo();

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRestaurant();
            }
        });


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, _itemCates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ItemCate selectedItem = (ItemCate) adapterView.getItemAtPosition(i);
                cate_id = selectedItem.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadData();
    }

    private void loadData(){
        categoryRepo.GetAllForRes(new BaseRepo.OnDataFetchedListener<List<ItemCate>>() {
            @Override
            public void onDataFetched(List<ItemCate> itemCates) {
                _itemCates.addAll(itemCates);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void AddRestaurant() {
        String name = editName.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        float rating = ratingBarRestaurant.getRating();

        if(ValidateAddRestaurant(imageUri, name, address))
            uploadImage(imageUri, name, address, rating);
        else{
            Toast.makeText(getActivity(), "Bạn phải điền đầy đủ các trường cần thiết.!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ValidateAddRestaurant(Uri file, String name, String address){
        if(name == null || address == null || file == null
                || name.equals("") || address.equals("") || file.equals("")){
            return false;
        }
        return true;
    }

    private void uploadImage(Uri file, String name, String address, float rating) {

        StorageReference ref = storageReference.child("image_restaurants/" + GenID.genUUID());

        UploadTask uploadTask = ref.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(TAG, "Upload image restaurant success.");

                // Lấy đường dẫn URL của tệp đã tải lên
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        Restaurant res = new Restaurant(GenID.genUUID(), name, address, rating, imageUrl, userId, cate_id);
                        resRepo.Add(res);
                        Toast.makeText(getActivity(), "Bạn đã thêm thành công.!", Toast.LENGTH_SHORT).show();
                        getActivity().recreate();
                        FragmentManagerHelper.getInstance().removeFragment(CreateRestaurantFragment.this);
                        FragmentManagerHelper.getInstance().addFragment(ListRegionFragment.newInstance(res.getUuid()), false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to get image URL: " + e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to upload image restaurant: " + e.getMessage());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                progressIndicator.setMax(Math.toIntExact(taskSnapshot.getTotalByteCount()));
                progressIndicator.setProgress(Math.toIntExact(taskSnapshot.getBytesTransferred()));
            }
        });
    }
}