package com.example.broadcastreceiver;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class TickerListFragment extends Fragment {

    private TickerViewModel ticker_VM;
    ListView ticker_lv;

    AdapterView.OnItemClickListener item_click_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ticker_VM.setUrl("www.google.com");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View frag_root = inflater.inflate(R.layout.fragment_tickerlist, container, false);
        ticker_lv = frag_root.findViewById(R.id.ticker_LV);
        return frag_root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ticker_VM = new ViewModelProvider(getActivity()).get(TickerViewModel.class);
    }
}