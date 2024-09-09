package com.example.booking_res.view.fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.booking_res.R;

public class InvoiceFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";
    private String table_id;

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

        TextView tvInvoice = view.findViewById(R.id.tvInvoice);

        // Hardcode dữ liệu hóa đơn cho table_id
        String invoiceDetails = "Invoice for Table: " + table_id + "\n\n";
        invoiceDetails += "1. Item 1 - $10\n";
        invoiceDetails += "2. Item 2 - $15\n";
        invoiceDetails += "3. Item 3 - $8\n";
        invoiceDetails += "----------------\n";
        invoiceDetails += "Total: $33";

        // Hiển thị dữ liệu hóa đơn lên TextView
        tvInvoice.setText(invoiceDetails);

        return view;
    }
}
