## 문제 5

### 문제 5.1
문자열 배열 String[] strArr = {"aaa","bb","c","dddd"}의 모든 문자열의 길이를 더한 결과를 출력하여라.

#### [ Idea ]
```
return Arrays.stream(strArr)
    .mapToInt(word -> word.length())
    .reduce(0, (accumulatedSoFar, current) -> accumulatedSoFar + current);
```

#### [ Answer ]
```
return Arrays.stream(STRING_ARR)
    .mapToInt(String::length)
    .sum();
```

#### [ Note ]

- `IntStream.sum()` is equivalent to `reduce(0, Integer::sum)`


<br>

### 문제 5.2
문자열 배열 String[] strArr = {"aaa","bb","c","dddd"}의 문자열 중에서 가장 긴 것의 길이를 출력하시오.

#### [ Idea ]
```
return Arrays.stream(STRING_ARR)
    .mapToInt(word -> word.length())
    .reduce(0, (accumulatedSoFar, current) -> accumulatedSoFar < current ? current : accumulatedSoFar);
```

#### [ Answer ]
```
return Arrays.stream(STRING_ARR)
    .mapToInt(String::length)
    .reduce(0, Math::max);
```

<br>

### 문제 5.3
임의의 로또번호(1~45)를 정렬해서 출력하시오.

#### [ Idea ]

- 임의의(랜덤) 로또 번호를 생성하는 스트림 생성.
- 해당 스트림에서 중복 없는 로또 번호를 로또번호 개수만큼 획득.

#### [ Answer ]

- Use Random.ints() which return IntStream
- Sort after limit

```
return new Random().ints(1, 46)
  .distinct()
  .limit(6)
  .sorted()
  .boxed()
  .collect(Collectors.toList());
```

#### [ Note ]
Q. `HashSet` vs `TreeSet` vs `LinkedHashSet`?

- HashSet : HashMap이용. 순서 보장 X
- TreeSet : TreeMap(RBT)이용. Comparator(Comparable)순으로 순서 보장 O
  - Q. RBT는 이진트리?
    - A. self-balancing `binary search tree`
- LinkedHashSet : HashSet이용. 추가적으로 Doubly Linked List이용해 Insertion순으로 순서 보장 O
  - Q. 소스코드 봐도 링크드 리스트 이용한게 없는데? from HERE

Q. Random.ints() ?
```
// Returns an effectively unlimited stream of pseudorandom int values, each conforming to the given origin (inclusive) and bound (exclusive).
public IntStream ints(int randomNumberOrigin, int randomNumberBound) {
    if (randomNumberOrigin >= randomNumberBound)
        throw new IllegalArgumentException(BadRange);
    return StreamSupport.intStream
            (new RandomIntsSpliterator
                     (this, 0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound),
             false);
}
```

#### [FeedBack ]

- 조금더 나은 방법 없나? 꼭 boxed()이용해서 박싱해야 하나?



<br>

### 문제 5.4
두 개의 주사위를 굴려서 나온 눈의 합이 6인 경우를 모두 출력하시오.

#### [ Idea ]
- 효율성은 일단 잊어버리고, 정확도에 집중
- [1..6] range를 가지는 2개의 IntStream이용해 두 합이 6을 갖는 경우의 수 를 필터링하여 반환.
 
```
return IntStream.rangeClosed(1, 6)
  .flatMap(i -> IntStream.rangeClosed(1, 6).filter(j -> i + j == 6).mapToObject(j -> new Integer[]{i, j}))
  .collect(Collectors.toList());
```

- Compile Error cause `IntStream.flatMap()` return IntStream, Not Stream<R>.
- 따라서 `IntStream.mapToObj()`를 이용해 Stream<Stream<Integer[]>> 형태로 반환받아 `Stream.flatMap()`이용.

```
return IntStream.rangeClosed(1, 6)
    .mapToObj(i ->  IntStream.rangeClosed(1, 6).filter(j -> i + j == 6).mapToObj(j -> new Integer[]{i, j}))
    .flatMap(stream -> stream)
    .collect(Collectors.toList());
```

#### [ Answer ]
#### [ Note ]

