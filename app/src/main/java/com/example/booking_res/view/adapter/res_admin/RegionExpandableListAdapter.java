package com.example.booking_res.view.adapter.res_admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.booking_res.model.Region;
import com.example.booking_res.model.Table;

import java.util.List;

public class RegionExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Region> regions;

    public RegionExpandableListAdapter(Context context, List<Region> regions) {
        this.context = context;
        this.regions = regions;
    }

    @Override
    public int getGroupCount() {
        return regions.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return regions.get(i).getTables().size();
    }

    @Override
    public Object getGroup(int i) {
        return regions.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return regions.get(i).getTables().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String regionName = ((Region) getGroup(i)).getName();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
        }
        TextView item = (TextView) view.findViewById(android.R.id.text1);
        item.setText(regionName);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String tableName = ((Table) getChild(i, i1)).getName();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_expandable_list_item_2, null);
        }
        TextView item = (TextView) view.findViewById(android.R.id.text1);
        item.setText(tableName);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
