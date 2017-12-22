package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class listclick extends AppCompatActivity {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private TextView coinSymbol, coinPrice, change1Hour, change24Hour, change7Day,txt;
    private static final String currentCoinInfo = "https://api.coinmarketcap.com/v1/ticker/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listclick);
        coinSymbol = (TextView)findViewById(R.id.coinSymbol);
        coinPrice = (TextView)findViewById(R.id.coinPrice);
        change1Hour = (TextView)findViewById(R.id.changeOneHour);
        change24Hour = (TextView)findViewById(R.id.change24Hour);
        change7Day = (TextView)findViewById(R.id.change7Day);

        Intent intent = getIntent();
        String currentCoinName = intent.getStringExtra("CoinName");

        txt = findViewById(R.id.txt);
        TextView coinName = findViewById(R.id.coinName);
        coinName.setText(currentCoinName);

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

    private void temp(String x){
        txt.setText(x);
    }
    private void parseCoinInfo(String currentCoin){
        try{
            Intent intent = getIntent();
            String currentCoinName = intent.getStringExtra("CoinName");
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONArray(currentCoin);

            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                if( name.equals(currentCoinName)) {
                    String symbol = jsonObject.getString("symbol");
                    String price = jsonObject.getString("price_usd");
                    String change1 = jsonObject.getString("percent_change_1h");
                    String change24 = jsonObject.getString("percent_change_24h");
                    String change7 = jsonObject.getString("percent_change_7d");
                    coinSymbol.setText(symbol);
                    coinPrice.setText(price);
                    change1Hour.setText(change1);
                    change24Hour.setText(change24);
                    change7Day.setText(change7);
                }

            }
        }

        catch (Exception e){

        }
    }
}
