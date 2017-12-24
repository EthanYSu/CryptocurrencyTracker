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
import org.w3c.dom.Text;

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
    private ArrayList<Long> xCoordinate1Day = new ArrayList<>();
    private ArrayList<Double> yCoordinate1Day = new ArrayList<>();
    private ArrayList<Long> xCoordinate7Day = new ArrayList<>();
    private ArrayList<Double> yCoordinate7Day = new ArrayList<>();
    private ArrayList<Long> xCoordinate30Day = new ArrayList<>();
    private ArrayList<Double> yCoordinate30Day = new ArrayList<>();

    LineGraphSeries<DataPoint> coinGraph1Day,coinGraph7Day,coinGraph30Day;

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
//            graphType = "1day";
//            load(graphType);
//        }
//        if(tab2 != null && tab2.isVisible()){
//            graphType = "7day";
//            load(graphType);
//
//        }
//        if(tab3 != null && tab3.isVisible()){
//            graphType = "30day";
//            load(graphType);
//
//        }
//        load("1day");
//        load("7day");
//        load("30day");
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
            coinGraph1Day = new LineGraphSeries<>();
            coinGraph7Day = new LineGraphSeries<>();
            coinGraph30Day = new LineGraphSeries<>();

            GraphView graphView;

            if(s.equals("1day")){
                for(int i = 0; i< jsonArray.length(); i++){
                    JSONArray innerArray = jsonArray.getJSONArray(i);
                    Long x = innerArray.getLong(0);
                    Double y = innerArray.getDouble(1);
                    xCoordinate1Day.add(x);
                    yCoordinate1Day.add(y);
                }
                graphView = findViewById(R.id.graph1Day);
                for(int j = 0; j < xCoordinate1Day.size(); j++){
                    coinGraph1Day.appendData(new DataPoint(xCoordinate1Day.get(j), yCoordinate1Day.get(j)), true, xCoordinate1Day.size());
                }
                graphView.addSeries(coinGraph1Day);
            }
            if(s.equals("7day")){
                for(int i = 0; i< jsonArray.length(); i++){
                    JSONArray innerArray = jsonArray.getJSONArray(i);
                    Long x = innerArray.getLong(0);
                    Double y = innerArray.getDouble(1);
                    xCoordinate7Day.add(x);
                    yCoordinate7Day.add(y);
                }
                graphView = findViewById(R.id.graph7Day);
                for(int j = 0; j < xCoordinate7Day.size(); j++){
                    coinGraph7Day.appendData(new DataPoint(xCoordinate7Day.get(j), yCoordinate7Day.get(j)), true, xCoordinate7Day.size());
                }
                graphView.addSeries(coinGraph7Day);
            }
            if(s.equals("30day")){
                for(int i = 0; i< jsonArray.length(); i++){
                    JSONArray innerArray = jsonArray.getJSONArray(i);
                    Long x = innerArray.getLong(0);
                    Double y = innerArray.getDouble(1);
                    xCoordinate30Day.add(x);
                    yCoordinate30Day.add(y);
                }
                graphView = findViewById(R.id.graph30Day);
                for(int j = 0; j < xCoordinate30Day.size(); j++){
                    coinGraph30Day.appendData(new DataPoint(xCoordinate30Day.get(j), yCoordinate30Day.get(j)), true, xCoordinate30Day.size());
                }
                graphView.addSeries(coinGraph30Day);
            }
        }
        catch (Exception e){
        }
    }
}
