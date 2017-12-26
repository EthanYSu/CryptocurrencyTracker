package com.example.ethan.cryptocurrencytracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ethan on 12/20/2017.
 */


public class CustomAdapter extends ArrayAdapter<Coin> implements Filterable {
    private ArrayList<Coin> listCoins;
    private Context contextm;
    private coinFilter cFilter;

    public CustomAdapter(ArrayList<Coin> coins, Context context){
        super(context, 0, coins);
        this.listCoins = coins;
        this.contextm = context;
    }

    public int getCount(){
        return listCoins != null ? listCoins.size() : 0;
    }
    public Coin getItem(int position){
        return null;
    }
    public long getItemId(int position){
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(contextm).inflate(R.layout.listview, parent, false);

        Coin currentCoin = listCoins.get(position);

        TextView nameText = listItem.findViewById(R.id.coin);
        TextView symbolText = listItem.findViewById(R.id.coinInitial);
        TextView priceText = listItem.findViewById(R.id.price);
        TextView changeText = listItem.findViewById(R.id.change);

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
    private class coinFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence == null || charSequence.length() == 0){
                results.values = listCoins;
                results.values = listCoins.size();
            }
            else{
                ArrayList<Coin> coinData = new ArrayList<>();
                for(Coin c : listCoins){
                    if(c.getCoinName().toUpperCase().contains(charSequence.toString().toUpperCase())){
                        coinData.add(c);
                    }
                }
                results.values = coinData;
                results.count = coinData.size();
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listCoins = (ArrayList<Coin>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public Filter getFilter(){
        if(cFilter == null){
            cFilter = new coinFilter();
        }
        return cFilter;
    }

}
