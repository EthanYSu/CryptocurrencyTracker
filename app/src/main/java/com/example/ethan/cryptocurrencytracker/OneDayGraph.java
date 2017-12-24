package com.example.ethan.cryptocurrencytracker;

/**
 * Created by ethan on 12/23/2017.
 */
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OneDayGraph extends Fragment{
    private TextView oneDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.graph1day, container, false);
        graphActivity gA = (graphActivity) getActivity();
        gA.load("1day");

        return rootView;
    }

}
