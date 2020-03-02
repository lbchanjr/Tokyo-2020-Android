package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.louisechan.tokyo2020.Activities.LoginActivity;
import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.Models.Wishlist;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {
    private View view;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        List<Wishlist> wishlists = LoginActivity.dbConnection.wishlistDao().getWishlistsByUserEmail(((MainActivity)getActivity()).getCurrentUser().getEmail());

        ArrayList<String> wishes = new ArrayList<String>();
        for (Wishlist w : wishlists) {
            wishes.add(w.getAttractionName());
        }

        // Create adapter for the ratings listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, wishes);
        ListView lv = (ListView) view.findViewById(R.id.listviewWishlists);

        lv.setAdapter(adapter);
        return view;
    }

}
