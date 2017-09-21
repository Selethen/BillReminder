package com.example.selethen.billreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.history_layout, container, false);

        ListView historyListView = (ListView) view.findViewById(R.id.history_list_view);
        ArrayList<Bill> billArrayList = new ArrayList<>();

        billArrayList.addAll(loadBillsListHistory());

        BillListAdapter adapter = new BillListAdapter(getContext(), billArrayList);
        historyListView.setAdapter(adapter);

        return view;
    }

    private List<Bill> loadBillsListHistory(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        List<Bill> bills = new ArrayList<>();

        String json = sharedPreferences.getString("billsHistory", "");
        if(json.equals("")) return bills;
        Type type = new TypeToken<List<Bill>>(){}.getType();
        bills = gson.fromJson(json, type);

        return bills;
    }
}
