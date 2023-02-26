package com.bobr.WebLab4.models;

public class Point {
    private double x;
    private double y;
    private double r;

    private boolean isHit;
    private String dateTime;

    public Point(double x, double y, double r, boolean isHit, String dateTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
        this.dateTime = dateTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
