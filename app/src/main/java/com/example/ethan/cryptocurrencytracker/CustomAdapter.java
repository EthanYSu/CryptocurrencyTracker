package com.example.ethan.cryptocurrencytracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ethan on 12/20/2017.
 */

public class CustomAdapter extends BaseAdapter implements Filterable{

    Context context;
    ArrayList<Coin> coins;
    LayoutInflater inflater;

    ArrayList<Coin> filterList;
    CoinFilter filter;

    public CustomAdapter(Context context, ArrayList<Coin> coins){
        this.coins = coins;
        this.context = context;
    }

    @Override
    public int getCount() {
        return coins.size();
    }

    @Override
    public Object getItem(int i) {
        return coins.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = inflater.inflate(R.layout.listview,null);
        }
        CoinHolder holder = new CoinHolder(view);

        holder.nameText.setText(coins.get(i).getCoinName());
        holder.symbolText.setText("("+coins.get(i).getSymbolName());
        holder.priceText.setText("$" + coins.get(i).getCoinPrice());
        double checkPlusMinus = Double.parseDouble(coins.get(i).getCoinChange());
        if(checkPlusMinus >= 0.00){
            holder.changeText.setText("+" + coins.get(i).getCoinChange());
            holder.changeText.setTextColor(Color.GREEN);
        }
        else{
            holder.changeText.setText(coins.get(i).getCoinChange());
            holder.changeText.setTextColor(Color.RED);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter = new CoinFilter(filterList,this);
        }
        return filter;
    }
}