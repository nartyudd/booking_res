package com.example.booking_res.view.fragment.admin;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;
import com.example.booking_res.model.Category;
import com.example.booking_res.model.Restaurant;
import com.example.booking_res.repo.BaseRepo;
import com.example.booking_res.repo.admin.CategoryRepo;
import com.example.booking_res.utilities.GenID;
import com.example.booking_res.view.adapter.admin.ListCategoryAdapter;
import com.example.booking_res.view.fragment.res_admin.CreateRestaurantFragment;
import com.example.booking_res.view.fragment.res_admin.ListRegionFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryFragment extends Fragment {
    private static final String TAG = "CU_CATEGORY_FRAGMENT";

    public ListCategoryFragment() {
        // Required empty public constructor
    }

    public static ListCategoryFragment newInstance(){
        ListCategoryFragment fragment = new ListCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button add;
    private EditText name;

    private StorageReference storageReference;
    private TextView uploadImage;
    private ImageView imageView;
    private LinearProgressIndicator progressIndicator;
    private Uri imageUri;
    private RecyclerView recyclerList;
    private ListCategoryAdapter listCategoryAdapter;
    private CategoryRepo categoryRepo;
    private List<Category> _cates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_category, container, false);

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

    private void init(View view){
        add = view.findViewById(R.id.add);
        name = view.findViewById(R.id.name);
        uploadImage = view.findViewById(R.id.uploadImage);
        imageView = view.findViewById(R.id.imageView);
        storageReference = FirebaseStorage.getInstance().getReference();
        progressIndicator = view.findViewById(R.id.progressIndicator);
        categoryRepo = new CategoryRepo();
        recyclerList = view.findViewById(R.id.recyclerList);
        _cates = new ArrayList<>();

        listCategoryAdapter = new ListCategoryAdapter(_cates);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerList.setLayoutManager(gridLayoutManager);
        recyclerList.setAdapter(listCategoryAdapter);

        handleAdd();

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        loadData();

    }

    private void loadData(){
        categoryRepo.GetAll(new BaseRepo.OnDataFetchedListener<List<Category>>() {
            @Override
            public void onDataFetched(List<Category> cates) {
                _cates.addAll(cates);
                listCategoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean ValidateAddRestaurant(Uri file, String name){
        if(name == null || file == null || name.equals("") || file.equals("")){
            return false;
        }
        return true;
    }



    private void handleAdd(){

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = name.getText().toString().trim();

                if(ValidateAddRestaurant(imageUri, _name)) {
                    uploadImage(imageUri, _name);
                } else {
                    Toast.makeText(getActivity(), "Bạn phải điền loại nhà hàng.!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void uploadImage(Uri file, String _name) {

        StorageReference ref = storageReference.child("image_categories/" + GenID.genUUID());

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
                        Toast.makeText(getActivity(), "Bạn đã thêm thành công.!", Toast.LENGTH_SHORT).show();
                        Category cate = new Category(GenID.genUUID(),  _name, imageUrl);

                        _cates.add(cate);
                        listCategoryAdapter.notifyDataSetChanged();

                        categoryRepo.Add(cate);
                        name.setText(null);
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