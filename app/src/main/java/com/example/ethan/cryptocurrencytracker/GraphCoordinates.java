package com.example.ethan.cryptocurrencytracker;

/**
 * Created by ethan on 12/24/2017.
 */

public class GraphCoordinates {
    private long x;
    private double y;

    public GraphCoordinates(long x, double y){
        this.x = x;
        this.y = y;
    }

    public long getX(){ return this.x;}
    public double getY() {return this.y;}

    public void setX(long x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
