package com.example.selethen.billreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BillHistoryDetailsFragment extends Fragment{
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.bill_history_details_layout, container, false);

        getActivity().setTitle("Szczegóły");

        TextView billName = (TextView) view.findViewById(R.id.bill_details_name);
        TextView billDescription = (TextView) view.findViewById(R.id.bill_details_description);
        TextView billPrice = (TextView) view.findViewById(R.id.bill_details_price);
        TextView billDate = (TextView) view.findViewById(R.id.bill_details_date);

        Bill bill = loadBill();
        if(bill == null){
            Toast.makeText(getContext(), "Nie udało się wczytać rachunku", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
            return view;
        }

        billName.setText(bill.getTitle());
        billDescription.setText(bill.getDescription());
        billPrice.setText(String.valueOf(bill.getPrice()) + " zł");
        billDate.setText(bill.getDate());

        Button backButton = (Button) view.findViewById(R.id.bill_details_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        final Button deleteButton = (Button) view.findViewById(R.id.bill_details_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBill();
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void deleteBill(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int pos = sharedPreferences.getInt("chosenBill", -1);
        if(pos != -1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Gson gson = new Gson();

            String json = sharedPreferences.getString("billsHistory", "");
            Type type = new TypeToken<List<Bill>>(){}.getType();
            if(json.equals("")) return;
            List<Bill> bills = gson.fromJson(json, type);

            bills.remove(pos);

            editor.putString("billsHistory", gson.toJson(bills));

            editor.apply();
        }
    }


    private Bill loadBill(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("billsHistory", "");
        if(json.equals("")) return null;
        Type type = new TypeToken<List<Bill>>(){}.getType();
        List<Bill> bills = gson.fromJson(json, type);

        int pos = sharedPreferences.getInt("chosenBill", -1);
        if(pos == -1) return null;
        return bills.get(pos);
    }
}