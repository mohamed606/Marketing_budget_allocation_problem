package com.company;

public class Channel {
    private final String name;
    private final double ROI;

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    private  String lowerBound;
    private  String upperBound;//percentage

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

    public double getUpperBound(double budget) {
        if(upperBound.equals("x")){
            return budget;
        }
        return (Double.parseDouble(upperBound)/100)*budget;
    }

    public Channel(String name, double ROI) {
        this.name = name;
        this.ROI = ROI;
    }
}
