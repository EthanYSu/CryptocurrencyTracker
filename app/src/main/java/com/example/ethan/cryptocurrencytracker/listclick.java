package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class listclick extends AppCompatActivity {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private TextView coinName, coinSymbol, coinPrice, change1Hour,
            change24Hour, change7Day, marketCap, volumeTotal, availableSupply, txt;
    private static final String currentCoinInfo = "https://api.coinmarketcap.com/v1/ticker/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listclick);
        coinName = findViewById(R.id.coinName);
        coinSymbol = findViewById(R.id.coinSymbol);
        coinPrice = findViewById(R.id.coinPrice);
        change1Hour = findViewById(R.id.changeOneHour);
        change24Hour = findViewById(R.id.change24Hour);
        change7Day = findViewById(R.id.change7Day);
        marketCap = findViewById(R.id.marketCap);
        volumeTotal = findViewById(R.id.volume);
        availableSupply = findViewById(R.id.availableSupply);

        load();
    }

    private String add(String s){
        Intent intent = getIntent();
        String currentCoinName = intent.getStringExtra("CoinName");
        return s + currentCoinName;
    }

    private void load() {

            Request coinRequest = new Request.Builder().url(add(currentCoinInfo)).build();

            okHttpClient.newCall(coinRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(listclick.this, "Error in loading" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String currentCoin = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parseCoinInfo(currentCoin);
                            temp(currentCoin);
                        }
                    });
                }
            });
    }

    private void parseCoinInfo(String currentCoin){
        try{
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONArray(currentCoin);

            jsonObject = jsonArray.getJSONObject(0);
            String name = jsonObject.getString("name");
            String symbol = jsonObject.getString("symbol");
            String price = jsonObject.getString("price_usd");
            String change1 = jsonObject.getString("percent_change_1h");
            String change24 = jsonObject.getString("percent_change_24h");
            String change7 = jsonObject.getString("percent_change_7d");
            String volume = jsonObject.getString("24h_volume_usd");
            String supply = jsonObject.getString("available_supply");
            String cap = jsonObject.getString("market_cap_usd");
//
//            int i = Integer.parseInt(volume);
//            int j = Integer.parseInt(supply);
//            int k = Integer.parseInt(cap);
//
//            String mCap = NumberFormat.getNumberInstance(Locale.US).format(k);
//            String volume24hr = NumberFormat.getNumberInstance(Locale.US).format(i);
//            String aSupply = NumberFormat.getNumberInstance(Locale.US).format(j);


            marketCap.setText(cap);
            volumeTotal.setText(volume);
            availableSupply.setText(supply);

            coinName.setText(name);
            coinSymbol.setText("(" + symbol + ")");
            coinPrice.setText("$" + price);
            double change1HourDouble = Double.parseDouble(change1);
            double change24HourDouble = Double.parseDouble(change24);
            double change7DayDouble = Double.parseDouble(change7);
            if(change1HourDouble >=0) {
                change1Hour.setText("+" + change1);
                change1Hour.setTextColor(Color.GREEN);
            }
            else{
                change1Hour.setText(change1);
                change1Hour.setTextColor(Color.RED);
            }
            if(change24HourDouble >= 0) {
                change24Hour.setText("+" + change24);
                change24Hour.setTextColor(Color.GREEN);
            }
            else{
                change24Hour.setText(change24);
                change24Hour.setTextColor(Color.RED);
            }
            if(change7DayDouble >= 0) {
                change7Day.setText("+" + change7);
                change7Day.setTextColor(Color.GREEN);
            }
            else{
                change7Day.setText(change7);
                change7Day.setTextColor(Color.RED);
            }

        }

        catch (Exception e){

        }
    }

    public void graphClick(View view) {
        Intent intent = getIntent();
        String currentSymbol = intent.getStringExtra("CoinSymbol");
        Intent graphIntent = new Intent(this, graphActivity.class);
        graphIntent.putExtra("CoinSymbol", currentSymbol);
        startActivity(graphIntent);
    }
}
