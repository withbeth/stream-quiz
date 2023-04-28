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
#### [ Answer ]
#### [ Note ]


<br>

### 문제 5.4
두 개의 주사위를 굴려서 나온 눈의 합이 6인 경우를 모두 출력하시오.

#### [ Idea ]
#### [ Answer ]
#### [ Note ]

