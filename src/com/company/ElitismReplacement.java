package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ElitismReplacement implements ReplacementType<Double> {
    private final int numberOfSelectedParents;

    public ElitismReplacement(int numberOfSelectedParents) {
        this.numberOfSelectedParents = numberOfSelectedParents;
    }

    @Override
    public List<Double[]> doReplacement(List<Double[]> oldGeneration, List<Double> oldGenerationFitness, List<Double[]> newGeneration, List<Double> newGenerationFitness) {
        List<Double[]> nextGen = new ArrayList<>();
        double max = Double.MIN_VALUE;
        int index = 0;
        int oldGenerationSize = oldGeneration.size();
        for (int i = 0; i < numberOfSelectedParents; i++) {
            for (int j = 0; j < oldGeneration.size(); j++) {
                if (oldGenerationFitness.get(j) > max) {
                    max = oldGenerationFitness.get(j);
                    index = j;
                }
            }
            nextGen.add(oldGeneration.remove(index));
            oldGenerationFitness.remove(index);
            max = Double.MIN_VALUE;
            index = 0;
        }
        max = Double.MIN_VALUE;
        index = 0;
        for (int i = 0; i < oldGenerationSize - numberOfSelectedParents; i++) {
            for (int j = 0; j < newGeneration.size(); j++) {
                if (newGenerationFitness.get(j) > max) {
                    max = newGenerationFitness.get(j);
                    index = j;
                }
            }
            nextGen.add(newGeneration.remove(index));
            newGenerationFitness.remove(index);
            max = Double.MIN_VALUE;
            index = 0;
        }
        return nextGen;
    }
}
