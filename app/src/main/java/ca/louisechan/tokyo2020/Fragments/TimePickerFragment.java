package ca.louisechan.tokyo2020.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity m = (MainActivity) getActivity();
        return new TimePickerDialog(getActivity(), this, m.getReminderHour(), m.getReminderMinute(), false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        MainActivity activity = (MainActivity) getActivity();
        activity.setReminderTimeFromPicker(hourOfDay, minute);

    }
}
