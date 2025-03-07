package com.mangkyu.stream.Quiz4;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Quiz4 {

    private List<Transaction> transactions;

    public Quiz4() {
        Trader kyu = new Trader("Kyu", "Seoul");
        Trader ming = new Trader("Ming", "Gyeonggi");
        Trader hyuk = new Trader("Hyuk", "Seoul");
        Trader hwan = new Trader("Hwan", "Busan");

        transactions = Arrays.asList(
                new Transaction(kyu, 2019, 30000),
                new Transaction(kyu, 2020, 12000),
                new Transaction(ming, 2020, 40000),
                new Transaction(ming, 2020, 7100),
                new Transaction(hyuk, 2019, 5900),
                new Transaction(hwan, 2020, 4900)
        );
    }

    // 4.1 2020년에 일어난 모든 거래 내역을 찾아 거래값을 기준으로 오름차순 정렬하라.
    public List<Transaction> quiz1() {
        return transactions.stream()
            .filter(tx -> tx.getYear() == 2020)
            .sorted(Comparator.comparingInt(Transaction::getValue))
            .collect(Collectors.toList());
    }

    // 4.2 거래 내역이 있는 거래자가 근무하는 모든 도시를 중복 없이 나열하라.
    public List<String> quiz2() {
        return transactions.stream()
            .map(Transaction::getTrader)
            .filter(Objects::nonNull)
            .map(Trader::getCity)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }

    // 4.3 서울에서 근무하는 모든 거래자를 찾아서 이름순서대로 정렬하라.
    public List<Trader> quiz3() {
        final String targetCity = "Seoul";
        return transactions.stream()
            .filter(Objects::nonNull)
            .map(Transaction::getTrader)
            .distinct()
            .filter(trader -> trader != null && targetCity.equals(trader.getCity()))
            .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
            .sorted(Comparator.comparing(Trader::getName))
            .collect(Collectors.toList());
    }

    // 4.4 모든 거래자의 이름을 구분자(",")로 구분하여 정렬하라.
    public String quiz4() {
        return transactions.stream()
            .map(tx -> tx.getTrader().getName())
            .distinct()
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.joining(","));
    }

    // 4.5 부산에 거래자가 있는지를 확인하라.
    public boolean quiz5() {
        final String targetCity = "Busan";
        return transactions.stream()
            .anyMatch(tx -> targetCity.equals(tx.getTrader().getCity()));
    }

    // 4.6 서울에 거주하는 거래자의 모든 거래 금액을 구하라.
    public List<Integer> quiz6() {
        final String targetCity = "Seoul";
        return transactions.stream()
            .filter(tx -> targetCity.equals(tx.getTrader().getCity()))
            .map(Transaction::getValue)
            .collect(Collectors.toList());
    }

    // 4.7 모든 거래 내역중에서 거래 금액의 최댓값과 최솟값을 구하라. 단, 최댓값은 reduce를 이용하고 최솟값은 stream의 min()을 이용하라.
    public Integer[] quiz7() {
        return new Integer[] {getMaxValue(transactions), getMinValue(transactions)};
    }

    Integer getMaxValue(List<Transaction> transactions) {
        return transactions.stream()
            .map(tx -> tx.getValue())
            .reduce(0, (accumulatedSoFar, current)-> accumulatedSoFar < current ? current : accumulatedSoFar);
    }

    Integer getMinValue(List<Transaction> transactions) {
        return transactions.stream()
            .map(tx -> tx.getValue())
            .min(Integer::compareTo)
            .orElse(0);
    }

}
