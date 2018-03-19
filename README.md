### smartkuk-coupon 개발과정 ###

아래는 구상했던 개발 로직을 공유 드립니다. 먼저 랜덤한 숫자를 채번 해주는 클래스는 SplittableRandom(https://docs.oracle.com/javase/8/docs/api/java/util/SplittableRandom.html) 클래스를 이용하여 쿠폰번호를 구성하도록 했습니다.

개발로직 1:
1. DB 검사(이메일 중복검사, 번호 중복검사) -> 쿠폰번호 랜덤하게 채번 -> DB 저장

문제점:
1. 100개의 병렬 처리시 중복의 문제


개발로직 2:
0. 쿠폰 pool 생성(1초 단위 스케줄러를 통해 사용 가능한 쿠폰을 pool에 계속 생성)
1. DB 검사(이메일 중복검사)
2. pool 테이블에서 쿠폰번호 select for update을 사용한 쿠폰번호 획득
3. 획득한 생성된 쿠폰번호 상태변경
4. DB 저장

문제점:
1. 쿠폰을 미리 준비해야 하는 스케줄러는 계속 pool의 가용 갯수를 확인 및 쿠폰번호를 채워줘야 함
2. 만약 쿠폰번호를 채워주는 스케줄러가 채우는 속도보다 더 많은 양의 request가 서버로 들어오면 장애발생
3. pool에서 유니크한 쿠폰 1개에 대해서 select for update를 사용하여 lock을 잡았을때 다른 요청이 동일 쿠폰번호를 조회 했을때의 소모되는 과정이 많음


현재 프로젝트 소스의 로직:
1. 이메일 입력값 및 DB 중복검사 -> 저장할 엔티티 생성
2. 쿠폰번호 랜덤 생성후 엔티티 할당 -> DB 저장(실패시 재시도 20회 1초 간격 반복)

설명: 생성된 쿠폰번호를 저장하려고 할 때에 오류가 발생 했다면 쿠폰번호의 채번과 저장만 처리하면 되기 때문에 재시도를 하는 부분을 method로 빼놓고 해당 method에서는 채번 및 저장 하는 로직만을 남겨놓음 

결과:
랜덤 이메일 10,000개를 병렬로 호출하는 테스트에서 모두 성공

하지만 스레드 풀 설정을 50개로 지정하고 테스트를 하여도 활성화 스레드는 36개 초과하지는 못하였음

이클립스 Heap size 4G로 설정 하였지만 Overflow는 발생하지 않음

이를 실서버에 적용한다고 가정한다면 쿠폰번호에 대한 유효기간과 사용여부 정보를 추가로 관리하여 지속적으로 테이블에서 제거 또는 이력을 관리하는 성격의 테이블에 옮겨 주는게 좋아보임


### 테스트 환경 ###
MacBook Pro (Retina, 15-inch, Mid 2015)

CPU: 2.2 GHz Intel Core i7

MEM: 16GB 1600 MHz DDR3

이클립스 Heap size: -Xmx4096m



### Build ###
maven 프로젝트로 구성이 되어있고, 빌드는 maven 빌드를 한다.

```bash
$ ./mvnw package #프로젝트 root 디렉토리로 이동하여 실행
```

### Run ###
빌드를 끝마치면 ${project_path}/target/smartkuk-coupon-0.0.1-SNAPSHOT.jar 파일이 생성되어 있는지 확인

아래와 같이 디렉토리를 이동후 jar 파일 실행 커맨드를 통하여 서버를 구동한다. `-DPORT=1234` 옵션을 통해서 포트를 변경 가능하며 입력하지 않으면 기본포트는 9090 이다.

```bash
$ cd ${project_path}
$ java -jar -DPORT=1234 ./smartkuk-coupon-0.0.1-SNAPSHOT.jar
```

### H2 콘솔 ###
Database는 H2 사용하고 서버 구동을 성공적으로 했다면 `http://localhost:1234/h2-console` 사용하여 접속할 수 있고 접속 정보는 아래와 같다.

```bash
JDBC URL: jdbc:h2:mem:smartkuk
User Name: sa
Password: 입력하지 않음
```

### Operating ###
로컬 환경에서 jar 파일을 실행후 브라우저에서 http://localhost:포트 입력하여 화면을 이용할 수 있음.

<img width="1280px" height="800px" src="./coupon_view.png" align="left" ></img>

[SplittableRandom]: https://docs.oracle.com/javase/8/docs/api/java/util/SplittableRandom.html