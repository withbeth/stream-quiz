# 문제 8 (무한 스트림)

피보나치 수열 :

0,1로 시작하며, 이후 숫자는 이전의 두 숫자를 더한 값.

예) 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55...

피보나치 수열 집합 :

각 연속된 피보나치 수열을 (이전 피보나치 수, 현재 피보나치 수) Pair 형식으로 나타낸 집합.

예) (0,1), (1, 1) (1, 2) (2, 3) (3, 5), (5, 8), (8, 13), (13, 21)...

## 문제 8.1

Stream.iterate()를 이용해, 피보나치수열 집합을 10개 만들어 주세요

Input : void

Output : List<int[]>

#### [ Idea ]
```
return Stream.iterate(new int[]{0, 1}, xs -> new int[]{xs[1], xs[0] + xs[1]})
    .limit(10)
    .collect(Collectors.toList());
```
#### [ Note ]
Q. How ArraysList.equals() works?
- A. 각 원소들의 equals()
 
Q. How int[].equals() work?
- A. Same with Object.equals()

## 문제 8.2

Stream.generate()를 이용해, 피보나치수열 집합을 10개 만들어 주세요

Input : void

Output : List<int[]>

#### [ Idea ]
- Stream.generate()는 `Supplier<T>`를 인자로 받아, 매번 새로운 값 생성하는 스트림.
- 보통 공급자(발행자)가 상태를 가지지 않아야 병렬 수행시 올바른 결과를 얻을 수 있다.
 
```
return Stream.generate(new Supplier<int[]>() {
        private int[] current = new int[]{0, 1};

        @Override
        public int[] get() {
            int[] result = this.current.clone();
            this.current = new int[]{result[1], result[0] + result[1]};
            return result;
        }
    })
    .limit(10)
    .collect(Collectors.toList());
```



