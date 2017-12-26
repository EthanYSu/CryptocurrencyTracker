package com.example.ethan.cryptocurrencytracker;

import java.util.ArrayList;

public class Coin extends ArrayList<Coin> {
    private String id;
    private String coinName;
    private String symbolName;
    private String coinPrice;
    private String coinChange;

    public Coin(String id, String coinName, String symbolName, String coinPrice, String coinChange){
        this.id = id;
        this.coinName = coinName;
        this.symbolName = symbolName;
        this.coinPrice = coinPrice;
        this.coinChange = coinChange;
    }

    public String getId() {return this.id;}
    public String getCoinName(){return this.coinName;}
    public String getSymbolName(){return this.symbolName;}
    public String getCoinPrice(){return this.coinPrice;}
    public String getCoinChange(){return this.coinChange;}

    public void setId(String id) {this.id = id;}
    public void setCoinName(String coinName){
        this.coinName = coinName;
    }
    public void setSymbolName(String symbolName) {this.symbolName = symbolName;}
    public void setCoinPrice(String coinPrice){
        this.coinPrice = coinPrice;
    }
    public void setCoinChange(String coinChange){
        this.coinChange = coinChange;
    }
}
