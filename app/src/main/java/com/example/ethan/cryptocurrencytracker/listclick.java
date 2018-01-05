package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
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
    private static final String currentGraphInfo = "http://coincap.io/history/";
    LineGraphSeries<DataPoint> coinGraph;
    GraphView graphView;
    Button oneDay, sevenDay, thirtyDay, halfYear, oneYear;

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

        graphView = findViewById(R.id.graphView);
        oneDay = findViewById(R.id.oneDayButton);
        sevenDay = findViewById(R.id.sevenDayButton);
        thirtyDay = findViewById(R.id.thirtyDayButton);
        halfYear = findViewById(R.id.sixMonthButton);
        oneYear = findViewById(R.id.oneYearButton);
        loadCoin();
        loadGraph("1day");

        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.removeAllSeries();
                loadGraph("1day");
            }
        });
        sevenDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.removeAllSeries();
                loadGraph("7day");
            }
        });
        thirtyDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.removeAllSeries();
                loadGraph("30day");
            }
        });
        halfYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.removeAllSeries();
                loadGraph("180day");
            }
        });
        oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.removeAllSeries();
                loadGraph("365day");
            }
        });

    }

    private String addName(String s){
        Intent intent = getIntent();
        String currentCoinName = intent.getStringExtra("CoinName");
        return s + currentCoinName;
    }

    private String addSymbol(String s){
        Intent intent = getIntent();
        String coinSymbol = intent.getStringExtra("CoinSymbol");
        return currentGraphInfo + s + "/" + coinSymbol;
    }


    private void loadCoin() {
            Request coinRequest = new Request.Builder().url(addName(currentCoinInfo)).build();
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
                        }
                    });
                }
            });
    }

    private void loadGraph(final String s){
        Request graphRequest = new Request.Builder().url(addSymbol(s)).build();
        okHttpClient.newCall(graphRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(listclick.this, "Error in Loading" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String graphInfo = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseGraphInfo(graphInfo,s);
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
    private void parseGraphInfo(String graphInfo, String s){
        try{
            JSONObject jsonObject = new JSONObject(graphInfo);
            JSONArray jsonArray  = jsonObject.getJSONArray("price");
            //StringBuilder stringBuilder = new StringBuilder();
            ArrayList<GraphCoordinates> graphCoordinates = new ArrayList<>();
            for(int i =0; i < jsonArray.length(); i++){
                JSONArray innerArray = jsonArray.getJSONArray(i);
                Long x = innerArray.getLong(0);
                Double y = innerArray.getDouble(1);
                //stringBuilder.append(x+ "\t").append(y + "\n");
                graphCoordinates.add(new GraphCoordinates(x,y));
            }
            //TextView txt = findViewById(R.id.txt);
            //txt.setText(stringBuilder.toString());

            coinGraph = new LineGraphSeries<>();

            if(s.equals("1day")){
                for(int i = 0; i < graphCoordinates.size(); i++){
                    GraphCoordinates temp = graphCoordinates.get(i);
                    coinGraph.appendData(new DataPoint(i, temp.getY()), true, graphCoordinates.size());
                }
                graphView.addSeries(coinGraph);
            }
            if(s.equals("7day")){
                for(int i = 0; i < graphCoordinates.size(); i++){
                    GraphCoordinates temp = graphCoordinates.get(i);
                    coinGraph.appendData(new DataPoint(i, temp.getY()), true, graphCoordinates.size());
                }
                graphView.addSeries(coinGraph);

            }
            if(s.equals("30day")){
                for(int i = 0; i < graphCoordinates.size(); i++){
                    GraphCoordinates temp = graphCoordinates.get(i);
                    coinGraph.appendData(new DataPoint(i, temp.getY()), true, graphCoordinates.size());
                }
                graphView.addSeries(coinGraph);

            }
            if(s.equals("180day")){
                for(int i = 0; i < graphCoordinates.size(); i++){
                    GraphCoordinates temp = graphCoordinates.get(i);
                    coinGraph.appendData(new DataPoint(i, temp.getY()), true, graphCoordinates.size());
                }
                graphView.addSeries(coinGraph);

            }
            if(s.equals("365day")){
                for(int i = 0; i < graphCoordinates.size(); i++){
                    GraphCoordinates temp = graphCoordinates.get(i);
                    coinGraph.appendData(new DataPoint(i, temp.getY()), true, graphCoordinates.size());
                }
                graphView.addSeries(coinGraph);

            }

        }
        catch (Exception e){
        }
    }

}
