package com.bobr.WebLab4.beans;

import com.bobr.WebLab4.models.Hit;
import org.springframework.stereotype.Component;

@Component
public class HitHandler {
    private final static double minX = -4;
    private static final double maxX = 4;
    private final static double minY = -5;
    private static final double maxY = 5;
    private final static double minR = -4;
    private static final double maxR = 4;

    public boolean isSuccess(Hit hit) {
        double x = hit.getX();
        double y = hit.getY();
        double r = Math.abs(hit.getR());
        boolean result;

        if (x <= 0 && y >= 0)
            result = (x >= -r && y <= r);
        else if (x >= 0 && y >= 0)
            result = y <= r / 2 && y <= r / 2 - x / 2;
        else if (x >= 0 && y <= 0)
            result = (x * x + y * y <= r * r);
        else
            result = false;

        return hit.getR() >= 0? result : !result;
    }
    public boolean isValidCoordinates(Hit hit) {
        if (hit.getX() == null || hit.getY() == null || hit.getR() == null)
            return false;

        return (hit.getX() >= minX && hit.getX() <= maxX &&
                hit.getY() >= minY && hit.getY() <= maxY &&
                hit.getR() >= minR && hit.getR() <= maxR);
    }
}
