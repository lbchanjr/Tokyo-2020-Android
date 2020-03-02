package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingsFragment extends Fragment {

    private View view;

    public RatingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ratings, container, false);
        return view;
    }

}
