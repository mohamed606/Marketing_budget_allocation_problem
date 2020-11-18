package com.company;

public class Channel {
    private final String name;
    private final double ROI;
    private final String lowerBound;
    private final String upperBound;//percentage

    public String getName() {
        return name;
    }

    public double getROI() {
        return ROI;
    }

    public double getLowerBound() {
        if(lowerBound.equals("x")){
            return 0;
        }
        return Double.parseDouble(lowerBound);
    }

    public double getUpperBound() {
        if(upperBound.equals("x")){
            return -1;
        }
        return Double.parseDouble(upperBound)/100;
    }

    public Channel(String name, double ROI, String lowerBound, String upperBound) {
        this.name = name;
        this.ROI = ROI;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
