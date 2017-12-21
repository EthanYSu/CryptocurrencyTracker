package com.example.ethan.cryptocurrencytracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by ethan on 12/20/2017.
 */


public class CustomAdapter extends ArrayAdapter<Coin> {
    private ArrayList<Coin> listCoins;
    Context contextm;

    public CustomAdapter(ArrayList<Coin> coins, Context context){
        super(context, R.layout.listview, coins);
        this.listCoins = coins;
        this.contextm = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

    }
}
