package com.example.selethen.billreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BillListFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.bill_list_layout, container, false);

        getActivity().setTitle("Twoje rachunki");

        ListView list = (ListView) view.findViewById(R.id.list);
        ArrayList<Bill> billArrayList = new ArrayList<>();

        billArrayList.addAll(loadBillsList());

        BillListAdapter adapter = new BillListAdapter(getContext(), billArrayList);
        list.setAdapter(adapter);

        FloatingActionButton plusButton = (FloatingActionButton) view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new AddingScreenFragment()).addToBackStack("AddingScreenFragment").commit();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("chosenBill", position).apply();
                getFragmentManager().beginTransaction().replace(R.id.content_main, new BillDetailsFragment()).addToBackStack("BillDetailsFragment").commit();
            }
        });

        return view;
    }

    private List<Bill> loadBillsList(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        List<Bill> bills = new ArrayList<>();

        String json = sharedPreferences.getString("bills", "");
        if(json.equals("")) return bills;
        Type type = new TypeToken<List<Bill>>(){}.getType();
        bills = gson.fromJson(json, type);

        Collections.sort(bills, new Comparator<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        editor.putString("bills", gson.toJson(bills));
        //put our obj to our base

        editor.apply();
        //apply changes


        return bills;
    }
}
