package ca.louisechan.tokyo2020.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity ma = (MainActivity)getActivity();
        int year = ma.getSelectedYear();
        int month = ma.getSelectedMonth();
        int dayOfMonth = ma.getSelectedDayOfMonth();

        // Create a date picker dialog object with initial value taken from the initial or last date picker setting
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);

        // Create a calendar object
        Calendar calendar = Calendar.getInstance();

        // Set minimum settable date for date picker (22-Jul-2020)
        // Note: Month starts at 0 so July will be equal to 6
        calendar.set(2020, 6, 22);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Set maximum settable date for date picker (09-Aug-2020)
        // Note: Month starts at 0 so August will be equal to 7
        calendar.set(2020, 7, 9);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        MainActivity activity = (MainActivity) getActivity();
        activity.setDateFromPicker(year, month, dayOfMonth);
    }
}
