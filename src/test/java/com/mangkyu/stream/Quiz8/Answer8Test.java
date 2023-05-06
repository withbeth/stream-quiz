package com.mangkyu.stream.Quiz8;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class Answer8Test {

    Answer8 answer = new Answer8();

    @Test
    void quiz1() {
        assertThat(answer.quiz1()).containsExactly(
            new int[]{0, 1},
            new int[]{1, 1},
            new int[]{1, 2},
            new int[]{2, 3},
            new int[]{3, 5},
            new int[]{5, 8},
            new int[]{8, 13},
            new int[]{13, 21},
            new int[]{21, 34},
            new int[]{34, 55}
        );
    }

    @Test
    void quiz2() {
        assertThat(answer.quiz2()).containsExactly(
            new int[]{0, 1},
            new int[]{1, 1},
            new int[]{1, 2},
            new int[]{2, 3},
            new int[]{3, 5},
            new int[]{5, 8},
            new int[]{8, 13},
            new int[]{13, 21},
            new int[]{21, 34},
            new int[]{34, 55}
        );
    }

}