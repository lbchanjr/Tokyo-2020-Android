package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSkedByDayFragment extends Fragment {
    private static final String TAG = "ViewSkedByDayFragment";

    private View view;
    private WebView webView;
    private static final String SKED_BYDAY_BASEURL = "https://tokyo2020.org/en/schedule/";

    public ViewSkedByDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_view_sked_by_day, container, false);

        webView = (WebView) view.findViewById(R.id.webViewSkedsByDay);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        String url = SKED_BYDAY_BASEURL + "20200722-schedule";
        Log.d(TAG, "onCreateView: url to load is: " + url);
        webView.loadUrl(url);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Destroy webview when fragment is destroyed to avoid memory leak.
        if(webView != null) {
            webView.destroy();
        }
    }

}
