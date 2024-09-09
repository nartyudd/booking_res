package com.example.booking_res.view.fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_res.Helper.FragmentManagerHelper;
import com.example.booking_res.R;

public class InvoiceFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";
    private static final String ARG_TABLE_NAME = "table_name";
    private String table_id;
    private String table_name;

    public InvoiceFragment() {
        // Required empty public constructor
    }

    public static InvoiceFragment newInstance(String table_id) {
        InvoiceFragment fragment = new InvoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TABLE_ID, table_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            table_id = getArguments().getString(ARG_TABLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);

        TextView tvInvoiceItems = view.findViewById(R.id.tvInvoiceItems);
        Button btnPay = view.findViewById(R.id.btnPay);
        // Hardcode dữ liệu hóa đơn cho table_id
        String invoiceDetails = "Loại bàn: Bàn đôi\n";
        invoiceDetails += "khách hàng: user@gmail.com \n";
        invoiceDetails += "Khu vực: Tầng 1 \n";
        invoiceDetails += "Số lượng: 1 \n";
        invoiceDetails += "----------------\n";
        invoiceDetails += "Tổng tiền: 800.000 VND";

        tvInvoiceItems.setText(invoiceDetails);

        btnPay.setOnClickListener(v -> {
            FragmentManagerHelper.getInstance().replaceFragment(QRCodeFragment.newQR(), false);
            Toast.makeText(getActivity(), "Thanh toán thành công. Cảm ơn quý khách!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

}
