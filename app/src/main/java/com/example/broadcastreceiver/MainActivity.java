package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TickerViewModel ticker_VM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticker_VM = new ViewModelProvider(this).get(TickerViewModel.class);

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
        }
        else {
            ticker_VM.addTickers(message.toUpperCase());
        }
        //Toast.makeText(--------, message, Toast.LENGTH_LONG).show();

    }
}