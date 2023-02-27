package com.bobr.WebLab4.beans;

import org.springframework.stereotype.Component;

@Component
public class HitHandler {
    private final static double minX = -4;
    private static final double maxX = 4;
    private final static double minY = -4;
    private static final double maxY = 4;
    private final static double minR = -4;
    private static final double maxR = 4;

    public boolean isHit(double x, double y, double r) {
        if (x < minX || x > maxX || y < minY || y > maxY || r < minR || r > maxR)
            return false;

        if (x < 0 && y > 0)
            return (x >= -r && y <= r);
        else if (x > 0 && y > 0)
            return y < r / 2 && y < r / 2 - x / 2;
        else if (x > 0 && y < 0)
            return (x * x + y * y < r * r);
        else
            return false;
    }
}
