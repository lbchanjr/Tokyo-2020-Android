package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.louisechan.tokyo2020.Activities.LoginActivity;
import ca.louisechan.tokyo2020.Adapters.RatingAdapter;
import ca.louisechan.tokyo2020.Models.Rating;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingsFragment extends Fragment {
    private static final String TAG = "RatingsFragment";
    private View view;
    private List<Rating> ratings;

    public RatingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ratings, container, false);

        ratings = LoginActivity.dbConnection.ratingDao().getallRatings();

        HashMap<String, Double> newRatingHashMap = new HashMap<String, Double>();
        HashMap<String, Integer> newRatingsCntHashMap = new HashMap<String, Integer>();

        for (Rating r : ratings) {
            String name = r.getAttractionName();
            double rating = r.getRating();
            if(newRatingHashMap.containsKey(name)) {
                double ratingSum = newRatingHashMap.get(name);
                newRatingHashMap.put(name, ratingSum+rating);
                newRatingsCntHashMap.put(name, newRatingsCntHashMap.get(name)+1);
            }
            else {
                newRatingHashMap.put(name, rating);
                newRatingsCntHashMap.put(name, 1);
            }
        }

        final ArrayList<Rating> newRatings = new ArrayList<Rating>();

        for (String name: newRatingHashMap.keySet()) {
            double avgRating = newRatingHashMap.get(name) / newRatingsCntHashMap.get(name);
            newRatings.add(new Rating("", name, avgRating));
        }

        // Create adapter for the ratings listview
        RatingAdapter adapter = new RatingAdapter(getContext(), R.layout.rating_row, newRatings);

        ListView lv = (ListView) view.findViewById(R.id.listviewRatings);

        lv.setAdapter(adapter);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Replace with fragment showing details of the clicked attraction
//                Log.d(TAG, "onItemClick: Item # " + position + " was clicked.");
//
//                // Pass data to the details fragment
//                Rating a = newRatings.get(position);
//                Bundle b = new Bundle();
//                b.putString("name", a.getName());
//                b.putString("desc", a.getDetailedDesc());
//                b.putString("video", a.getYoutubeUrl());
//                b.putDouble("fee", a.getVisitFee());
//
//                AttractionDetailFragment attrDetail = new AttractionDetailFragment();
//                attrDetail.setArguments(b);
//
//                FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.frame_main_content, attrDetail);
//                ft.commit();
//
//            }
//        });

        return view;
    }

}
