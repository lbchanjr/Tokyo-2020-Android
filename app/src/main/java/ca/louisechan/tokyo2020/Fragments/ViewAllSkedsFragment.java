package ca.louisechan.tokyo2020.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAllSkedsFragment extends Fragment {

    private View view;
    private WebView webView;

    public ViewAllSkedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_view_all_skeds, container, false);

        webView = (WebView) view.findViewById(R.id.webViewAllSkeds);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://tokyo2020.org/en/schedule/");

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
