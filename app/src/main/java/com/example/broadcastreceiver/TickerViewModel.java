package com.example.broadcastreceiver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;

public class TickerViewModel extends ViewModel {

    private MutableLiveData<String> current_url;

    public MutableLiveData<String> getUrl() {
        if (current_url == null) {
            current_url = new MutableLiveData<>();
            String newURL = "www.google.com";
            current_url.setValue(newURL);
        }
        return current_url;
    }

    public void setUrl(String url) {
        if (current_url == null) {
            current_url = new MutableLiveData<>();
        }
            current_url.setValue(url);
    }

}
