package ca.louisechan.tokyo2020.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import ca.louisechan.tokyo2020.Models.Rating;
import ca.louisechan.tokyo2020.R;

public class RatingAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    ArrayList<Rating> ratings;

    public RatingAdapter(Context context, int resource, ArrayList<Rating> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        ratings = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data to populate in custom view
        String name = ratings.get(position).getAttractionName();
        double rating = ratings.get(position).getRating();

        // Create a layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        // Fetch text views
        TextView txtRateName = (TextView) convertView.findViewById(R.id.textRowRatingName);
        TextView txtRateScore = (TextView) convertView.findViewById(R.id.textRateRowScore);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rateRowRatingBar);

        txtRateName.setText(name);
        txtRateScore.setText(String.format("Rating: %.1f", rating));
        ratingBar.setRating((float) rating);

        // Return the view with updated items
        return convertView;


    }
}
