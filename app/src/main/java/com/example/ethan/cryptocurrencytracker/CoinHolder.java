package com.example.ethan.cryptocurrencytracker;

import android.view.View;
import android.widget.TextView;

/**
 * Created by ethan on 1/5/2018.
 */

public class CoinHolder implements View.OnClickListener{

    TextView nameText, symbolText, priceText, changeText;
    ItemClickListener itemClickListener;

    public CoinHolder(View v){
        nameText = v.findViewById(R.id.coin);
        symbolText = v.findViewById(R.id.coinInitial);
        priceText = v.findViewById(R.id.price);
        changeText = v.findViewById(R.id.change);

        v.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view);
    }

    public void setItemClickListener(ItemClickListener icl){
        this.itemClickListener = icl;
    }
}
