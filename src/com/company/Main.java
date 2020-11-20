package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        int numberOfChannels;
        double budget;
        List<Channel> channelList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the marketing budget (in thousands):");
        budget = input.nextDouble();
        System.out.println("Enter the number of marketing channels:");
        numberOfChannels = input.nextInt();
        System.out.println("Enter the name of ROI (in %) of each channel separated by space:");
        for (int i = 0; i < numberOfChannels; i++) {
            channelList.add(new Channel(input.next(), input.nextDouble()));
        }
        System.out.println("Enter the lower (k) and upper bounds (%) of investment in each channel:\n(enter x if there is no bound)");
        for (int i = 0; i < numberOfChannels; i++) {
            channelList.get(i).setLowerBound(input.next());
            channelList.get(i).setUpperBound(input.next());
        }
        System.out.println("\nPlease wait while running the GA...\n");
        MarketPlace marketPlace = new MarketPlace(channelList, budget);
        ElitismReplacement elitismReplacement = new ElitismReplacement(3);
        TwoPointCrossOver twoPointCrossOver = new TwoPointCrossOver();
        GA<Double> ga = new GA<>(16, 0.56, 0.1, 1000, marketPlace, twoPointCrossOver, elitismReplacement);
        for(int i=0; i<20 ; i++){
            ga.start();
        }
    }
}
