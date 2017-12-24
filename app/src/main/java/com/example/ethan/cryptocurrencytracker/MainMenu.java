package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
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
    EditText editText;
    ListView coinListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);

        txt = (TextView) findViewById(R.id.coinName);
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

            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String symbol = jsonObject.getString("symbol");
                String price = jsonObject.getString("price_usd");
                String change = jsonObject.getString("percent_change_24h");
                coinList.add(new Coin(id, name, symbol, price, change));
                coinNames.add(name);
            }
            coinListView = (ListView)findViewById(R.id.list);
            CustomAdapter cAdapter = new CustomAdapter(coinList, this);
            coinListView.setAdapter(cAdapter);
            editText = findViewById(R.id.coinSearch);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            coinListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Coin coinTemp = coinList.get(i);
                    String nameTemp = coinTemp.getId();
                    String symbolTemp = coinTemp.getSymbolName();
                    Intent intent = new Intent(MainMenu.this, listclick.class);
                    intent.putExtra("CoinName", nameTemp);
                    intent.putExtra("CoinSymbol", symbolTemp);
                    startActivity(intent);

                }

            });
        }
        catch (Exception e){

        }
    }

    public void coinSearch(View view) {
    }
}


