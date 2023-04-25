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
