package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA<T> {
    private List<T[]> chromosomes;
    private List<Double> populationFitness;
    private final List<Integer> matingPool;
    private final int populationSize;
    private final double crossOverProb;
    private List<T[]> newGeneration;
    private List<T[]> newGenerationWithNoCross;
    private final double mutationProb;
    private final int iterationNumber;
    private final GaHelper helper;
    private final CrossOverType crossOver;
    private final ReplacementType replacementType;

    public GA(int populationSize, double crossOverProb, double mutationProb, int iterationNumber, GaHelper gaHelper, CrossOverType crossOver, ReplacementType replacementType) {
        matingPool = new ArrayList<>();
        newGeneration = new ArrayList<>();
        this.populationSize = populationSize;
        this.crossOverProb = crossOverProb;
        this.mutationProb = mutationProb;
        this.iterationNumber = iterationNumber;
        newGenerationWithNoCross = new ArrayList<>();
        this.helper = gaHelper;
        this.crossOver = crossOver;
        this.replacementType = replacementType;
    }

    private void startTournament() {
        int counter = 0;
        Random random = new Random();
        int prev = -1;
        while (counter < populationSize) {
            int index1 = random.nextInt(populationSize);
            int index2 = random.nextInt(populationSize);
            if (index1 == index2) {
                continue;
            }
            int winningIndex = helper.tournamentWinningCondition(populationFitness.get(index1), index1, populationFitness.get(index2), index2);
            if (prev == winningIndex) {
                continue;
            }
            prev = winningIndex;
            matingPool.add(winningIndex);
            counter++;
            if (matingPool.size() % 2 == 0) {
                prev = -1;
            }
        }
    }

    private void startMating() {
        Random random = new Random();
        while (!matingPool.isEmpty()) {
            T[] parent1 = chromosomes.get(matingPool.remove(0));
            T[] parent2 = chromosomes.get(matingPool.remove(0));
            if (Math.random() <= crossOverProb) {
                List<T[]> childes = crossOver.doCrossOver(parent1, parent2);
                newGeneration.add(childes.get(0));
                newGeneration.add(childes.get(1));
            } else {
                newGenerationWithNoCross.add(parent1);
                newGenerationWithNoCross.add(parent2);
            }
        }
    }

    public void start() {
        chromosomes = helper.generatePopulation(populationSize);
        populationFitness = helper.calculatePopulationFitness(chromosomes);
        for (int i = 0; i < iterationNumber; i++) {
            startTournament();
            startMating();
            helper.mutation(newGeneration, mutationProb,i,iterationNumber);
            newGeneration.addAll(newGenerationWithNoCross);
            List<Double> newGenFitness =  helper.calculatePopulationFitness(newGeneration);
            chromosomes = replacementType.doReplacement(chromosomes, populationFitness,newGeneration,newGenFitness);
            newGeneration = new ArrayList<>();
            newGenerationWithNoCross = new ArrayList<>();
            populationFitness = helper.calculatePopulationFitness(chromosomes);
            int solutionIndex = helper.stoppingCondition(populationFitness);
            if (solutionIndex != -1) {
                helper.printPhenotype(chromosomes.get(solutionIndex), populationFitness.get(solutionIndex));
                break;
            }
            if (i == iterationNumber - 1) {
                helper.printPhenotypeAfterIterations(chromosomes, populationFitness);
            }
        }
    }
}
