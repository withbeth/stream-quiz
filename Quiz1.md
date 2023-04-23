## 문제1

아래와 같은 User.csv가 있다고 할 때, 아래의 CSV 데이터를 조회하여 아래와 같은 결과를 출력한다고 하자.
```text
//User.csv
이름, 취미, 소개
김프로, 축구:농구:야구, 구기종목 좋아요
정프로, 개발:당구:축구, 개발하는데 뛰긴 싫어
앙몬드, 피아노, 죠르디가 좋아요 좋아좋아너무좋아
죠르디, 스포츠댄스:개발, 개발하는 죠르디 좋아
박프로, 골프:야구, 운동이 좋아요
정프로, 개발:축구:농구, 개발도 좋고 운동도 좋아
```

### 문제 1.1
위와 같은 데이터를 조회하여 각 취미를 선호하는 인원이 몇 명인지 계산하여라.

[ Idea ]
- 각 컬렉션의 원소를 순회하며, 각 취미들을 HashMap에 mapping(Key:취미, Val:Count).

[ Approach1 ]
- use `Collectors.groupingBy` and `Collectors.couting()`
```
return csvLines.stream()
    .map(line -> line[1])
    .map(hobbies -> hobbies.split(":"))
    .flatMap(Arrays::stream)
    .map(String::trim)
    .collect(groupingBy(hobby -> hobby, summingInt(each -> 1)));
```
[ Note ]
- Q. How to skip csv header?
    - A. 애초에 헤더는 빼고 추출된다.
- Q. 취미 항목만 어떻게 추출하나?
    - A. 객체매핑이 안되있으니 포지션으로 추출(1)
- Q. Stream<String[]>을 어떻게 하나의 스트림으로 평준화 시키나
    - A. flatMap(Arrays.stream)
- Q. 추출한 각 취미들로부터, 어떻게 그 카운트를 구하지?
    - A. groupingBy의 Collector에 `couting()`이용
- Q. `Collector.couting()` return Long인데, Int로 어떻게 캐스팅하면 좋을까?
    - A. use `summingInt` collector

[ Approach2 ]
- use `Collectors.toMap(KeyMapper,ValueMapper,MergeFunction)`
- > Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
  Function<? super T, ? extends U> valueMapper,
  BinaryOperator<U> mergeFunction) {...}
```
return csvLines.stream()
    .map(strings -> strings[1]) // collect only hobbies
    .map(hobbies -> hobbies.split(":"))
    .flatMap(Arrays::stream)
    .map(String::trim)
    .collect(toMap(hobby -> hobby, hobby -> 1, Integer::sum));
```

[ Reference ]
- toMap : https://www.baeldung.com/java-collectors-tomap

### 문제 1.2
위와 같은 데이터를 조회하여 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.

[ Idea ]
- 정씨 성을 갖는 라인을 먼저 필터링 한 후에 Quiz1.1로직 적용
```
return csvLines.stream()
    .filter(line -> line[0].startsWith("정"))
    .map(line -> line[1].replaceAll("\\s", ""))
    .map(hobbies -> hobbies.split(":"))
    .flatMap(Arrays::stream)
    .map(String::trim)
    .collect(groupingBy(hobby -> hobby, summingInt(hobby -> 1)));
```

### 문제 1.3
위와 같은 데이터를 조회하여 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.<br>

[ Idea ]
- 소개 내용 컬럼들을 먼저 추출후, 각 라인마다, 소개 내용에 해당 키워드가 "몇번" 등장하는지 카운팅.
- 각 라인 마다 몇번 등장했는지 카운팅했는지의 총 합계를 reduce 이용하여 계산.
```
return csvLines.stream()
    .map(line -> line[2])
    .map(introduction -> getMatchCount(introduction, "좋아"))
    .reduce(0, Integer::sum);

private static int getMatchCount(String line, String keyword) {
    int result = 0;
    int foundIndex = line.indexOf(keyword);
    while (0 <= foundIndex && foundIndex < line.length()) {
        result++;
        foundIndex = line.indexOf(keyword, foundIndex + keyword.length());
    }
    return result;
}
```

<br>