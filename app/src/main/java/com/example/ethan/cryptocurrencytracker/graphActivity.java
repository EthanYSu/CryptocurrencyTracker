package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class graphActivity extends AppCompatActivity {
    OkHttpClient okHttpClient = new OkHttpClient();
    private TextView txt;
    public static final String graphURL =  "http://coincap.io/history/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        txt = findViewById(R.id.txt);

        load();
    }

    private String add(String s){
        Intent intent = getIntent();
        String currentSymbol = intent.getStringExtra("CoinSymbol");
        return s + "1day/" + currentSymbol;
    }

   private void load(){
       Request graphRequest = new Request.Builder().url(add(graphURL)).build();

       okHttpClient.newCall(graphRequest).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Toast.makeText(graphActivity.this, "Error in Loading" + e.getMessage(),
                       Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               final String graphInfo = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseGraphInfo(graphInfo);
                    }
                });
           }
       });
   }

   private void parseGraphInfo(String graphInfo){
       try{
            JSONObject jsonObject = new JSONObject(graphInfo);
            JSONArray jsonArray = jsonObject.getJSONArray("price");
            //JSONArray innerArray = jsonArray.getJSONArray(0);
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0; i < jsonArray.length(); i++){
                JSONArray innerArray = jsonArray.getJSONArray(i);

                stringBuilder.append(innerArray.get(0)).append("  " + innerArray.get(1) + "\n");
            }
            txt.setText(stringBuilder.toString());

       }
       catch(Exception e){

       }
   }
}
