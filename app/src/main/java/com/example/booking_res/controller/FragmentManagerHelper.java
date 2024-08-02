package com.example.booking_res.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentManagerHelper {

    private static FragmentManagerHelper instance;
    private FragmentManager fragmentManager;
    private int containerId;

    private FragmentManagerHelper() {
    }

    public static synchronized FragmentManagerHelper getInstance() {
        if (instance == null) {
            instance = new FragmentManagerHelper();
        }
        return instance;
    }

    public void init(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerId, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        } else {
            throw new IllegalStateException("FragmentManagerHelper not initialized. Call init() first.");
        }
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        } else {
            throw new IllegalStateException("FragmentManagerHelper not initialized. Call init() first.");
        }
    }

    public void removeFragment(Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        } else {
            throw new IllegalStateException("FragmentManagerHelper not initialized. Call init() first.");
        }
    }
}
