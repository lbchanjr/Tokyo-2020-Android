package ca.louisechan.tokyo2020.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {

    private View view;

    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view  = inflater.inflate(R.layout.fragment_reminder, container, false);

        Button btSelectDate = (Button) view.findViewById(R.id.buttonChangeDate);
        btSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new ReminderDatePickerFragment();
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "remindDatePicker");
            }
        });

        Button btSelectTime = (Button) view.findViewById(R.id.buttonChangeTime);
        btSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getActivity().getSupportFragmentManager(), "remindTimePicker");
            }
        });

        TextView txtReminderDateSelected = (TextView) view.findViewById(R.id.textReminderDateShown);
        TextView txtReminderTimeSelected = (TextView) view.findViewById(R.id.textReminderTimeShown);

        MainActivity ma = (MainActivity)getActivity();
        final int year = ma.getReminderYear();
        final int month = ma.getReminderMonth();
        final int dayOfMonth = ma.getReminderDay();
        final int hour = ma.getReminderHour();
        final int minute = ma.getReminderMinute();

        int hourAMPM = hour;
        boolean isAM = true;

        if(hourAMPM == 0) {
            hourAMPM = 12;
        }

        if(hourAMPM > 12) {
            hourAMPM -= 12;
            isAM = false;
        }

        String reminderTime = String.format("%d", hourAMPM) + ":" + String.format("%02d", minute) + (isAM ? " AM" : " PM");
        String reminderDate = Integer.toString(year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth);
        txtReminderDateSelected.setText(reminderDate);
        txtReminderTimeSelected.setText(reminderTime);

        Button buttonSetReminder = (Button) view.findViewById(R.id.buttonReminderSet);
        buttonSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth, hour, minute);
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", false);
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "Game reminder from Tokyo 2020 app");
                startActivity(intent);

                Toast.makeText(getContext(), "A reminder has now been set in the Calendar app.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
