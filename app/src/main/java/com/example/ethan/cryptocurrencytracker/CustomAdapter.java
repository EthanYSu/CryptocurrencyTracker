package com.example.ethan.cryptocurrencytracker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ethan on 12/20/2017.
 */

public class CustomAdapter extends ArrayAdapter<Coin> implements Filterable {
    private ArrayList<Coin> listCoins, filterList;
    private Context contextm;
    private coinFilter cFilter;

    public CustomAdapter(ArrayList<Coin> coins, Context context){
        super(context, 0, coins);
        this.filterList = coins;
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
    public View getView(final int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(contextm).inflate(R.layout.listview, parent, false);

        final Coin currentCoin = listCoins.get(position);

        TextView nameText = listItem.findViewById(R.id.coin);
        TextView symbolText = listItem.findViewById(R.id.coinInitial);
        TextView priceText = listItem.findViewById(R.id.price);
        TextView changeText = listItem.findViewById(R.id.change);
        ImageView imageView = listItem.findViewById(R.id.imageView);
        String imageName = currentCoin.getSymbolName().toLowerCase();
        Resources res = contextm.getResources();
        int id = res.getIdentifier(imageName,"drawable", contextm.getPackageName());
        imageView.setImageResource(id);

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
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contextm, listclick.class);
                intent.putExtra("CoinName", currentCoin.getId());
                intent.putExtra("CoinSymbol", currentCoin.getSymbolName());
                contextm.startActivity(intent);
            }
        });
        return listItem;

    }

    public static int getImageID(Context context, String id){
        return context.getResources().getIdentifier("drawable/"+ id,null, context.getPackageName());
    }
    private class coinFilter extends Filter {
        ArrayList<Coin> filterList;
        CustomAdapter adapter;

        public coinFilter(ArrayList<Coin> filterList, CustomAdapter adapter){
            this.filterList = filterList;
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence != null || charSequence.length() > 0){
                ArrayList<Coin> coinData = new ArrayList<>();

                for(int i = 0; i < filterList.size(); i++){
                    charSequence = charSequence.toString().toUpperCase();
                    if(filterList.get(i).getCoinName().toUpperCase().contains(charSequence)||
                            filterList.get(i).getSymbolName().toUpperCase().contains(charSequence)){
                        coinData.add(filterList.get(i));
                    }
                }
                results.count = coinData.size();
                results.values = coinData;
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
            adapter.listCoins = (ArrayList<Coin>) filterResults.values;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter(){
        if(cFilter == null){
            cFilter = new coinFilter(filterList, this);
        }
        return cFilter;
    }

}