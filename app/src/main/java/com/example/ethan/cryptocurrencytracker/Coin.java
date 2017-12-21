package com.example.ethan.cryptocurrencytracker;

public class Coin {
    private String coinName;
    private String coinPrice;
    private String coinChange;

    public Coin(String coinName, String coinPrice, String coinChange){
        this.coinName = coinName;
        this.coinPrice = coinPrice;
        this.coinChange = coinChange;
    }

    public String getCoinName(){return this.coinName;}
    public String getCoinPrice(){return this.coinPrice;}
    public String getCoinChange(){return this.coinChange;}

    public void setCoinName(String coinName){
        this.coinName = coinName;
    }
    public void setCoinPrice(String coinPrice){
        this.coinPrice = coinPrice;
    }
    public void setCoinChange(String coinChange){
        this.coinChange = coinChange;
    }
}
