## 문제 6
아래와 같은 학생 클래스가 있다고 하자.
(생성자 및 Getter 등은 생략)

```java
public class Student {

    private String name;
    private boolean isMale; // 성별
    private int hak; // 학년
    private int ban; // 반
    private int score;

    public String toString() {
        return String.format("[%s, %s, %d학년 %d반, %3d점 ]", name, isMale ? "남" : "여", hak, ban, score);
    }
}
```


또한 데이터는 Student[] stuArr에 저장되어 있다.

### 문제 6.1

stuArr에서 불합격(150점 미만)한 학생의 수를 남자와 여자로 구별하여라.
(Boolean, List<Student>)

#### [ Idea ]

- Boolean : 여자 (false), 남자 (true)
- Use `Collectors.partitioningBy`

#### [ Answer ]

```
return Arrays.stream(stuArr)
    .filter(student -> student.getScore() < 150)
    .collect(Collectors.partitioningBy(Student::isMale));
```

#### [ Note ]

Q. `Collectors.partitioningBy()`?


from : Javadoc

> Returns a Collector which 
> 
> partitions the input elements according to a Predicate, 
> 
> and organizes them into a Map<Boolean, List<T>>. 
 
> Params: predicate – a predicate used for classifying input elements
> Returns: a Collector implementing the partitioning operation 

### 문제 6.2
각 반별 총점을 학년 별로 나누어 구하여라
(Map<Integer, Map<Integer, Integer>>)

#### [ Idea ]

- Map<학년, Map<반, totalScoreOf반>>
- 2번의 groupingBy필요.

```
return Arrays.stream(stuArr)
    .collect(groupingBy(Student::getHak,
        toMap(Student::getBan, Student::getScore, (oldScore, newScore) -> newScore += oldScore)));
```

#### [ Answer ]
```
return Stream.of(stuArr)
        .collect(groupingBy(Student::getHak, groupingBy(Student::getBan, summingInt(Student::getScore))));
```

#### [ Note ]
Q. `Collectors.groupingBy()`?

from : JavaDoc

> Returns a Collector implementing a cascaded "group by" operation on input elements of type T,
> grouping elements according to a `classification function`,
> and then performing **a reduction operation** on the values associated with a given key using `downstream Collector`.

> The classification function maps elements to some key type K.

> The downstream collector operates on elements of type T and produces a result of type D.

> The resulting collector produces a Map<K, D>.

```
public static <T, K, A, D>
Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                                      Collector<? super T, A, D> downstream) {
    return groupingBy(classifier, HashMap::new, downstream);
}
```


example from JavaDoc
```
Map<City, Set<String>> namesByCity = people.stream()
    .collect(groupingBy(Person::getCity, mapping(Person::getLastName, toSet())));
```


<br>