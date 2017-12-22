package com.example.ethan.cryptocurrencytracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ethan on 12/20/2017.
 */


public class CustomAdapter extends ArrayAdapter<Coin> implements View.OnClickListener {
    private ArrayList<Coin> listCoins;
    private Context contextm;

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
        TextView symbolText = (TextView) listItem.findViewById(R.id.coinInitial);
        TextView priceText = (TextView) listItem.findViewById(R.id.price);
        TextView changeText = (TextView) listItem.findViewById(R.id.change);

        nameText.setText(currentCoin.getCoinName());
        symbolText.setText("(" + currentCoin.getSymbolName() + ")");
        priceText.setText("$" + currentCoin.getCoinPrice());
        double checkPlusMinus = Double.parseDouble(currentCoin.getCoinChange());
        if(checkPlusMinus >= 0.00){
            changeText.setText("+" + currentCoin.getCoinChange());
            changeText.setTextColor(Color.GREEN);
        }
        else{
            changeText.setText(currentCoin.getCoinChange());
            changeText.setTextColor(Color.RED);
        }
        return listItem;

    }

    @Override
    public void onClick(View view) {
        int currentItem = (Integer) view.getTag();
        Object object = getItem(currentItem);
        Coin coin = (Coin)object;

        switch(view.getId()){

        }
    }
}
