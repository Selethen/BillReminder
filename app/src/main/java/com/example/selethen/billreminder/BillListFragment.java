package com.example.selethen.billreminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BillListFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.bill_list_layout, container, false);

        ListView list = (ListView) view.findViewById(R.id.list);
        ArrayList<String> testList = new ArrayList<>();
        testList.add("1");
        testList.add("2");
        testList.add("3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, testList);

        list.setAdapter(adapter);

        return view;
    }
}
