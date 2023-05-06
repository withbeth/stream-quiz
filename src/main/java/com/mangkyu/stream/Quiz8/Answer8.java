package com.mangkyu.stream.Quiz8;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Answer8 {

    public List<int[]> quiz1() {
        return Stream.iterate(new int[]{0, 1}, xs -> new int[]{xs[1], xs[0] + xs[1]})
            .limit(10)
            .collect(Collectors.toList());
    }

    public List<int[]> quiz2() {
        return Stream.generate(new Supplier<int[]>() {
                private int[] current = new int[]{0, 1};

                @Override
                public int[] get() {
                    int[] result = this.current.clone();
                    this.current = new int[]{result[1], result[0] + result[1]};
                    return result;
                }
            })
            .limit(10)
            .collect(Collectors.toList());
    }

}
