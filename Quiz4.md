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

#### [ Idea ]
- Convert tx list to trader list
- Convert trader list to city list
- use `distinct()` or `Set<도시>`

#### [ My Answer ]

```
return transactions.stream()
    .map(Transaction::getTrader)
    .filter(Objects::nonNull)
    .map(Trader::getCity)
    .filter(Objects::nonNull)
    .distinct()
    .collect(Collectors.toList());
```

### 문제 4.3
서울에서 근무하는 모든 거래자를 찾아서 이름순서대로 정렬하라.

#### [ Idea ]

- Convert tx list to trader list
- Filter trader who lives in Seoul
- Order by Trader name

#### [ My Answer ]
```
return transactions.stream()
    .filter(Objects::nonNull)
    .map(Transaction::getTrader)
    .distinct()
    .filter(trader -> trader != null && targetCity.equals(trader.getCity()))
    .sorted(Comparator.comparing(Trader::getName))
    .collect(Collectors.toList());
```

#### Q. `Comparator.comparing()`?

```
// AS-IS : 실제 비교하고자 하는 Key.compareTo() 이용
.sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))

// TO-BE : 간결하게 비교하고자 하는 Key Extractor만 제공
.sorted(Comparator.comparing(Trader::getName))
```

```
public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
        Function<? super T, ? extends U> keyExtractor)
{
    Objects.requireNonNull(keyExtractor);
    return (Comparator<T> & Serializable)
        (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
}
```

#### Q. `distinct`는 무엇을 기준으로 동치성을 확인할까?

A. 당연하지만 `equals()` 기준.

`distinct()`관련 TMI : 
- For Ordered stream   : stable 보장 O (같은 값이 연산 수행후에도 기존 순서 유지)
- For Unordered stream(`Stream.generate()`) : stable 보장 x

- stable 보장한다는 소리는 stateful한 연산 이기에, 병렬 처리시 비싼 연산으로 취급된다.
- 따라서, stable보장 필요 없는 경우는, `BaseStream.unorder()`를 이용해 `order 제약조건` 제거하여 성능 향상 가능.
```
/**
 * Returns a stream consisting of the distinct elements (according to
 * {@link Object#equals(Object)}) of this stream.
 *
 * <p>For ordered streams, the selection of distinct elements is stable
 * (for duplicated elements, the element appearing first in the encounter
 * order is preserved.)  For unordered streams, no stability guarantees
 * are made.
 *
 * <p>This is a <a href="package-summary.html#StreamOps">stateful
 * intermediate operation</a>.
 *
 * @apiNote
 * Preserving stability for {@code distinct()} in parallel pipelines is
 * relatively expensive (requires that the operation act as a full barrier,
 * with substantial buffering overhead), and stability is often not needed.
 * Using an unordered stream source (such as {@link #generate(Supplier)})
 * or removing the ordering constraint with {@link #unordered()} may result
 * in significantly more efficient execution for {@code distinct()} in parallel
 * pipelines, if the semantics of your situation permit.  If consistency
 * with encounter order is required, and you are experiencing poor performance
 * or memory utilization with {@code distinct()} in parallel pipelines,
 * switching to sequential execution with {@link #sequential()} may improve
 * performance.
 *
 * @return the new stream
 */
Stream<T> distinct();
```

### 문제 4.4
모든 거래자의 이름을 구분자(",")로 구분하여 정렬하라.


#### [ Idea ]
- Convert tx list to trader list
- Convert trader list to trader name list
- distinct
- Sort trader name list with nature order
- Join with ","

#### [ My Answer ]
```
return transactions.stream()
    .map(tx -> tx.getTrader().getName())
    .distinct()
    .sorted(Comparator.naturalOrder())
    .collect(Collectors.joining(","));
```

### 문제 4.5
부산에 거래자가 있는지를 확인하라.

#### [ Idea ]
- Use anyMatch() if 트랜잭션의 트레이더 중 부산지역사람 존재하는지

#### [ My Answer ]
```
final String targetCity = "Busan";
return transactions.stream()
    .anyMatch(tx -> targetCity.equals(tx.getTrader().getCity()));
```

### 문제 4.6
서울에 거주하는 거래자의 모든 거래 금액을 구하라.

#### [ Idea ]
- 트랜잭션 리스트중, 해당 트랜젹선의 거래자가 서울에 사는 리스트를 필터링
- 해당 트랜잭션 리스트를 트랜잭션 거래 금액으로 Mapping하여 반환

#### [ My Answer ]
```
final String targetCity = "Seoul";
return transactions.stream()
    .filter(tx -> targetCity.equals(tx.getTrader().getCity()))
    .map(Transaction::getValue)
    .collect(Collectors.toList());
```

### 문제 4.7
모든 거래 내역중에서 거래 금액의 최댓값과 최솟값을 구하라.
단, 최댓값은 reduce를 이용하고 최솟값은 stream의 min()을 이용하라.

#### [ Idea ]
- Q. 한 스트림에서 min, max 동시에 구할 수 있나? I don't think so.
  - -> Use IntStreamStats?
  - -> 요구 사항이 `max : use reduce(), min : use min()`이니, 아마 스트림 2개 필요하지 않을까?
  - -> A. YES; 스트림은 한번 소비되면 끝이므로 2개 필요
- Q. 어떻게 reduce를 이용해 max값을 구하지?
  - -> 애초에 reduce연산은 누적 값을 구하기 위해 사용하는 거 아닌가?
  - -> A. checkout reduce java doc
- Q. reduce연산은, IntStream에서도 이용가능한가?
  - -> A. YES
```
return Arrays.asList(getMaxValue(), getMinValue());

int getMaxValue(List<Transaction> transactions) {
    return transactions.stream()
        .???
}

int getMinValue(List<Transaction> transactions) {
    return transactions.stream()
        .mapToInt(tx -> tx.getValue())
        .min();
}
```

#### [ My Answer ]
```
return new Integer[] {getMaxValue(transactions), getMinValue(transactions)};

int getMaxValue(List<Transaction> transactions) {
    return transactions.stream()
        .mapToInt(Transaction::getValue)
        .reduce(0, (maxSoFar, currentElement) -> maxSoFar < currentElement ? currentElement : maxSoFar);
}

int getMinValue(List<Transaction> transactions) {
    return transactions.stream()
        .mapToInt(Transaction::getValue)
        .min()
        .orElse(0);
}
```

#### [ Feedback ]

- `min()` return Optional.

- read `reduce()` java doc

#### Reference

https://withbeth.oopy.io/e5cee31d-fb11-49b4-8586-f93635ea519f

