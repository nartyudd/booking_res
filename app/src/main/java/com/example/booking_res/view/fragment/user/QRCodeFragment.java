package com.example.booking_res.view.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;

public class QRCodeFragment extends Fragment {

    public QRCodeFragment() {
        // Required empty public constructor
    }
    public static QRCodeFragment newQR() {
        QRCodeFragment fragment = new QRCodeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_rcode, container, false);

        ImageView imageViewQRCode = view.findViewById(R.id.imageViewQRCode);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            // Close QR code fragment and return to home or previous screen

            FragmentManagerHelper.getInstance().replaceFragment(new HomeFragment(), false);
        });

        return view;
    }
}
