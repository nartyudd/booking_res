package com.example.booking_res.Helper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking_res.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ToolBarHelper {
    public static void setToolbarTitle(AppCompatActivity activity, String title) {
        if (activity != null) {
            MaterialToolbar toolbar = activity.findViewById(R.id.topAppBarResAdmin); // Assumes toolbar has id "toolbar"
            if (toolbar != null) {
                toolbar.setTitle(title);
            }
        }
    }
}
