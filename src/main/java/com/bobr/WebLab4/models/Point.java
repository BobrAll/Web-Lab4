package com.bobr.WebLab4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double x;
    private double y;
    private double r;

    private boolean isHit;
    private String dateTime;

    public Point() {

    }
    public Point(double x, double y, double r, boolean isHit, String dateTime) {
        setX(x);
        setY(y);
        setR(r);
        this.isHit = isHit;
        this.dateTime = dateTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = round(x, 1);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = round(y, 1);
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = round(r, 1);
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

    private double round(double num, int digits) {
        return (int)(num * digits * 10) / (10.0 * digits);
    }
}
