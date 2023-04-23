package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
            .map(strings -> strings[1]) // collect only hobbies
            .map(hobbies -> hobbies.split(":"))
            .flatMap(Arrays::stream)
            .map(String::trim)
            .collect(toMap(hobby -> hobby, hobby -> 1, Integer::sum));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
            .filter(line -> line[0].startsWith("정"))
            .map(line -> line[1].replaceAll("\\s", ""))
            .map(hobbies -> hobbies.split(":"))
            .flatMap(Arrays::stream)
            .map(String::trim)
            .collect(groupingBy(hobby -> hobby, summingInt(hobby -> 1)));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
            .map(line -> line[2])
            .map(introduction -> getMatchCount(introduction, "좋아"))
            .reduce(0, Integer::sum);
    }

    private static int getMatchCount(String line, String keyword) {
        int result = 0;
        int foundIndex = line.indexOf(keyword);
        while (0 <= foundIndex && foundIndex < line.length()) {
            result++;
            foundIndex = line.indexOf(keyword, foundIndex + keyword.length());
        }
        return result;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
