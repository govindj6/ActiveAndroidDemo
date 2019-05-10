package com.example.activeandroiddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeAdapter extends ArrayAdapter {
    ArrayList<Employee>arrayList;
    Context context;

    public EmployeeAdapter(Context context, ArrayList<Employee> arrayList) {
        super(context, R.layout.list,arrayList);
        this.arrayList = arrayList;
        this.context=context;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Employee e=arrayList.get(position);
        View v= LayoutInflater.from(context).inflate(R.layout.list,null);
        TextView tvname=v.findViewById(R.id.tvName);
        TextView tvMobile=v.findViewById(R.id.tvMobile);
        tvname.setText("Name: "+e.name);
        tvMobile.setText("Address: "+e.address);
        return v;
    }
}
