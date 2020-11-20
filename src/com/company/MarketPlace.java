package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketPlace implements GaHelper<Double[]> {
    protected List<Channel> channels;
    protected double budget;

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
                    chromosome[index] += -1 * overValue;
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

    protected double randomNumberWithBounds(double upperBound, double lowerBound) {
        return Math.random() * (upperBound - lowerBound) + lowerBound;
    }

    @Override
    public List<Double> calculatePopulationFitness(List<Double[]> chromosomes) {
        List<Double> fitness = new ArrayList<>();
        for (int i = 0; i < chromosomes.size(); i++) {
            fitness.add(calculateChromosomeFitness(chromosomes.get(i)));
        }
        return fitness;
    }

    private double calculateChromosomeFitness(Double[] chromosome) {
        double fitness = 0D;
        for (int i = 0; i < chromosome.length; i++) {
            fitness = fitness + (chromosome[i] * (channels.get(i).getROI()/100));
        }
        return fitness;
    }

    @Override
    public void mutation(List<Double[]> chromosomes, double mutationProb, int currentGeneration, int totalNumberOfGenerations) {
        for (int i = 0; i < chromosomes.size(); i++) {
            Double[] chromosome = chromosomes.get(i);
            for (int j = 0; j < chromosome.length; j++) {
                if (Math.random() < mutationProb) {
                    double deltaLower = chromosome[j] - channels.get(j).getLowerBound();
                    double deltaUpper = channels.get(j).getUpperBound(budget) - chromosome[j];
                    double delta;
                    if (Math.random() <= 0.5) {
                        delta = deltaLower;
                    } else {
                        delta = deltaUpper;
                    }
                    double changeValue = randomNumberWithBounds(delta, 0);
                    if (delta == deltaLower) {
                        chromosome[j] = chromosome[j] - changeValue;
                    } else {
                        chromosome[j] = chromosome[j] + changeValue;
                    }
                }
            }
            checkAndFix(chromosome);
        }
    }

    @Override
    public int stoppingCondition(List<Double> populationFitness) {
        return -1;
    }

    @Override
    public void printPhenotype(Double[] chromosome, double fitness) {
        try {
            System.out.println("The final marketing budget allocation");
            FileWriter writer = new FileWriter("src/com/company/uniform.txt", true);
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
    public void printPhenotypeAfterIterations(List<Double[]> chromosomes, List<Double> fitness) {
        int index = 0;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < chromosomes.size(); i++) {
            if (fitness.get(i) > max) {
                max = fitness.get(i);
                index = i;
            }
        }
        printPhenotype(chromosomes.get(index), fitness.get(index));
    }

    @Override
    public int tournamentWinningCondition(double fitness1, int index1, double fitness2, int index2) {
        if (fitness1 > fitness2) {
            return index1;
        } else {
            return index2;
        }
    }
}
