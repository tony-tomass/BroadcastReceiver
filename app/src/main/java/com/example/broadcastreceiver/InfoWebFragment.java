package com.example.broadcastreceiver;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class InfoWebFragment extends Fragment {

    WebView info_web_wv;
    private TickerViewModel ticker_VM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag_root = inflater.inflate(R.layout.fragment_infoweb, container, false);
        info_web_wv = frag_root.findViewById(R.id.info_web_WV);
        info_web_wv.getSettings().setJavaScriptEnabled(true);
        ticker_VM = new ViewModelProvider(getActivity()).get(TickerViewModel.class);
        ticker_VM.getUrl().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                info_web_wv.loadUrl(ticker_VM.getUrl().getValue());
            }
        });
        return frag_root;
    }
}