package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        /** Note
         * - Output should be Map<취미, Number of 해당 취미를 가지고 있는 사람>
         * - SQL의 GroupBy를 이용하여 Grouping필요.
         * - 각 사람당(라인당) 취미를 N개 가지고 있으며, 이때 delimiter is ":"
         * Q. How to skip csv header?
         * -> A. 애초에 헤더는 빼고 추출된다.
         * Q. 취미 항목만 어떻게 추출하나?
         * -> A. 객체매핑이 안되있으니 포지션으로 추출(1)
         * Q. Stream<String[]>을 어떻게 하나의 스트림으로 평준화 시키나
         * -> A. flatMap(Arrays.stream)
         * Q. 추출한 각 취미들로부터, 어떻게 그 카운트를 구하지?
         * -> A. groupingBy에 counting collector이용
         * Q. 각 취미 개수 연산 개수는 Integer여야 하는데, couting collector는 Long을 반환한다.어쩌지?
         * -> A. summingInt collector이용
         * Q. 각 빌딩블록 연산들은 순서 보장되나?
         * Q. How to ordering by the count result?
         * Q. Answer에서는 toMap이용해서 해결했으니, toMap으로도 해결해보기
         */
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                .map(line -> line[1])
                .map(hobbies -> hobbies.split(":"))
                .flatMap(Arrays::stream)
                .map(String::trim)
                .collect(groupingBy(hobby -> hobby, summingInt(each -> 1)));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return new HashMap<>();
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return 0;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
