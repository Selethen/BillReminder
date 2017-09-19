package com.example.selethen.billreminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

public class BillListFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.bill_list_layout, container, false);

        ListView list = (ListView) view.findViewById(R.id.list);
        ArrayList<Bill> billArrayList = new ArrayList<>();


        try {
            billArrayList.add(new Bill("memy", "potrzebuje duzo memow ale opisu i tak narazie nie widac", 700.00d, "20-04-1889"));
            billArrayList.add(new Bill("sdgf", "sdgf", 70420.00d, "20-03-1999"));
            billArrayList.add(new Bill("sdgfsdgfsdgf", "g", 7.00d, "27-07-1999"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BillListAdapter adapter = new BillListAdapter(getContext(), billArrayList);
        list.setAdapter(adapter);

        FloatingActionButton plusButton = (FloatingActionButton) view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new AddingScreenFragment()).addToBackStack("AddingScreenFragment").commit();
            }
        });

        return view;
    }
}
