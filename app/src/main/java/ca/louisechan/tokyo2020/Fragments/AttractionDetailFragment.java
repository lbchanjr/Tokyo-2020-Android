package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionDetailFragment extends Fragment {

    String videoUrl;
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

        txtName.setText(b.getString("name", ""));
        txtDesc.setText(b.getString("desc", ""));
        txtFee.setText(String.format("Visit fee: CA$%.2f", b.getDouble("fee", 0)));
        videoUrl = b.getString("video", "");

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


        return view;
    }

}
