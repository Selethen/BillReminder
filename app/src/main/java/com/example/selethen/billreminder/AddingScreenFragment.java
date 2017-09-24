package com.example.selethen.billreminder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddingScreenFragment extends Fragment {
    View view;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String date;
    private final String DATE_NULL_TOAST = "Proszę wybrać datę wydarzenia.";
    private final String NAME_NULL_TOAST = "Proszę wpisać nazwę wydarzenia.";
    private final String PRICE_NULL_TOAST = "Proszę wpisać cenę wydarzenia.";
    private final String FAILED_TOAST = "Tworzenie rachunku nie powiodło się.";
    private final String SUCCESS_TOAST = "Stworzono nowy rachunek";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adding_screen_layout, container, false);

        getActivity().setTitle("Dodaj nowy rachunek");

        date = "";
        final EditText billName = (EditText) view.findViewById(R.id.bill_name);
        final EditText billDescription = (EditText) view.findViewById(R.id.bill_description);
        final EditText billPrice = (EditText) view.findViewById(R.id.bill_price);

        Button acceptButton = (Button) view.findViewById(R.id.acceptButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String billNameString = billName.getText().toString();
                String billPriceString = billPrice.getText().toString();

                if(billName.getText().toString().equals("")){
                    Toast.makeText(getContext(), NAME_NULL_TOAST, Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(billPrice.getText().toString().equals("")){
                    Toast.makeText(getContext(), PRICE_NULL_TOAST, Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(date.equals("")){
                    Toast.makeText(getContext(), DATE_NULL_TOAST, Toast.LENGTH_SHORT).show();
                    return;
                }

                double price = Double.parseDouble(billPriceString);
                String billDescriptionString = billDescription.getText().toString();

                Bill bill;
                try {
                    bill = new Bill(billNameString, billDescriptionString, price, date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    getFragmentManager().popBackStack();
                    Toast.makeText(getContext(), FAILED_TOAST, Toast.LENGTH_SHORT).show();
                    return;
                }
                saveBillObject(bill);
                Toast.makeText(getContext(), SUCCESS_TOAST, Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back to previous fragment
                getFragmentManager().popBackStack();
            }
        });

        final Button datePickerButton = (Button) view.findViewById(R.id.bill_date_picker);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date = addZero(dayOfMonth) + "-" + addZero(month) + "-" + year;
                datePickerButton.setText(date);
            }
        };

        return view;
    }

    private String addZero(int number) {
        if(number<10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }

    private void saveBillObject(Bill bill){
        if(bill == null) return;

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //access to editor

        Gson gson = new Gson();

        List<Bill> bills;
        String json = sharedPreferences.getString("bills", "");
        if(json.equals("")){
            bills = new ArrayList<>();
        }
        else {
            Type type = new TypeToken<List<Bill>>() {
            }.getType();
            bills = gson.fromJson(json, type);
        }

        bills.add(bill);

        editor.putString("bills", gson.toJson(bills));
        //put our obj to our base

        editor.apply();
        //apply changes
    }
}
