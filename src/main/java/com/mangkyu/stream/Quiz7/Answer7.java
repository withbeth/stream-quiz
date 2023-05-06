package com.mangkyu.stream.Quiz7;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Answer7 {

    public static boolean isPrime(int candidate) {
        return IntStream.range(2, candidate)
            .noneMatch(i -> candidate % i == 0);
    }

    public Map<Boolean, List<Integer>> quiz1(int n) {
        return IntStream.rangeClosed(2, n)
            .boxed()
            .collect(partitioningBy(Answer7::isPrime, toList()));
    }


}
