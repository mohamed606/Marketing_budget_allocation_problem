package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoPointCrossOver implements CrossOverType<Double> {
    @Override
    public List<Double[]> doCrossOver(Double[] chromosome1, Double[] chromosome2) {
        Random random = new Random();
        List<Double[]> childes = new ArrayList<>();
        int crossOverPoint1 = random.nextInt(chromosome1.length - 2) + 1;
        int crossOverPoint2 = random.nextInt(chromosome1.length - 2) + 1;
        Double[] child1 = new Double[chromosome1.length];
        Double[] child2 = new Double[chromosome2.length];
        for (int i = 0; i < chromosome1.length; i++) {
            child1[i] = chromosome1[i];
            child2[i] = chromosome2[i];
        }
        for (int i = crossOverPoint1; i < crossOverPoint2; i++) {
            child1[i] = chromosome2[i];
            child2[i] = chromosome1[i];
        }
        childes.add(child1);
        childes.add(child2);
        return childes;
    }
}
