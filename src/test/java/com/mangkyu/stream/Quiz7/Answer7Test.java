package com.mangkyu.stream.Quiz7;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class Answer7Test {

    private final Answer7 answer = new Answer7();

    @Test
    void two() {
        Map<Boolean, List<Integer>> result = answer.quiz1(2);
        List<Integer> primes = result.get(Boolean.TRUE);
        List<Integer> nonPrimes = result.get(Boolean.FALSE);
        assertThat(primes).containsExactly(2);
        assertThat(nonPrimes).isEmpty();
    }

    @Test
    void three() {
        Map<Boolean, List<Integer>> result = answer.quiz1(3);
        List<Integer> primes = result.get(Boolean.TRUE);
        List<Integer> nonPrimes = result.get(Boolean.FALSE);
        assertThat(primes).containsExactly(2, 3);
        assertThat(nonPrimes).isEmpty();
    }

    @Test
    void five() {
        Map<Boolean, List<Integer>> result = answer.quiz1(5);
        List<Integer> primes = result.get(Boolean.TRUE);
        List<Integer> nonPrimes = result.get(Boolean.FALSE);
        assertThat(primes).containsExactly(2, 3, 5);
        assertThat(nonPrimes).containsExactly(4);
    }

    @Test
    void eleven() {
        Map<Boolean, List<Integer>> result = answer.quiz1(11);
        List<Integer> primes = result.get(Boolean.TRUE);
        List<Integer> nonPrimes = result.get(Boolean.FALSE);
        assertThat(primes).containsExactly(2, 3, 5, 7, 11);
        assertThat(nonPrimes).containsExactly(4, 6, 8, 9, 10);
    }


}