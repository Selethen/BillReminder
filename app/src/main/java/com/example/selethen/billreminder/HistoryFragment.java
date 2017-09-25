package com.example.selethen.billreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.history_layout, container, false);

        getActivity().setTitle("Historia");

        ListView historyListView = (ListView) view.findViewById(R.id.history_list_view);
        ArrayList<Bill> billArrayList = new ArrayList<>();

        billArrayList.addAll(loadBillsListHistory());

        BillListAdapter adapter = new BillListAdapter(getContext(), billArrayList);
        historyListView.setAdapter(adapter);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("chosenBill", i).apply();
                getFragmentManager().beginTransaction().replace(R.id.content_main, new BillHistoryDetailsFragment()).addToBackStack("BillHistoryDetailsFragment").commit();
            }
        });

        return view;
    }

    private List<Bill> loadBillsListHistory(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        List<Bill> bills = new ArrayList<>();

        String json = sharedPreferences.getString("billsHistory", "");
        if(json.equals("")) return bills;
        Type type = new TypeToken<List<Bill>>(){}.getType();
        bills = gson.fromJson(json, type);

        Collections.sort(bills, new Comparator<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        editor.putString("billsHistory", gson.toJson(bills));
        //put our obj to our base

        editor.apply();
        //apply changes

        return bills;
    }
}
