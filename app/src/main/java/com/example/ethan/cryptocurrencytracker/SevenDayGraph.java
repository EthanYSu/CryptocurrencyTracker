package com.example.ethan.cryptocurrencytracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ethan on 12/23/2017.
 */

public class SevenDayGraph extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.graph7day, container, false);
        graphActivity gA = (graphActivity) getActivity();
        gA.load("7day");
        return rootView;
    }
    @Override
    public void onStart() {
        graphActivity gA = (graphActivity) getActivity();
        gA.load("7day");
        super.onStart();
    }

    public void onResume() {
        graphActivity gA = (graphActivity) getActivity();
        gA.load("7day");
        super.onResume();
    }
}