package com.example.selethen.billreminder;

import android.app.DatePickerDialog;
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
import android.widget.TextView;

import java.util.Calendar;

public class AddingScreenFragment extends Fragment {
    View view;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    //TO BE DELETED
    private TextView testTextView;
    //TO BE DELETED

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adding_screen_layout, container, false);

        //TO BE DELETED
        testTextView = (TextView) view.findViewById(R.id.testTextView);
        //TO BE DELETED

        Button datePickerButton = (Button) view.findViewById(R.id.datePickerButton);
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
                //TO BE DELETED
                String date = addZero(dayOfMonth) + "-" + addZero(month) + "-" + year;
                testTextView.setText(date);
                //TO BE DELETED
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
}
