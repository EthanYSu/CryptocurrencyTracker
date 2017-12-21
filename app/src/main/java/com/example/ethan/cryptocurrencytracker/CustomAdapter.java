package com.example.ethan.cryptocurrencytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ethan on 12/20/2017.
 */


public class CustomAdapter extends ArrayAdapter<Coin> {
    private ArrayList<Coin> listCoins;
    Context contextm;

    public CustomAdapter(ArrayList<Coin> coins, Context context){
        super(context, 0, coins);
        this.listCoins = coins;
        this.contextm = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(contextm).inflate(R.layout.listview, parent, false);

        Coin currentCoin = listCoins.get(position);

        TextView nameText = (TextView) listItem.findViewById(R.id.coin);
        TextView priceText = (TextView) listItem.findViewById(R.id.price);
        TextView changeText = (TextView) listItem.findViewById(R.id.change);

        nameText.setText(currentCoin.getCoinName());
        priceText.setText(currentCoin.getCoinPrice());
        changeText.setText(currentCoin.getCoinChange());

        return listItem;

    }
}
