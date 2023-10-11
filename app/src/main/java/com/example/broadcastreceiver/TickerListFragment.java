package com.example.broadcastreceiver;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;


public class TickerListFragment extends Fragment {

    private TickerViewModel ticker_VM;
    ListView ticker_lv;

    AdapterView.OnItemClickListener ticker_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ticker_VM.setUrl(ticker_lv.getItemAtPosition(i).toString());
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

        /*
        LinkedList<String> entries = new LinkedList<>();
        entries.add("BAC");
        entries.add("AAPL");
        entries.add("DIS");
        ArrayAdapter<String> entries_list = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, entries);
        ticker_lv.setAdapter(entries_list);
        ticker_lv.setOnItemClickListener(ticker_listener);

        //Moved down to onActivityCreated OnChanged
         */
        return frag_root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ticker_VM = new ViewModelProvider(getActivity()).get(TickerViewModel.class);
        ticker_lv.setOnItemClickListener(ticker_listener);
        ticker_VM.getTickers().observe(getViewLifecycleOwner(), new Observer<LinkedList<String>>() {
            @Override
            public void onChanged(LinkedList<String> tickers) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, tickers);
                ticker_lv.setAdapter(adapter);
            }
        });
    }
}