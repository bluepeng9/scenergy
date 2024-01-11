
> 음악과 사람을 잇는 모든 연결의 시작, 시너지 
--- 
### 기획 배경 

___
### 프로젝트 소개 
 + Scenergy는 `Springboot` `vue.js`를 기반으로 만들어진 뮤지션을 위한 웹 큐레이팅 SNS 입니다. 
--- 
### 프로젝트 목표 
 + 멤버를 구하는데 어려움을 겪는 뮤지션을 위해 플랫폼을 제공합니다.
 +  연주 영상 업로드에 특화된 게시글 작성 기능을 통해 자신을 어필하고 싶은 뮤지션에게 좀 더 나은 환경을 제공합니다. 
 + 역할을 분담하여 서비스를 코드로 **구현**하고 **배포**합니다. 
 + 구현하는 과정에서 Front-end와 Back-end가 **협업**하는 과정을 이해합니다. 
 +  RESTful API를 직접 설계하고 API를 통한 HTTP 통신을 겪으며 협업 능력을 기릅니다. 
 +  자신이 맡은 부분을 남에게 설명할 수 있는 의사소통 능력을 기릅니다. 
 +  단순히 구현 후 끝나는 것이 아닌 **코드 리뷰**와 **피드백**을 통해 함께 성장합니다. 
 + 기존의 코드를 지속적으로 개선하기 위해 **리팩토링**을 진행합니다. 
--- 
### 기술스택 
#### 개발 환경 
 
<img src="https://img.shields.io/badge/Visual%20Studio%20Code-007ACC.svg?&style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> 
 
#### 개발 
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
 
#### 커뮤니케이션 
<img src="https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=Mattermost&logoColor=white"> <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white"> 
---
### 기능정의서 및 ERD

#### 기능정의서
<a href="https://docs.google.com/spreadsheets/d/1eb2wqtTxqo4EBiJq_kRAA-wO56QQbC8ycn4FkqmRq-s/edit?pli=1#gid=2139742202"><h5>scenergy 기능 정의서</h5></a>
#### ERD
<img src="/uploads/a130be76fd8f0d6470d962bc5adc4928/B308.png">

---
### 페르소나
#### 1️⃣ 드러머 구하는 김민석

<img src="/uploads/84c0d3d84f06a25b32a03cc59e890732/image__7_.png" width="600">

#### 2️⃣ 팀을 꾸리고 싶은 강혜은
<img src="/uploads/a131b4107aecb5d8fa770fdd79c29c46/image__8_.png" width="600"> 

---
### 코드 컨벤션

#### BE 코드 컨벤션

##### 파일 공통 요건
1. 파일 인코딩은 UTF-8
2. 새줄 문자는 LF
3. 파일의 마지막에는 새줄

##### 이름 (Naming)
1. 식별자에는 영문/숫자/언더스코어만 허용
2. 한국어 발음대로의 표기 금지
3. 대문자로 표기할 약어 명시
4. 패키지 이름은 소문자로 구성
5. 클래스/인터페이스 이름에 대문자 카멜표기법 적용
6. 클래스 이름에 명사 사용
7. 인터페이스 이름에 명사/형용사 사용
8. 테스트 클래스는 'Test’로 끝남
9. 메서드 이름에 소문자 카멜표기법 적용
10. 메서드 이름은 동사/전치사로 시작
11. 상수는 대문자와 언더스코어로 구성
12. 변수에 소문자 카멜표기법 적용
13. 임시 변수 외에는 1 글자 이름 사용 금지

##### 선언 (Declarations)
1. 소스파일당 1개의 탑레벨 클래스를 담기
2. static import에만 와일드 카드 허용
3. 제한자 선언의 순서
4. 애너테이션 선언 후 새줄 사용
5. 한 줄에 한 문장
6. 하나의 선언문에는 하나의 변수만
7. 배열에서 대괄호는 타입 뒤에 선언
8. `long`형 값의 마지막에 `L`붙이기
9. 특수 문자의 전용 선언 방식을 활용

##### 들여쓰기 (Indentation)
1. 하드탭 사용
2. 탭의 크기는 4개의 스페이스
3. 블럭 들여쓰기

##### 중괄호 (Braces)
1. K&R 스타일로 중괄호 선언
2. 닫는 중괄호와 같은 줄에 else, catch, finally, while 선언
3. 빈 블럭에 새줄 없이 중괄호 닫기 허용
4. 조건/반복문에 중괄호 필수 사용

##### 줄바꿈 (Line-wrapping)
1. 최대 줄 너비는 120
2. package,import 선언문은 한 줄로
3. 줄바꿈 후 추가 들여쓰기
4. 줄바꿈 허용 위치

##### 빈 줄(Blank lines)
1. package 선언 후 빈 줄 삽입
2. import 선언의 순서와 빈 줄 삽입
3. 메소드 사이에 빈 줄 삽입

##### 공백 (Whitespace)
1. 공백으로 줄을 끝내지 않음
2. 대괄호 뒤에 공백 삽입
3. 중괄호의 시작 전, 종료 후에 공백 삽입
4. 제어문 키워드와 여는 소괄호 사이에 공백 삽입
5. 식별자와 여는 소괄호 사이에 공백 미삽입
6. 타입 캐스팅에 쓰이는 소괄호 내부 공백 미삽입
7. 제네릭스 산괄호의 공백 규칙
8. 콤마/구분자 세미콜론의 뒤에만 공백 삽입
9. 콜론의 앞 뒤에 공백 삽입
10. 이항/삼항 연산자의 앞 뒤에 공백 삽입
11. 단항 연산자와 연산 대상 사이에 공백을 미삽입
12. 주석문 기호 전후의 공백 삽입

#### FE 코드 컨벤션

---

### 팀원 소개
### Contributors

|<img src="/uploads/8c282182d74295e5f9974e98d513db55/김준표.jpg" width="110"><br>**김준표**|<img src="/uploads/4f94ffce787f8c59b26d0bdb40aafd1a/김혜지.jpg" width="110"><br>**김혜지**|<img src="/uploads/fc76178fc2ab76750f3ef76435a9fdc6/이민형.jpg" width="110"><br>**이민형**|<img src="/uploads/5547ddc370012032652eba43b8ccf115/이태경.jpg" width="110"><br>**이태경**|<img src="/uploads/51330ab2900e11d5a46f07d1e163fe42/김은지.jpg" width="110"><br>**김은지**|<img src="/uploads/277b2260b05e29e580d0d1af4dda106f/강대은.jpg" width="110"><br>**강대은**|
|--|--|--|--|--|--|
|**🍪Back-end**<br>**💎Team Leader**<br>**🤴Back-end Leader**|**💟Back-end**|**🧗‍♀️Back-end**<br>**🎩Survey Leader**|**🍖Back-end**|**🌹Front-end**<br>**👸Front-end Leader**|**🍞Front-end**|
