package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        int numberOfChannels;
//        double budget;
//        List<Channel> channelList = new ArrayList<>();
//        Scanner input = new Scanner(System.in);
//        numberOfChannels = input.nextInt();
//        budget = input.nextDouble();
//        for(int i=0 ;i<numberOfChannels ; i++){
//            channelList.add(new Channel(input.next(), input.nextDouble(), input.next(),input.next()));
//        }
//        MarketPlace marketPlace = new MarketPlace(channelList, budget);
//        marketPlace.generatePopulation(8);\
        Double[] pa = new Double[]{1.0,2.0,3.0,4.0};
        Double[] childe = pa;
        childe[2]= 6666.0;
        for(int i=0 ; i<pa.length ; i++){
            System.out.println(pa[i]);
        }
    }
}
