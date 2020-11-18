package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MarketPlace implements GaHelper<Double[]> {
    private List<Channel> channels;
    private double budget;

    public MarketPlace(List<Channel> channels, double budget) {
        this.channels = channels;
        this.budget = budget;
    }

    @Override
    public List<Double[]> generatePopulation(int populationSize) {
        List<Double[]> chromosomes = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Double[] chromosome = new Double[channels.size()];
            for (int j = 0; j < channels.size(); j++) {
                Channel channel = channels.get(j);
                double upperBound = channel.getUpperBound(budget);
                chromosome[j] = randomNumberWithBounds(upperBound, channel.getLowerBound());
            }
            checkAndFix(chromosome);
            chromosomes.add(chromosome);
        }
        return chromosomes;
    }

    @Override
    public void checkAndFix(Double[] chromosome) {
        double sum = 0D;
        Random random = new Random();
        for (int i = 0; i < chromosome.length; i++) {
            sum += chromosome[i];
        }
        if (sum > budget) {
            double overValue = sum - budget;
            int index = random.nextInt(chromosome.length);
            while (overValue != 0) {
                double value = chromosome[index];
                double newValue = randomNumberWithBounds(value, channels.get(index).getLowerBound());
                chromosome[index] = newValue;
                overValue = overValue - (value - newValue);
                if (overValue < 0) {
                    chromosome[index] += -1*overValue;
                    overValue = 0;
                }
                index++;
                if (index >= chromosome.length) {
                    index = 0;
                }
            }
        } else if (sum < budget) {
            double underValue = budget - sum;
            int index = random.nextInt(chromosome.length);
            while (underValue != 0) {
                double value = chromosome[index];
                double newValue = value + underValue;
                double upperBound = channels.get(index).getUpperBound(budget);
                if (upperBound == budget) {
                    upperBound = newValue;
                }
                if (newValue > upperBound) {
                    underValue = newValue - upperBound;
                    newValue = upperBound;
                } else {
                    underValue = 0;
                }
                chromosome[index] = newValue;
                index++;
                if (index >= chromosome.length) {
                    index = 0;
                }
            }
        }
    }

    private double randomNumberWithBounds(double upperBound, double lowerBound) {
        return Math.random() * (upperBound - lowerBound ) + lowerBound;
    }

    @Override
    public List<Double> calculatePopulationFitness(List<Double[]> chromosomes) {
        return null;
    }

    @Override
    public void mutation(List<Double[]> chromosomes, double mutationProb) {

    }

    @Override
    public int stoppingCondition(List<Double> populationFitness) {
        return 0;
    }

    @Override
    public void printPhenotype(Double[] chromosome, double fitness) {

    }

    @Override
    public void printPhenotypeAfterIterations(List<Double[]> chromosomes, List<Double> fitness) {

    }
}
