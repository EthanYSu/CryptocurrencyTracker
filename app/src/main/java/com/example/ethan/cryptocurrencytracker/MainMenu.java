package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainMenu extends AppCompatActivity {
    private OkHttpClient okHttpClient = new OkHttpClient();
    public static final String coinAPI = "https://api.coinmarketcap.com/v1/ticker/";
    private TextView txt;
    private ArrayList<String> coinNames = new ArrayList<>();
    ArrayList<Coin> coinList = new ArrayList<>();

    ListView coinListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);

        txt = (TextView) findViewById(R.id.txt);
        load();

    }

    private void load() {
        Request request = new Request.Builder().url(coinAPI).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainMenu.this, "Error during loading"+
                e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String coinInfo = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseCoinInfo(coinInfo);
                    }
                });
            }
        });
    }

    private void parseCoinInfo(String jsonData){
        try{
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONArray(jsonData);

            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String symbol = jsonObject.getString("symbol");
                String price = jsonObject.getString("price_usd");
                String change = jsonObject.getString("percent_change_24h");
                coinList.add(new Coin(name, symbol, price, change));
                coinNames.add(name);
            }
            coinListView = (ListView)findViewById(R.id.list);
            CustomAdapter cAdapter = new CustomAdapter(coinList, this);
            coinListView.setAdapter(cAdapter);
        }
        catch (Exception e){

        }
    }

}


