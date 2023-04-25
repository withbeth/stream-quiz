## 문제 3

```
private static final List<Integer> numbers1 = Arrays.asList(1, 2, 3);
private static final List<Integer> numbers2 = Arrays.asList(3, 4);
```



### 문제 3.1
위와 같은 숫자 리스트가 있을 때 모든 숫자 쌍의 배열 리스트를 반환하여라.
ex) numbers1 = [1,2,3], numbers2 =  [3,4] -> [(1,3), (1,4), (2,3), (2,4), (3,3), (3,4)]

[ Idea ]
```
return number1.stream()
    .map(i -> number2.stream().map(j -> Arrays.asList(i, j)))
    .toList();
```

[ Problem ]

- `Stream<Stream<Intger[]>>`을 반환하게 되기에, `flatMap`적용 필요.

[ Q. flatMap? ]
- `<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);`
- This operation has the effect of applying a `one-to-many` transformation to the elements of the stream, and then flattening the resulting elements into a new stream.
- 즉, 스트림의 각 값을 다른 스트림으로 만든 후 모든 스트림을 하나의 스트림으로 연결하여, 평면화된 스트림을 반환.
- Example :
```
If orders is a stream of purchase orders, and each purchase order contains a collection of line items, then the following produces a stream containing all the line items in all the orders:
 orders.flatMap(order -> order.getLineItems().stream())...
 
If path is the path to a file, then the following produces a stream of the words contained in that file:
 Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);  
 Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
 
The mapper function passed to flatMap splits a line, using a simple regular expression, into an array of words, and then creates a stream of words from that array.
```

[ Answer ]
```
return numbers1.stream()
    .flatMap(i -> numbers2.stream().map(j -> new Integer[]{i, j}))
    .collect(Collectors.toList());
```



### 문제 3.2
위와 같은 숫자 리스트가 있을 때 모든 숫자 쌍의 곱이 가장 큰 값을 반환하여라.
ex) numbers1 = [1,2,3], numbers2 =  [3,4] -> 12

[ Idea ]
- 각 스트림을 모두 순회해서, 각 숫자쌍의 곱의 리스트로 추출.
- 해당 곱 리스트의 max값을 반환.
```
return numbers1.stream()
    .flatMap(i -> numbers2.stream().map(j -> i * j))
    .max();
```

[ Problem ]

- `max()`는, `Comparator` 인자 필요.

[ Q. 그럼 어떻게 정렬해야 max값을 얻을 수 있나? ]
- A. 값이 큰 것을 1로 반환하는 Comparator 구현체 제공.

Why :
- `Stream` interface의 `max()`는 `ReferencePipeline`에서 overriding.
- 해당 메서드 구현에서는, `BinaryOperator.maxBy()`를 결과값을 reducing.
- `maxBy()`는, `compare`값이 0 이상인 것을 취한다.
 
```
// ReferencePipeline
@Override
public final Optional<P_OUT> max(Comparator<? super P_OUT> comparator) {
    return reduce(BinaryOperator.maxBy(comparator));
}

// BinaryOperator
public static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) {
    Objects.requireNonNull(comparator);
    return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
}
```

[ Answer ]
```
return numbers1.stream()
    .flatMap(i -> numbers2.stream().map(j -> i * j))
    .max(Integer::compare)
    .orElse(0);
```

[ Feedback ]

- Integer 객체 이용하는 것보다 int 기본타입 이용하는게 성능상 효율적이니, `mapToInt()`으로 `IntStream`이용하는 것이 더 좋을듯 하다.