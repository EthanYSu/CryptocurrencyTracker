package com.example.ethan.cryptocurrencytracker;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by ethan on 1/5/2018.
 */

public class CoinFilter extends Filter {
    ArrayList<Coin> filterList;
    CustomAdapter adapter;

    public CoinFilter(ArrayList<Coin> filterList, CustomAdapter adapter){
        this.filterList = filterList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null || charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();

            ArrayList<Coin> filterCoins = new ArrayList<>();
            for(int i = 0; i < filterList.size(); i++){
                if(filterList.get(i).getCoinName().toUpperCase().contains(charSequence)){
                    filterCoins.add(filterList.get(i));
                }
            }
            results.count = filterCoins.size();
            results.values = filterCoins;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }
    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.coins = (ArrayList<Coin>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
