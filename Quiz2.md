## 문제2

아래와 같은 데이터가 저장된 리스트가 있다고 하자.
```
private final static List<String> WORDS = Arrays.asList("TONY", "a", "hULK", "B", "america", "X", "nebula", "Korea");
```



### 문제 2.1
List에 저장된 단어들의 접두사가 각각 몇개씩 있는지 Map<String, Integer>으로 변환하여라.
ex) ("T", 1), ("a", 2) ...

[ Idea ]
- 각 Word로부터 첫 문자(letter) 만 추출 -> groupingBy(letter -> letter, Collectors.counting())하면 되지 않을까?
- > Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {...}

```
return WORDS.stream()
    .map(word -> word.substring(0, 1))
    .collect(groupingBy(letter -> letter, summingInt(letter -> 1)));
```


### 문제 2.2
List에 저장된 단어들 중에서 단어의 길이가 2 이상인 경우에만, 모든 단어를 대문자로 변환하여 스페이스로 구분한 하나의 문자열로 합한 결과를 반환하여라.
ex) ["Hello", "a", "Island", "b"] -> “H I”

[ Idea ]
- 단어 길이가 2 이상인 단어들 filtering -> 해당 단어의 첫 문자를 대문자 변환 -> join with space delimiter

```
return WORDS.stream()
    .filter(word -> word.length() >= 2)
    .map(word -> word.substring(0, 1))
    .map(String::toUpperCase)
    .collect(joining(" "));
```