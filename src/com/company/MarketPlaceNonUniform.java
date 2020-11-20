package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MarketPlaceNonUniform extends MarketPlace {
    public MarketPlaceNonUniform(List<Channel> channels, double budget) {
        super(channels, budget);
    }

    @Override
    public void printPhenotype(Double[] chromosome, double fitness) {
        try {
            System.out.println("The final marketing budget allocation");
            FileWriter writer = new FileWriter("src/com/company/nonUniform.txt", true);
            for (int i = 0; i < chromosome.length; i++) {
                String line = channels.get(i).getName() + " -> " + chromosome[i] + "\n";
                System.out.print(line);
                writer.write(line);
            }
            System.out.println("The total profit is " + fitness);
            writer.write("The total profit is " + fitness+"\n");
            writer.write("\n********************************\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mutation(List<Double[]> chromosomes, double mutationProb,int currentGeneration, int totalNumberOfGenerations) {
        for (int i = 0; i < chromosomes.size(); i++) {
            Double[] chromosome = chromosomes.get(i);
            for (int j = 0; j < chromosome.length; j++) {
                if (Math.random() < mutationProb) {
                    double deltaLower = chromosome[j] - channels.get(j).getLowerBound();
                    double deltaUpper = channels.get(j).getUpperBound(budget) - chromosome[j];
                    double delta;
                    double y;
                    if (Math.random() <= 0.5) {
                        y = deltaLower;
                    } else {
                        y = deltaUpper;
                    }
                    delta = y*(1-Math.pow(Math.random(),Math.pow(1-(double)currentGeneration/totalNumberOfGenerations,3)));
                    double changeValue = randomNumberWithBounds(delta, 0);
                    if (y == deltaLower) {
                        chromosome[j] = chromosome[j] - changeValue;
                    } else {
                        chromosome[j] = chromosome[j] + changeValue;
                    }
                }
            }
            checkAndFix(chromosome);
        }
    }
}
