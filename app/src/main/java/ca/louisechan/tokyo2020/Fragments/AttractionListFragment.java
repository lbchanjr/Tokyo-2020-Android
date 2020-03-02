package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ca.louisechan.tokyo2020.Activities.LoginActivity;
import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.Adapters.AttractionAdapter;
import ca.louisechan.tokyo2020.Models.Attraction;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionListFragment extends Fragment {

    private static final String TAG = "AttractionListFragment";
    private View view;
    private List<Attraction> attractions;

    public AttractionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_attraction_list, container, false);

        attractions = LoginActivity.dbConnection.attractionDao().getallAttractions();

        // Create adapter for the attractions listview
        AttractionAdapter adapter = new AttractionAdapter(getContext(), R.layout.attraction_row, attractions);

        ListView lv = (ListView) view.findViewById(R.id.listAttractions);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Replace with fragment showing details of the clicked attraction
                Log.d(TAG, "onItemClick: Item # " + position + " was clicked.");

                // Pass data to the details fragment
                Attraction a = attractions.get(position);
                Bundle b = new Bundle();
                b.putString("name", a.getName());
                b.putString("desc", a.getDetailedDesc());
                b.putString("video", a.getYoutubeUrl());
                b.putDouble("fee", a.getVisitFee());

                AttractionDetailFragment attrDetail = new AttractionDetailFragment();
                attrDetail.setArguments(b);

                FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_main_content, attrDetail);
                ft.commit();

            }
        });

        return view;
    }

}
