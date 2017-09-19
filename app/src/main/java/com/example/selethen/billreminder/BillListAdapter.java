package com.example.selethen.billreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillListAdapter extends BaseAdapter {
    ArrayList<Bill> billArrayList;
    LayoutInflater inflater;

    public BillListAdapter(Context context, ArrayList<Bill> billArrayList) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.billArrayList = billArrayList;
    }

    @Override
    public int getCount() {
        return billArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return billArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.bill_list_element, null);

        TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);

        Date date = billArrayList.get(position).getDate();

        textViewDate.setText(dateToString(date));
        textViewTitle.setText(billArrayList.get(position).getTitle());
        textViewPrice.setText(String.valueOf(billArrayList.get(position).getPrice())+" zł");

        return view;
    }

    private String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
}
