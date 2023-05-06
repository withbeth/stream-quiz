# 문제 7

## 문제 7 - 1

정수 n이 주어졌을때, 2부터 n 까지의 자연수를 소수와, 비소수로 나누는 프로그램을 구현하라.

메서드는 다음과 같다.

`Map<Boolean, List<Integer>> quiz1(int n) {...} `


소수란 `1과 자기자신 외에는 약수를 가지지 않는 수`.


### Input

정수 n

### Output

`Map<Boolean, List<Integer>>`
- Boolean : 소수일 경우 true이며, 비소수일 경우 false
- List<Integer> : 2부터 n까지의 자연수중, 해당 경우에 속하는 자연수 집합.

### Constraint

- 2 <= n <= 1_000_000

####  [ Idea ]
- Use IntStream.rangeClosed(2, n)
- Use Collector.partitioningBy((i) -> i % 2 == 0, Collector.toSet())
- Q. Custom Set(`TreeSet`)을 반환하는 컬렉터 작성법?

```
boolean isPrime(int candidate) {
    return IntStream.range(2, candidate)
        .noneMatch(i -> candidate % i == 0);
}

Map<Boolean, List<Integer>> quiz1(int n) {
    return IntStream.rangeClosed(2, n)
        .boxed()
        .collect(partitioningBy(Answer7::isPrime, toList()));
}
```


### [ Feedback ]

- toSet()과 같은 유틸성 메서드는, `Collectors`에서 제공. 클래스 이름보고 인식 할수 있어야 한다.
  - 관례상 유틸성 클래스는 복수형이름으로 짓는다.(EffectiveJava Item XX TODO )

- IntStream을 직접 partitioningBy로 이용하지 못한다. primitive type이니까.
  - 따라서 boxed로 Integer객체로 박싱 필요.


## 문제7-2

7-1을, `partitioningBy()`를 이용하지 않고 커스텀 컬렉터를 이용하여 구현하고 성능 개선을 시도하라.

성능 개선 할 수 있는 부분 : 

- 주어진 숫자가 소수인지 아닌지 판단하하기 위해, 지금까지 구한 소수리스트 이용
 
```
boolean isPrime(List<Integer> primes, int candidate) {
  int candidateRoot = (int) Math.sqrt((double) candidate);
  takeWhile(primes, i -> i <= candidateRoot).stream()
    .nonMatch(i -> candidate % i == 0);
}

<T> List<T> takeWhile(List<T> list, Predicate<T> predicate) {
  int i = 0;
  for (T item : list) {
    if (!predicate.test(item)) return list.subList(0, i);
    i++;
  }
  return list;
}
```

## TODO



