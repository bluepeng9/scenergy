##### 1. let 대신 const이 기본이다

-   모든 참조에는 let 대신 const를 사용하는 것을 기본으로 한다.
-   다만 재할당을 해야 한다면 let을 사용한다.
-   (참고) let과 const 모두 블록 스코프이다.

#####  2. 객체를 생성할 때는 리터럴 문법을 사용하자
##### 3. 단축구문을 사용하자
##### 4. 배열
+ 배열을 생성할 때 리터럴을 구문 사용하자

```javascript
// bad
const items = new Array();

// good
const items = [];
```
+ 배열에 직접 값을 할당하지 말고 Array.push을 사용하자
+ 배열을 복사할 때는 배열 전개 구문  `...`을 사용하자

```javascript
// bad
const len = items.length;
const itemsCopy = [];
let i;

for (i = 0; i < len; i += 1) {
  itemsCopy[i] = items[i];
}

// good
const itemsCopy = [...items];
```
+ 배열이 여러 줄에 걸쳐 있다면 배열을 연 이후와 닫기 이전에 줄바꿈을 하자
##### 5. 하나의 객체에서 여러 속성에 접근할 때는 객체 비구조화를 사용하자
##### 6. 문자열
+ 문자열에는 큰 따옴표 `""` 를 사용하자
+  100자가 넘는 문자열을 문자열 연결을 이용해 여러 줄에 걸쳐 쓰지 말자
+ 문자열을 생성하는 경우 문자열 연결 대신 템플릿 문자열을 사용하자
```javascript
// good
function sayHi(name) {
  return `How are you, ${name}?`;
}
```
##### 7. 함수선언식 대신 기명 함수표현식을 사용하자
```javascript
// good
// 변수 참조 호출과 구분되는 이름
const short = function longUniqueMoreDescriptiveLexicalFoo() {
  // ...
};
```
##### 8. 함수
+절대 arguments 사용 금지
+ 함수 시그니처에 공백을 넣자
```javascript
// good
const x = function () {};
const y = function a() {};
```
##### 8-1. 매개변수
+ 절대로 매개변수를 바꾸지 말자
+ 절대로 매개변수를 재할당하지 말자
##### 8-2. 함수 호출
+ 가변 인자 함수를 호출할 때는 전개 구문  `...`을 사용하자
```javascript
// good
const x = [1, 2, 3, 4, 5];
console.log(...x);

// good
new Date(...[2016, 8, 5]);
```
+ 여러 줄의 시그니처 또는 호출을 취하는 함수는 들여쓰기를 해주자
+ 익명함수를 사용할 때는 화살표 함수 표현을 사용하자
+ 명확성과 일관성을 위해 항상 인자를 괄호로 감싸자

```javascript
// good
[1, 2, 3].map((x) => x * x);
```
##### 9. 중복되는 클래스 멤버를 만들지 말자

##### 10. 클래스 메소드는 외부 라이브러리나 프레임워크가 구체적으로 비정적 메소드를 요구하지 않는 이상 this를 사용하거나 해당 메소드를 정적 메소드로 만들어야 한다
##### 11. 모듈 사용법
```javascript
// best
import { es6 } from './StyleGuide';
export default es6;
```
+ 와일드카드 import는 사용하지 말자
```javascript
// bad
import * as AirbnbStyleGuide from './AirbnbStyleGuide';
```
+ 가변 바인딩을 export하지 말자(일반적으로는 상수 참조만 export되어야 함)
+ 한가지만 export하는 모듈에서는 이름 붙여진 export보다는 default export를 사용하자
+ 여러 줄에 걸친 import는 여러 줄의 배열이나 객체 리터럴처럼 들여쓰기하자

##### 12. 자바스크립트 파일 확장자를 명시하지 말자
##### 13.  이터레이터 사용 않기. for-in이나 for-of같은 루프 대신 자바스크립트의 고급함수를 사용하자
#### 14. 속성에 접근할 때는 마침표를 사용하자
```javascript
const luke = {
  jedi: true,
  age: 28,
};
// good
const isJedi = luke.jedi;
```
##### 15. 변수를 사용해 속성에 접근할 때는 대괄호 []를 사용하자
```javascript
const luke = {
  jedi: true,
  age: 28,
};

function getProp(prop) {
  return luke[prop];
}

const isJedi = getProp('jedi');
```
##### 16. 제곱 계산을 할 때는 Math.pow 대신 제곱 연산자 **을 사용하자
##### 17. 변수
+ 하나의 변수 선언/할당에는 하나의 const 또는 let을 사용하자
+ const를 그룹화한 다음에 let을 선언하자
```javascript
// good
const goSportsTeam = true;
const items = getItems();
let dragonball;
let i;
let length;
```
+ 변수 할당 체이닝을 하지 말자
```javascript
// good
(function example() {
  let a = 1;
  let b = a;
  let c = a;
}());

console.log(a); // throws ReferenceError
console.log(b); // throws ReferenceError
console.log(c); // throws ReferenceError

// `const`에도 동일하게 적용
```
##### 18. 연산
+ 단항 증감 연산자(++, --)를 사용하지 말자
```javascript
// good

const array = [1, 2, 3];
let num = 1;
num += 1;
num -= 1;

const sum = array.reduce((a, b) => a + b, 0);
const truthyCount = array.filter(Boolean).length;
```
+ 삼항 연산자를 중첩해서는 안되며, 일반적으로 한줄에 표현해야 한다
+ 불필요한 삼항 연산자를 사용하지 말자
```javascript
// good
const foo = a || b;
const bar = !!c;
const baz = !c;
```
+ 연산자를 섞어 사용할 때 해당 연산자들을 괄호로 둘러싸자
    - 유일한 예외는 산술 연산자 (+, -, **)
    -  /와 *은 섞일 경우 순서가 모호할 수 있으므로 괄호로 감싸기
##### 19.  조건문/제어문
+ 여러 줄의 if와 else문을 사용할 때는 else를 if 블록의 닫는 중괄호와 같은 줄에 두자
+ if 블록이 항상 return 구문을 실행시킨다면, else 블록은 불필요하다
+ 제어문 (if, while 등)이 너무 길거나 최대 길이를 넘긴 경우, 각 조건을 새로운 줄에 두자
##### 20. 주석
+ 한줄 주석을 쓸 때는 //을 사용, 주석 전에는 빈 행을 넣어주기
+  모든 주석은 공백으로 시작해야 한다

##### 21. 문제를 지적하고 재고를 촉구하는 경우나 문제의 해결책을 제안하는 경우 등에는 주석 앞에 FIXME 나 TODO 를 붙임으로써 다른 개발자의 빠른 이해를 돕는다

+ 문제를 지적하는  `// FIXME:`

```javascript
class Calculator extends Abacus {
  constructor() {
    super();

    // FIXME: 전역 변수를 사용해서는 안 됨
    total = 0;
  }
}
```

+ 문제의 해결책을 제안하는  `// TODO:`

```javascript
class Calculator extends Abacus {
  constructor() {
    super();

    // TODO: total은 옵션 파라메터로 설정해야함
    this.total = 0;
  }
}
```
##### 22. 공백
+ 탭은 공백문자 2개로 설정!
+ 주요 중괄호 앞에는 공백을 1개 넣자
+ 제어문 (if, while 등)의 소괄호 앞에는 공백을 1개 넣자.
+ 함수선언이나 함수호출시 인자 리스트 앞에는 공백을 넣지 말자.
+  연산자 사이에 공백을 넣자
+ 구문의 앞과 블록의 뒤에는 빈 행을 두자
+ 블록에 빈 행을 끼워 넣지 말자
+ 대괄호 안쪽에 공백을 두지 말자+ 중괄호 안쪽에 공백을 두자
+ 객체 리터럴 속성의 키와 값 사이에는 공백을 넣자
```javascript
// good
var obj = { foo: 42 };
```
+ 파일의 마지막 행에는 빈 행을 두되 파일의 시작에는 빈 행을 두지 말자
+ 맨 앞의 쉼표는 불가, 끝에 쉼표를 달아준다
+ spread 구문시 나머지에는 쉼표를 달지 않는다.
##### 23. eslint를 쓰자
구문의 끝을 명시, 빠뜨린 세미콜론을 잡도록 linter를 설정.
##### 24. 형변환을 하는 경우 Number를 사용하고, 문자열을 파싱하는 경우에는 기수를 인자로 넘겨 parseInt를 사용하자

```javascript
const inputValue = '4';

// good
const val = Number(inputValue);

// good
const val = parseInt(inputValue, 10);
```
##### 25. 명명
+ 객체, 함수, 인스턴스에는 캐멀케이스(camelCase)를 사용
```javascript
// good
const thisIsMyObject = {};
function thisIsMyFunction() {}
```
+ 클래스나 생성자에는 파스칼케이스(PascalCase)를 사용
```javascript
// good
class User {
  constructor(options) {
    this.name = options.name;
  }
}

const good = new User({
  name: 'yup',
});
```
+ 파일 이름은 default export의 이름과 일치해야 한다
+ 상수 이름을 대문자로 짓는 것은 해당 상수가
    - (1) 내보내기 될 때
    - (2) const 타입일 때 (값이 재할당되지 못할 때)
    - (3) 그 상수와 상수가 중첩된 속성이 절대 변하지 않는다는 것을 신뢰할 수 있을 때
```javascript
// good
export const MAPPING = {
  key: 'value' // 속성인 key는 대문자가 아님에 유의
};
```