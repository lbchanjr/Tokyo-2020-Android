package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import ca.louisechan.tokyo2020.Activities.LoginActivity;
import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.Models.Rating;
import ca.louisechan.tokyo2020.Models.Wishlist;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionDetailFragment extends Fragment {
    private static final String TAG = "AttractionDetailFragmen";
    String videoUrl;
    String attrName;
    String email;
    private View view;
    public AttractionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_attraction_detail, container, false);


        Bundle b = getArguments();
        //WebView webView = (WebView) view.findViewById(R.id.webAttractYTVideo);

        TextView txtName = (TextView) view.findViewById(R.id.textAttractName);
        TextView txtDesc = (TextView) view.findViewById(R.id.textAttractDetailDesc);
        TextView txtFee = (TextView) view.findViewById(R.id.textAttractPrice);

        email = ((MainActivity)getActivity()).getCurrentUser().getEmail();

        attrName = b.getString("name", "");
        txtName.setText(attrName);
        txtDesc.setText(b.getString("desc", ""));
        txtFee.setText(String.format("Visit fee: CA$%.2f", b.getDouble("fee", 0)));
        videoUrl = b.getString("video", "");

        // Check if rating needs to be preset
        List<Rating> ratings = LoginActivity.dbConnection.ratingDao().getRatingsByUserEmail(email);

        for (Rating r: ratings) {
            if(r.getAttractionName().equals(attrName)) {
                float rateScore = (float)r.getRating();
                RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
                ratingBar.setRating(rateScore);
            }
        }

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = videoUrl.substring(videoUrl.lastIndexOf('/')+1);
                youTubePlayer.loadVideo(videoId, 0f);
            }
        });

        Button btnBack = (Button) view.findViewById(R.id.buttonAttractBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_main_content, new AttractionListFragment());
                ft.commit();
            }
        });


        Button btnWishlist = (Button) view.findViewById(R.id.buttonWishlist);
        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Adding attraction to wishlist");

                Wishlist w = new Wishlist(email, attrName);
                int wishId = 0;
                boolean duplicateFound = false;
                List<Wishlist> wishes = LoginActivity.dbConnection.wishlistDao().getWishlistsByAttraction(attrName);
                for( Wishlist wish : wishes) {
                    if(wish.getUserEmail().equals(email)) {
                        wishId = wish.getId();
                        duplicateFound = true;
                    }
                }

                // Check if we need to update
                if(duplicateFound) {
                    w.setId(wishId);
                    //LoginActivity.dbConnection.wishlistDao().updateWishlist(w);
                    Toast.makeText(getContext(), "Attraction is already on your wishlist!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // User has not yet rated the attraction, add it to database.
                    LoginActivity.dbConnection.wishlistDao().addWishlist(w);
                    Toast.makeText(getContext(), "Attraction was added on your wishlist.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        RatingBar rb = (RatingBar) view.findViewById(R.id.ratingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Rating rate = new Rating(email, attrName, rating);

                int ratingId = 0;
                boolean duplicateFound = false;
                List<Rating> ratings = LoginActivity.dbConnection.ratingDao().getRatingsByAttraction(attrName);
                for( Rating r : ratings) {
                    if(r.getUserEmail().equals(email)) {
                        ratingId = r.getId();
                        duplicateFound = true;
                    }
                }

                // Check if we need to update
                if(duplicateFound) {
                    rate.setId(ratingId);
                    LoginActivity.dbConnection.ratingDao().updateRating(rate);
                    Toast.makeText(getContext(), "Your existing rating has been update.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // User has not yet rated the attraction, add it to database.
                    LoginActivity.dbConnection.ratingDao().addRating(rate);
                    Toast.makeText(getContext(), "A new rating has been added for this attraction.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
