## 문제4
아래와 같은 데이터를 갖는 거래자와 거래 내역 클래스가 있다고 하자.
(생성자 및 Getter, Setter 등은 생략)

```
public class Trader {
    private String name;
    private String city;
}

public class Transaction {
    private Trader trader;
    private int year;
    private int value;
}
```


또한 데이터는 아래와 같이 초기화되어 있다.

```
public void init() {
    Trader kyu = new Trader("Kyu", "Seoul");
    Trader ming = new Trader("Ming", "Gyeonggi");
    Trader hyuk = new Trader("Hyuk", "Incheon");
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
```


### 문제 4.1

2020년에 일어난 모든 거래 내역을 찾아 거래값을 기준으로 오름차순 정렬하라.

#### [ Idea ]
- filter "year == 2020년 거래 내역" and order by value asc

#### Q. order by, 어떻게 하지? sort()를 Stream api가 제공을 하나? or collections?
A. Stream API provides `sorted()`
> Stream<T> sorted(Comparator<? super T> comparator);

#### [ My Answer ]

```
return transactions.stream()
    .filter(tx -> tx.getYear() == 2020)
    .sorted(Comparator.comparingInt(Transaction::getValue))
    .collect(Collectors.toList());
```


### 문제 4.2
거래 내역이 있는 거래자가 근무하는 모든 도시를 중복 없이 나열하라.



### 문제 4.3
서울에서 근무하는 모든 거래자를 찾아서 이름순서대로 정렬하라.



### 문제 4.4
모든 거래자의 이름을 구분자(",")로 구분하여 정렬하라.

### 문제 4.5
부산에 거래자가 있는지를 확인하라.



### 문제 4.6
서울에 거주하는 거래자의 모든 거래 금액을 구하라.



### 문제 4.7
모든 거래 내역중에서 거래 금액의 최댓값과 최솟값을 구하라.
단, 최댓값은 reduce를 이용하고 최솟값은 stream의 min()을 이용하라.


<br>