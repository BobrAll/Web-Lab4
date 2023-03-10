package com.bobr.WebLab4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double x;
    private Double y;
    private Double r;

    private boolean isSuccess;
    private String dateTime;
    private String owner;

    public Hit(double x, double y, double r, boolean isSuccess, String dateTime) {
        setX(x);
        setY(y);
        setR(r);
        this.isSuccess = isSuccess;
        this.dateTime = dateTime;
    }
}
