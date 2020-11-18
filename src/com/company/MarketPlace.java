package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MarketPlace implements GaHelper<Double[]> {
    private List<Channel> channels;
    private double budget;
    @Override
    public List<Double[]> generatePopulation(int populationSize) {
        List<Double> chromosomes = new ArrayList<>();
        for(int i=0 ; i < populationSize ; i++){
            Double [] chromosome = new Double[channels.size()];
            for(int j=0 ; j<channels.size() ; j++){
                Channel channel = channels.get(j);
                double upperBound = channel.getUpperBound();
                if(upperBound == -1){
                    upperBound = budget;
                }
                chromosome[j] = Math.random() * (upperBound - channel.getLowerBound()+1) + channel.getLowerBound();
            }
            checkAndFix(chromosome);
        }
    }

    @Override
    public void checkAndFix(Double[] chromosome) {
        double sum = 0D;
        Random random = new Random();
        for(int i=0 ; i<chromosome.length ; i++){
            sum += chromosome[i];
        }
        if(sum > budget){
            double overValue = sum - budget;
            while (overValue != 0){

            }
        }
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
