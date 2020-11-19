package com.company;

import java.util.List;

public interface CrossOverType<T> {
    List<T[]> doCrossOver(T[]chromosome1, T[]chromosome2);
}
