package com.company;

import java.util.List;

public interface ReplacementType<T> {
    List<T[]> doReplacement(List<T[]> oldGeneration, List<Double> oldGenerationFitness , List<T[]> newGeneration, List<Double> newGenerationFitness);
}
