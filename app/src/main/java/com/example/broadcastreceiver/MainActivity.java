package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

// TODO: IF THE PROJECT STARTS ACTING UP = WHEN IN DOUBT, INVALIDATE CACHES AND RESTART
// TODO: Fix the latest ticker from SMS being added to the top of the list whenever exiting the app

public class MainActivity extends AppCompatActivity {

    private TickerViewModel ticker_VM;
    public static final String PREF_NAME = "Tickers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticker_VM = new ViewModelProvider(this).get(TickerViewModel.class);

        SharedPreferences tickerPref = getSharedPreferences(PREF_NAME, 0);
        Set<String> saved = tickerPref.getStringSet("ticks", null);
        if (saved != null) {
            LinkedList<String> ticks = new LinkedList<>(saved);
            ticker_VM.setTickers(ticks);
        }

        Intent act_intent = getIntent();
        String message = act_intent.getStringExtra("sms");

        //ticker_VM.setTickers(ticker_VM.getTickers().getValue());
        //ticker_VM.addTickers(message);
        //Toast.makeText(------, message, Toast.LENGTH_LONG).show();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS)
        != PackageManager.PERMISSION_GRANTED) {
            String[] permission = new String[]{Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permission, 101);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("sms");
        if (message == null || message.isEmpty() || message.length() > 4) {
            Toast.makeText(getApplicationContext(), "Invalid length for ticker, try again!", Toast.LENGTH_LONG).show();
        } else if (!checkValidTicker(message)) {
            Toast.makeText(getApplicationContext(), "Invalid ticker, try again!", Toast.LENGTH_LONG).show();
        } else {
            ticker_VM.addTickers(message.toUpperCase());
        }
        savePrefs();
        //Toast.makeText(--------, message, Toast.LENGTH_LONG).show();

    }

    private boolean checkValidTicker(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public void savePrefs() {
        SharedPreferences tickerPref = getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = tickerPref.edit();
        Set<String> set = new HashSet<>(ticker_VM.getTickers().getValue());
        Toast.makeText(getApplicationContext(), "Saving tickers...", Toast.LENGTH_SHORT).show();
        editor.putStringSet("ticks", set);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePrefs();
    }
    @Override
    protected void onPause() {
        super.onPause();
        savePrefs();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePrefs();
    }
}