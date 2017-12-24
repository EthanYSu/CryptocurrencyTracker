package com.example.ethan.cryptocurrencytracker;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class graphActivity extends AppCompatActivity {

    OkHttpClient okHttpClient = new OkHttpClient();
    private final String coinGraphAPI = "http://coincap.io/history/";
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

//        String graphType = "";
//
//        Fragment tab1 = getSupportFragmentManager().findFragmentByTag("1 Day");
//        Fragment tab2 = getSupportFragmentManager().findFragmentByTag("7 Day");
//        Fragment tab3 = getSupportFragmentManager().findFragmentByTag("30 Day");
//
//        if(tab1 != null && tab1.isVisible()){
//            graphType = "1Day";
//        }
//        if(tab1 != null && tab2.isVisible()){
//            graphType = "7day";
//        }
//        if(tab1 != null && tab3.isVisible()){
//            graphType = "30day";
//        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch(position){
               case 0:
                   OneDayGraph oneDayGraph = new OneDayGraph();
                   return oneDayGraph;
               case 1:
                   SevenDayGraph sevenDayGraph = new SevenDayGraph();
                   return sevenDayGraph;
               case 2:
                   ThirtyDayGraph thirtyDayGraph = new ThirtyDayGraph();
                   return thirtyDayGraph;
               default:
                   return null;
           }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


    public String coinSymbol(String x){
        Intent intent = getIntent();
        String coinS = intent.getStringExtra("CoinSymbol");
        return coinGraphAPI + x + "/" + coinS;
    }

    public void load(final String s){
        Request graphRequest = new Request.Builder().url(coinSymbol(s)).build();

        okHttpClient.newCall(graphRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(graphActivity.this, "Error in loading" +  e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String graphInfo = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseGraphInfo(graphInfo, s);
                    }
                });
            }
        });
    }

    private void parseGraphInfo(String graphInfo, String s){
        try{
            JSONObject jsonObject = new JSONObject(graphInfo);
            JSONArray jsonArray = jsonObject.getJSONArray("price");
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i< jsonArray.length(); i++){
                JSONArray innerArray = jsonArray.getJSONArray(i);
                stringBuilder.append(innerArray.get(0)).append("\t"+ innerArray.get(1)+"\n");
            }

            TextView output;
            if(s.equals("1day")){
                output = findViewById(R.id.txt);
                output.setText(stringBuilder.toString());
            }
            if(s.equals("7day")){
                output = findViewById(R.id.txt7);
                output.setText(stringBuilder.toString());
            }
            if(s.equals("30day")){
                output = findViewById(R.id.txt30);
                output.setText(stringBuilder.toString());
            }
        }
        catch (Exception e){
        }
    }
}
