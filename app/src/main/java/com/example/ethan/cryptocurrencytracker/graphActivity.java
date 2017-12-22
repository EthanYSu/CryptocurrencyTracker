package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class graphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Intent intent = getIntent();
        String symbol = intent.getStringExtra("CoinSymbol");
        TextView txt = findViewById(R.id.txt);
        txt.setText(symbol);
    }
}
