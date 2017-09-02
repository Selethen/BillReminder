package com.example.selethen.billreminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_layout, container, false);

        Button buttonBills = (Button) view.findViewById(R.id.bills);
        buttonBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new BillListFragment()).addToBackStack("BillListFragment").commit();
            }
        });

        Button buttonHistory = (Button) view.findViewById(R.id.history);
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new HistoryFragment()).addToBackStack("HistoryFragment").commit();
            }
        });

        return view;
    }
}
