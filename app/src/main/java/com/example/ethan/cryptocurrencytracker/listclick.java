package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class listclick extends AppCompatActivity {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String url;
    private TextView coinSymbol, coinPrice, change1Hour, change24Hour, change7Day,txt;
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
        String coinName = intent.getStringExtra("CoinName").toLowerCase();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("api.coinmarketcap.com")
                .appendPath("v1")
                .appendPath("ticker")
                .appendPath(coinName);
        String temp = builder.build().toString();
        url = temp;
        txt = (TextView) findViewById(R.id.txt);
       // txt.setText(temp);

        load();
    }
    private void load() {
        Intent intent = getIntent();
        String coinName = intent.getStringExtra("CoinName").toLowerCase();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("api.coinmarketcap.com")
                .appendPath("v1")
                .appendPath("ticker")
                .appendPath(coinName);
        String temp = builder.build().toString();
        final String tempurl = "https://api.coinmarketcap.com/v1/ticker/" + coinName+ "/";
        url = tempurl;

        Request request = new Request.Builder().url(tempurl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(listclick.this, "Error during loading"+
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String fullCoinInfo = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseCoinInfo(fullCoinInfo);
                    }
                });
            }
        });
    }

    private void parseCoinInfo(String body){
        try{
            JSONObject jsonObject = new JSONObject(body);
            String symbol = jsonObject.getString("symbol");
            String price = jsonObject.getString("price_usd");
            String change1Hr = jsonObject.getString("percent_change_1h");
            String change24Hr = jsonObject.getString("percent_change_24h");
            String change7D = jsonObject.getString("percent_change_7d");

            txt.setText("Hello");
            coinSymbol.setText(symbol);
            coinPrice.setText(price);
            change1Hour.setText(change1Hr);
            change24Hour.setText(change24Hr);
            change7Day.setText(change7D);
        }
        catch (Exception e){

        }
    }
}
